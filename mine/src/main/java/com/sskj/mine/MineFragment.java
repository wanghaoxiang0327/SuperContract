package com.sskj.mine;

import android.os.Bundle;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.sskj.common.base.BaseFragment;
import com.sskj.common.dialog.TipDialog;
import com.sskj.common.utils.ClickUtil;

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
        ClickUtil.click(menuSecurity, view -> {
            SecurityActivity.start(getContext());
        });

        //董事分红
        ClickUtil.click(menuDirector, view -> {
            new TipDialog(getContext())
                    .setContent("离董事分红仅差一步，赶快邀请好友。")
                    .setConfirmListener(dialog -> {
                        dialog.dismiss();
                        DirectorActivity.start(getContext());
                    }).show();

        });
        //邀请好友
        ClickUtil.click(menuInvite, view -> {
            InviteActivity.start(getContext());
        });
    }

    @Override
    public void loadData() {

    }


    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

}
