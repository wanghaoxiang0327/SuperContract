package com.sskj.common;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.sskj.common.base.BaseActivity;
import com.sskj.common.glide.HttpsUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Hey
 * Create at  2019/07/04
 */
public class WebViewActivity extends BaseActivity<WebViewPresenter> {


    @BindView(R2.id.content)
    WebView content;

    private AgentWeb agentWeb;
    private String url;

    @Override
    public int getLayoutId() {
        return R.layout.common_activity_web_view;
    }

    @Override
    public WebViewPresenter getPresenter() {
        return new WebViewPresenter();
    }

    @Override
    public void initView() {
        url = getIntent().getStringExtra("url");
        agentWeb = AgentWeb.with(this)
                .setAgentWebParent(content, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebViewClient(new WebViewClient(){
                    @Override
                    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                        CertifiUtils.OnCertificateOfVerification(handler, view.getUrl());
                    }
                })
                .createAgentWeb()
                .ready()
                .go(url);
    }

    @Override
    public void initData() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        agentWeb.destroy();
    }

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }


}
