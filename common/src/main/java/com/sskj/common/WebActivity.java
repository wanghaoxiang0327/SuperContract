package com.sskj.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.sskj.common.base.BaseActivity;
import com.sskj.common.data.WebData;
import com.sskj.common.language.LocalManageUtil;
import com.zzhoujay.richtext.RichText;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Hey
 * Create at  2019/07/01
 */
public class WebActivity extends BaseActivity<WebPresenter> {


    @BindView(R2.id.text)
    TextView text;

    private int type;

    @Override
    public int getLayoutId() {
        return R.layout.common_activity_web;
    }

    @Override
    public WebPresenter getPresenter() {
        return new WebPresenter();
    }

    @Override
    public void initView() {
        type = getIntent().getIntExtra("type", 0);
        switch (type) {
            case 1:
                mToolBarLayout.setTitle(getString(R.string.common_webActivity1));
                break;
            case 2:
                mToolBarLayout.setTitle(R.string.common_invite_rules);
                break;
            case 3:
                mToolBarLayout.setTitle(R.string.common_director_rules);
                break;
            default:
                break;
        }
    }

    @Override
    public void initData() {
        mPresenter.getOrderDetail(LocalManageUtil.getLanguage(this));
    }

    /**
     * @param context
     * @param type    1 注册协议 2 邀请规则 3董事分红规则
     */
    public static void start(Context context, int type) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    public void setData(WebData data) {

        switch (type) {
            case 1:
                RichText.fromHtml(data.getReg_agree()).into(text);
                break;
            case 2:
                RichText.fromHtml(data.getTj_agree()).into(text);
                break;
            case 3:
                RichText.fromHtml(data.getFh_agree()).into(text);
                break;
            default:
                break;
        }


    }


}
