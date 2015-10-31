package io.geeteshk.polyide.project;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import io.geeteshk.polyide.R;
import io.geeteshk.polyide.util.PolyClient;

public class ProjectHolder extends RecyclerView.ViewHolder {

    public Button mOpenProject, mConfigureProject;
    public TextView mTitleView, mDescriptionView;
    public WebView mPreview;

    public ProjectHolder(View view) {
        super(view);

        mOpenProject = (Button) view.findViewById(R.id.open_button);
        mConfigureProject = (Button) view.findViewById(R.id.configure_button);

        mTitleView = (TextView) view.findViewById(R.id.project_title);
        mDescriptionView = (TextView) view.findViewById(R.id.project_description);

        mPreview = (WebView) view.findViewById(R.id.project_preview);
        mPreview.setWebViewClient(new PolyClient());
    }
}
