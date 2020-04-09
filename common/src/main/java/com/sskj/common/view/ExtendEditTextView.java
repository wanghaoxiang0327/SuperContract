package com.sskj.common.view;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

/**
 * TextView扩展类,可以处理左右drawable点击事件
 * @author Hey
 */
public class ExtendEditTextView extends AppCompatEditText {

    private static final int DRAWABLE_RIGHT = 2;
    private static final int DRAWABLE_LEFT = 0;

    private boolean isClick;

    private float x;
    private float y;

    /**
     * 最大移动距离,超出则视为滑动
     */
    private float moveDistance = 40;


    private OnRightDrawableClickListener onRightDrawableClickListener;
    private OnLeftDrawableClickListener onLeftDrawableClickListener;

    public ExtendEditTextView(Context context) {
        super(context);
    }

    public ExtendEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isClick = true;
                x = event.getX();
                y = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //若移动距离大于移动距离则视作滑动，不处理点击事件
                if (event.getX() - x > moveDistance) {
                    isClick = false;
                }
                if (event.getY() - y > moveDistance) {
                    isClick = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isClick) {
                    //点击右边
                    if (this.getCompoundDrawables()[DRAWABLE_RIGHT] != null) {
                        if (event.getX() > getWidth() - getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width() - getCompoundDrawablePadding()) {
                            if (onRightDrawableClickListener != null) {
                                onRightDrawableClickListener.onClick(this);
                                return true;
                            }
                        }
                    }
                    //点击左边
                    if (this.getCompoundDrawables()[DRAWABLE_LEFT] != null) {
                        if (event.getX() < getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width() + getCompoundDrawablePadding()) {
                            if (onRightDrawableClickListener != null) {
                                return onLeftDrawableClickListener.onClick(this);
                            }
                        }
                    }

                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void setOnLongClickListener(@Nullable OnLongClickListener l) {
        super.setOnLongClickListener(l);
    }

    public interface OnRightDrawableClickListener {
        /**
         * 点击
         *
         * @param view
         * @return
         */
        boolean onClick(ExtendEditTextView view);
    }

    public interface OnLeftDrawableClickListener {
        /**
         * 点击
         *
         * @param view
         * @return
         */
        boolean onClick(ExtendEditTextView view);
    }


    public void setOnRightDrawableClickListener(OnRightDrawableClickListener onRightDrawableClickListener) {
        this.onRightDrawableClickListener = onRightDrawableClickListener;
    }

    public void setOnLeftDrawableClickListener(OnLeftDrawableClickListener onLeftDrawableClickListener) {
        this.onLeftDrawableClickListener = onLeftDrawableClickListener;
    }

}
