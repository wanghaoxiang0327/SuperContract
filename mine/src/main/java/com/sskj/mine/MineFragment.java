package com.sskj.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.allen.library.SuperTextView;
import com.sskj.common.AppManager;
import com.sskj.common.BaseApplication;
import com.sskj.common.base.BaseFragment;
import com.sskj.common.dialog.TipDialog;
import com.sskj.common.router.RoutePath;
import com.sskj.common.utils.ClickUtil;
import com.sskj.common.utils.SpUtil;

import butterknife.BindView;

/**
 * Create at  2019/06/24
 */
public class MineFragment extends BaseFragment<MinePresenter> {


    @BindView(R2.id.user_name)
    TextView userName;
    @BindView(R2.id.user_id)
    TextView userId;
    @BindView(R2.id.user_level)
    TextView userLevel;
    @BindView(R2.id.menu_security)
    SuperTextView menuSecurity;
    @BindView(R2.id.menu_order_manager)
    SuperTextView menuOrderManager;
    @BindView(R2.id.menu_invite)
    SuperTextView menuInvite;
    @BindView(R2.id.menu_service)
    SuperTextView menuService;
    @BindView(R2.id.menu_version)
    SuperTextView menuVersion;
    @BindView(R2.id.menu_logout)
    SuperTextView menuLogout;
    @BindView(R2.id.menu_director)
    SuperTextView menuDirector;

    private boolean isDirector;

    @Override
    public int getLayoutId() {
        return R.layout.mine_fragment_mine;
    }

    @Override
    public MinePresenter getPresenter() {
        return new MinePresenter();
    }

    @Override
    public void initView() {
        menuVersion.setRightString("V" + BuildConfig.VERSION_NAME);
    }

    @Override
    public void initData() {
        userViewModel.getUser().observe(this, userBean -> {
            if (userBean != null) {
                userName.setText(userBean.getNickname());
                userLevel.setText(userBean.getUserLevel());
                userId.setText("UID：" + userBean.getUid());
                isDirector = userBean.getIs_ds() == 1;
                menuLogout.setVisibility(View.VISIBLE);
            } else {
                userName.setText(getString(R.string.mine_mineFragment1));
                userId.setText(getString(R.string.mine_mineFragment2));
                ClickUtil.click(userName, view -> {
                    ARouter.getInstance().build(RoutePath.LOGIN_LOGIN).navigation();
                });
                menuLogout.setVisibility(View.GONE);
            }
        });

        ClickUtil.click(menuOrderManager, view -> {
            if (BaseApplication.isLogin()) {
                ARouter.getInstance().build(RoutePath.ORDER_MANAGER).navigation();
            } else {
                ARouter.getInstance().build(RoutePath.LOGIN_LOGIN).navigation();
            }

        });

        //安全中心
        ClickUtil.click(menuSecurity, view -> {

            if (BaseApplication.isLogin()) {
                SecurityActivity.start(getContext());
            } else {
                ARouter.getInstance().build(RoutePath.LOGIN_LOGIN).navigation();
            }

        });
        //董事分红
        ClickUtil.click(menuDirector, view -> {

            if (BaseApplication.isLogin()) {
                if (isDirector) {
                    DirectorActivity.start(getContext());
                } else {
                    new TipDialog(getContext())
                            .setContent(getString(R.string.mine_mineFragment3))
                            .setConfirmListener(dialog -> {
                                dialog.dismiss();

                            }).show();
                }
            } else {
                ARouter.getInstance().build(RoutePath.LOGIN_LOGIN).navigation();
            }


        });
        //邀请好友
        ClickUtil.click(menuInvite, view -> {
            if (BaseApplication.isLogin()) {
                InviteActivity.start(getContext());
            } else {
                ARouter.getInstance().build(RoutePath.LOGIN_LOGIN).navigation();
            }

        });
        ClickUtil.click(menuLogout, (view) -> {
            new TipDialog(getContext())
                    .setContent(getString(R.string.mine_mineFragment4))
                    .setConfirmListener(dialog -> {
                        dialog.dismiss();
                        SpUtil.exit(BaseApplication.getMobile());
                        userViewModel.clear();
                        ARouter.getInstance().build(RoutePath.LOGIN_LOGIN).navigation();
                        AppManager.getInstance().finishAllLogin();
                    }).show();
        });
    }

    @Override
    public void loadData() {
        userViewModel.update();
    }

    @Override
    public void onVisible() {
        userViewModel.update();
    }

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

}
