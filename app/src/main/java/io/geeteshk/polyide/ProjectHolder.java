package io.geeteshk.polyide;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ProjectHolder extends RecyclerView.ViewHolder {
    public TextView mTitleView;
    public TextView mDescriptionView;

    public ProjectHolder(View view) {
        super(view);

        mTitleView = (TextView) view.findViewById(R.id.project_title);
        mDescriptionView = (TextView) view.findViewById(R.id.project_description);
    }
}
