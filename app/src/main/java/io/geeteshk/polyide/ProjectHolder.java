package io.geeteshk.polyide;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class ProjectHolder extends RecyclerView.ViewHolder {
    public TextView mTitleView;
    public TextView mDescriptionView;
    public WebView mPreview;

    public ProjectHolder(View view) {
        super(view);

        mTitleView = (TextView) view.findViewById(R.id.project_title);
        mDescriptionView = (TextView) view.findViewById(R.id.project_description);

        mPreview = (WebView) view.findViewById(R.id.project_preview);
        mPreview.setWebViewClient(new PolyClient());
    }

    private class PolyClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
