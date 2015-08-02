package io.geeteshk.polyide;

import android.animation.ValueAnimator;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mTitleView;
    public TextView mDescriptionView;

    private int mHeight = 0;
    private boolean mExpanded = false;
    private View mView;

    public ProjectHolder(View view) {
        super(view);

        view.setOnClickListener(this);
        mTitleView = (TextView) view.findViewById(R.id.project_title);
        mDescriptionView = (TextView) view.findViewById(R.id.project_description);

        mView = LayoutInflater.from(view.getContext()).inflate(R.layout.project_expanded_item, null, false);
        mView.findViewById(R.id.open_project).getBackground().setColorFilter(0xff2196f3, PorterDuff.Mode.MULTIPLY);
        mView.findViewById(R.id.configure_project).getBackground().setColorFilter(0xffffffff, PorterDuff.Mode.MULTIPLY);
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
}
