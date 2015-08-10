package io.geeteshk.polyide;

import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.io.File;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectHolder> {

    public Project[] mProjects;

    public ProjectAdapter(Project[] projects) {
        mProjects = projects;
    }

    @Override
    public ProjectHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ProjectHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.project_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ProjectHolder projectHolder, int i) {
        projectHolder.mTitleView.setText(mProjects[i].getTitle());
        projectHolder.mDescriptionView.setText(mProjects[i].getDescription());

        if (mProjects[i].getTitle().equals("Welcome to Polyide!")) {
            projectHolder.mRootView.setOnClickListener(null);
        }

        projectHolder.mPreview.loadUrl("file://" + Environment.getExternalStorageDirectory() + File.separator + "Polyide" + File.separator + projectHolder.mTitleView.getText() + File.separator + "index.html");
    }

    @Override
    public int getItemCount() {
        return mProjects.length;
    }
}
