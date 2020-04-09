package com.sskj.common.mvc;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjq.toast.ToastUtils;
import com.shizhefei.mvc.ILoadViewFactory;
import com.shizhefei.view.vary.VaryViewHelper;
import com.sskj.common.App;
import com.sskj.common.R;
import com.sskj.common.exception.LogoutException;

/**
 * Created by vzhihao on 2016/11/24.
 */
public class LoadViewFactory implements ILoadViewFactory {
    @Override
    public ILoadMoreView madeLoadMoreView() {
        return new LoadMoreHelper();
    }

    @Override
    public ILoadView madeLoadView() {
        return new LoadViewHelper();
    }

    private static class LoadMoreHelper implements ILoadMoreView {

        protected TextView footView;

        protected View.OnClickListener onClickRefreshListener;

        @Override
        public void init(FootViewAdder footViewHolder, View.OnClickListener onClickRefreshListener) {
            View contentView = footViewHolder.getContentView();

            Context context = contentView.getContext();
            TextView textView = new TextView(context);
            textView.setTextColor(Color.GRAY);
            textView.setPadding(0, dip2px(context, 16), 0, dip2px(context, 16));
            textView.setGravity(Gravity.CENTER);
            footViewHolder.addFootView(textView);

            footView = textView;
            this.onClickRefreshListener = onClickRefreshListener;
            showNormal();
        }

        @Override
        public void showNormal() {
            footView.setText(App.INSTANCE.getString(R.string.strLoadViewFactory200));
            footView.setOnClickListener(onClickRefreshListener);
        }

        @Override
        public void showLoading() {
            footView.setText(App.INSTANCE.getString(R.string.strLoadViewFactory201));
            footView.setOnClickListener(null);
        }

        @Override
        public void showFail(Exception exception) {
            footView.setText(App.INSTANCE.getString(R.string.strLoadViewFactory202));
            footView.setOnClickListener(onClickRefreshListener);
        }

        @Override
        public void showNomore() {
            footView.setText(App.INSTANCE.getString(R.string.strLoadViewFactory203));
            footView.setOnClickListener(null);
        }

    }

    private static class LoadViewHelper implements ILoadView {
        private VaryViewHelper helper;
        private View.OnClickListener onClickRefreshListener;
        private Context context;

        @Override
        public void init(View switchView, View.OnClickListener onClickRefreshListener) {
            this.context = switchView.getContext().getApplicationContext();
            this.onClickRefreshListener = onClickRefreshListener;
            helper = new VaryViewHelper(switchView);
        }

        @Override
        public void restore() {
            helper.restoreView();
        }

        @Override
        public void showLoading() {
            View loadingView = LayoutInflater.from(context).inflate(R.layout.common_mvc_loading_view, null);
//            ImageView ivLoading = loadingView.findViewById(R.id.ivLoading);
//            AnimationDrawable animationDrawable = (AnimationDrawable) ivLoading.getDrawable();
//            animationDrawable.start();
//            time = 0;
//            DisposUtil.close(timeDispo);
//            timeDispo = Flowable.interval(0, 20, TimeUnit.MILLISECONDS)
//                    .onBackpressureDrop()
//                    .subscribeOn(Schedulers.newThread())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .map(aLong -> time++)
//                    .subscribe(aLong -> {
//                        myMaterialHeader.onPositionChange(aLong % 100);
//                    });
            helper.showLayout(loadingView);
        }

        @Override
        public void tipFail(Exception exception) {
            String msg;
            if (exception.getCause() instanceof LogoutException) {
                msg = App.INSTANCE.getString(R.string.strLoadViewFactory205);
            } else {
                msg = App.INSTANCE.getString(R.string.strLoadViewFactory206);
            }
            ToastUtils.show(msg);
        }

        @Override
        public void showFail(Exception exception) {
            String msg;
            if (exception.getCause() instanceof LogoutException) {
                msg = App.INSTANCE.getString(R.string.strLoadViewFactory205);
            } else {
                msg = App.INSTANCE.getString(R.string.strLoadViewFactory206);
            }
            Context context = helper.getContext();
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setGravity(Gravity.CENTER);
            TextView textView = new TextView(context);
            textView.setText(msg);
            textView.setTextColor(ContextCompat.getColor(context, R.color.common_hint));
            textView.setGravity(Gravity.CENTER);
            layout.addView(textView);
            Button button = new Button(context);
            button.setText(App.INSTANCE.getString(R.string.strLoadViewFactory209));
            button.setOnClickListener(onClickRefreshListener);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT );
            int top = dip2px(context, 12);
            params.setMargins(0, top, 0, 0);
            layout.addView(button, params);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT );
            layout.setMinimumHeight(dip2px(context, 300));
            layout.setLayoutParams(layoutParams);
//            layout.setPadding(0, dip2px(context, 50), 0, 0);
            helper.showLayout(layout);
        }

        @Override
        public void showEmpty() {
            Context context = helper.getContext();
            View emptyView = LayoutInflater.from(context).inflate(R.layout.common_empty_view, null);
            emptyView.setOnClickListener(onClickRefreshListener);
            helper.showLayout(emptyView);
        }

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
