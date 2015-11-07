package io.geeteshk.polyide.project;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

import io.geeteshk.polyide.ConfigureActivity;
import io.geeteshk.polyide.R;
import io.geeteshk.polyide.workspace.WorkspaceActivity;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectHolder> {

    public AppCompatActivity mContext;
    public Project[] mProjects;

    public ProjectAdapter(AppCompatActivity context, Project[] projects) {
        mContext = context;
        mProjects = projects;
    }

    @Override
    public ProjectHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ProjectHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.project_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final ProjectHolder projectHolder, final int i) {
        projectHolder.mTitleView.setText(mProjects[i].getTitle());
        projectHolder.mDescriptionView.setText(mProjects[i].getDescription());
        projectHolder.mPreview.loadUrl("file://" + Environment.getExternalStorageDirectory() + File.separator + "Polyide" + File.separator + projectHolder.mTitleView.getText() + File.separator + "index.html");
        projectHolder.mOpenProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openIntent = new Intent(mContext, WorkspaceActivity.class);
                openIntent.putExtra("project_name", mProjects[i].getTitle());
                mContext.startActivityForResult(openIntent, 0);
            }
        });

        projectHolder.mConfigureProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent configureIntent = new Intent(mContext, ConfigureActivity.class);
                configureIntent.putExtra("project_title", mProjects[i].getTitle());
                configureIntent.putExtra("project_description", mProjects[i].getDescription());
                configureIntent.putExtra("project_url", "file://" + Environment.getExternalStorageDirectory() + File.separator + "Polyide" + File.separator + projectHolder.mTitleView.getText() + File.separator + "index.html");
                mContext.startActivity(configureIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProjects.length;
    }
}
