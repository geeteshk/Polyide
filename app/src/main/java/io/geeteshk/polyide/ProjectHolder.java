package io.geeteshk.polyide;

import android.animation.ValueAnimator;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mTitleView;
    public TextView mDescriptionView;
    public View mRootView;
    public WebView mPreview;

    private int mHeight = 0;
    private boolean mExpanded = false;
    private View mView;

    public ProjectHolder(View view) {
        super(view);

        mRootView = view;
        mRootView.setOnClickListener(this);

        mTitleView = (TextView) view.findViewById(R.id.project_title);
        mDescriptionView = (TextView) view.findViewById(R.id.project_description);

        mView = LayoutInflater.from(view.getContext()).inflate(R.layout.project_expanded_item, null, false);
        mView.findViewById(R.id.open_project).getBackground().setColorFilter(0xff2196f3, PorterDuff.Mode.MULTIPLY);
        mView.findViewById(R.id.configure_project).getBackground().setColorFilter(0xffffffff, PorterDuff.Mode.MULTIPLY);
        mPreview = (WebView) mView.findViewById(R.id.project_preview);
        mPreview.setWebViewClient(new PolyClient());
    }

    @Override
    public void onClick(final View v) {
        if (mHeight == 0) {
            mHeight = v.getHeight();
        }

        ValueAnimator valueAnimator;
        if (!mExpanded) {
            ((LinearLayout) v).addView(mView);
            mExpanded = true;
            valueAnimator = ValueAnimator.ofInt(mHeight, mHeight + (int) (mHeight * 3.5));
        } else {
            mExpanded = false;
            valueAnimator = ValueAnimator.ofInt(mHeight + (int) (mHeight * 3.5), mHeight);
            ((LinearLayout) v).removeView(mView);
        }

        valueAnimator.setDuration(200);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (Integer) animation.getAnimatedValue();
                v.requestLayout();
            }
        });

        valueAnimator.start();
    }

    private class PolyClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
