package io.geeteshk.polyide.workspace;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class WorkspaceAdapter extends FragmentStatePagerAdapter {

    String[] mFiles = new String[]{"index.html", "style.css", "main.js"};
    String mProject;

    public WorkspaceAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        WorkspaceFragment fragment = new WorkspaceFragment();
        fragment.setProject(mProject);
        fragment.setFilename(mFiles[position]);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFiles[position];
    }

    public void setProject(String project) {
        mProject = project;
    }

    @Override
    public int getCount() {
        return mFiles.length;
    }
}
