package com.sskj.common.glide;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.sskj.common.R;
import com.youth.banner.loader.ImageLoader;

/**
 * 作者 :吕志豪
 * 简书：https://www.jianshu.com/u/6e525b929aac
 * github：https://github.com/lvzhihao100
 * 描述：
 * 创建时间：2018-08-08 09:09
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .error(R.mipmap.common_banner_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideRoundTransformCenterCrop(20));
        GlideApp.with(context)
                .load(path)
                .apply(options)
                .into(imageView);
    }
}