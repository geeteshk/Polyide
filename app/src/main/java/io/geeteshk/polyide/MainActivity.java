package io.geeteshk.polyide;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import io.geeteshk.polyide.project.ProjectAdapter;
import io.geeteshk.polyide.project.ProjectHandler;
import io.geeteshk.polyide.util.Theme;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton mCreateProject;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView mNoProjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ProjectHandler.init();
        setTheme(Theme.getThemeId(this));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNoProjects = (TextView) findViewById(R.id.no_projects);
        mRecyclerView = (RecyclerView) findViewById(R.id.projects_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ProjectAdapter(MainActivity.this, ProjectHandler.getProjects());
        if (ProjectHandler.getProjects().length == 0) {
            mNoProjects.setAlpha(1);
        }

        mRecyclerView.setAdapter(mAdapter);
        mCreateProject = (FloatingActionButton) findViewById(R.id.create_project);
        mCreateProject.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_project:
                startActivityForResult(new Intent(MainActivity.this, CreateActivity.class), 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mAdapter.notifyDataSetChanged();
        recreate();
    }
}
