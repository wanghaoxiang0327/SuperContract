package com.sskj.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sskj.common.AppManager;
import com.sskj.common.base.BaseActivity;
import com.sskj.common.language.LocalManageUtil;
import com.sskj.common.router.RoutePath;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Hey
 * Create at  2019/06/24
 */
public class LanguageActivity extends BaseActivity<LanguagePresenter> {


    @BindView(R2.id.chinese)
    RadioButton chinese;
    @BindView(R2.id.chinese_p)
    RadioButton chineseP;
    @BindView(R2.id.english)
    RadioButton english;
    @BindView(R2.id.korean)
    RadioButton korean;
    @BindView(R2.id.languageGroup)
    RadioGroup languageGroup;

    @Override
    public int getLayoutId() {
        return R.layout.mine_activity_language;
    }

    @Override
    public LanguagePresenter getPresenter() {
        return new LanguagePresenter();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        chinese.setOnCheckedChangeListener((buttonView, isChecked) -> {
            changeLanguage(isChecked, 1);
        });
        chineseP.setOnCheckedChangeListener((buttonView, isChecked) -> {
            changeLanguage(isChecked, 2);
        });
        english.setOnCheckedChangeListener((buttonView, isChecked) -> {

            changeLanguage(isChecked, 3);
        });
        korean.setOnCheckedChangeListener((buttonView, isChecked) -> {
            changeLanguage(isChecked, 4);
        });
    }

    public void changeLanguage(boolean isChecked, int selection) {
        if (isChecked) {
            LocalManageUtil.saveSelectLanguage(this, selection);
            ARouter.getInstance().build(RoutePath.SPLASH).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).navigation();
        }
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, LanguageActivity.class);
        context.startActivity(intent);
    }


}
