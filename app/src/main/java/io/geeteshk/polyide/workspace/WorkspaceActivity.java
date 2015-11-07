package io.geeteshk.polyide.workspace;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import io.geeteshk.polyide.ConfigureActivity;
import io.geeteshk.polyide.R;
import io.geeteshk.polyide.util.Theme;

public class WorkspaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Theme.getThemeId(this));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workspace);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        WorkspaceAdapter adapter = new WorkspaceAdapter(getSupportFragmentManager());
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        adapter.setProject(getIntent().getStringExtra("project_name"));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_workspace, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_configure) {
            Intent configureIntent = new Intent(WorkspaceActivity.this, ConfigureActivity.class);
            startActivity(configureIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
