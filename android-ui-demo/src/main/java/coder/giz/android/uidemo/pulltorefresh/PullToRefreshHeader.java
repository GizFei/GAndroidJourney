package coder.giz.android.uidemo.pulltorefresh;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import coder.giz.android.uidemo.R;


public class PullToRefreshHeader extends BaseHeader {

    private LinearLayout llPullDownView;
    private ImageView ivLoading;
    private Animation animation;

    @Override
    public View getView(LayoutInflater inflater, ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.layout_pull_to_refresh_header, viewGroup, true);
        llPullDownView = view.findViewById(R.id.ll_pull_down_view);
        ivLoading = view.findViewById(R.id.iv_home_loading);
        animation = AnimationUtils.loadAnimation(viewGroup.getContext(), R.anim.common_rotation);
        return view;
    }

    @Override
    public void onPreDrag(View rootView) {
        if(ivLoading.getVisibility()==View.GONE) {
            llPullDownView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDropAnim(View rootView, int dy) {

    }

    @Override
    public void onLimitDes(View rootView, boolean upORdown) {

    }

    @Override
    public void onStartAnim() {
        llPullDownView.setVisibility(View.GONE);
        ivLoading.setVisibility(View.VISIBLE);
        ivLoading.startAnimation(animation);
    }

    @Override
    public void onFinishAnim() {
        if (ivLoading != null) {
            ivLoading.clearAnimation();
            ivLoading.setVisibility(View.GONE);
        }
    }
}
