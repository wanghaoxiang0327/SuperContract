package com.sskj.mine;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.Guideline;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.sskj.common.DividerLineItemDecoration;
import com.sskj.common.adapter.BaseAdapter;
import com.sskj.common.adapter.ViewHolder;
import com.sskj.common.base.BaseActivity;
import com.sskj.common.utils.TimeFormatUtil;
import com.sskj.mine.data.ShareInfo;
import com.sskj.mine.data.TeamBean;

import butterknife.BindView;

/**
 * @author Hey
 * Create at  2019/06/24
 */
public class MyTeamActivity extends BaseActivity<MyTeamPresenter> {


    @BindView(R2.id.team_count)
    TextView teamCount;
    @BindView(R2.id.mine_guideline)
    Guideline mineGuideline;
    @BindView(R2.id.increase_count)
    TextView increaseCount;
    @BindView(R2.id.team_list)
    RecyclerView teamList;

    BaseAdapter<TeamBean.Team> teamAdapter;

    private int page = 1;
    private int size = 10;

    @Override
    public int getLayoutId() {
        return R.layout.mine_activity_my_team;
    }

    @Override
    public MyTeamPresenter getPresenter() {
        return new MyTeamPresenter();
    }

    @Override
    public void initView() {
        teamList.addItemDecoration(new DividerLineItemDecoration(this)
                .setFirstDraw(false)
                .setLastDraw(false)
                .setDividerColor(color(R.color.common_divider))
        );
        teamList.setLayoutManager(new LinearLayoutManager(this));
        teamAdapter = new BaseAdapter<TeamBean.Team>(R.layout.mine_item_team, null, teamList) {
            @Override
            public void bind(ViewHolder holder, TeamBean.Team item) {
                holder.setText(R.id.name_tv, item.getRealname())
                        .setText(R.id.mobile_tv, item.getAccount())
                        .setText(R.id.time_tv, TimeFormatUtil.SF_FORMAT_E.format(item.getReg_time() * 1000));
            }
        };
    }

    @Override
    public void initData() {
        wrapRefresh(teamList);
    }

    @Override
    public void loadData() {
        mPresenter.getTeam(page, size);
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        page++;
        mPresenter.getTeam(page, size);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        page = 1;
        mPresenter.getTeam(page, size);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, MyTeamActivity.class);
        context.startActivity(intent);
    }


    public void setTeam(TeamBean data) {
        teamCount.setText(data.getTtl());
        increaseCount.setText(data.getToday());
        if (page == 1) {
            teamAdapter.setNewData(data.getList());
        } else {
            teamAdapter.addData(data.getList());
        }

        if (data.getList() == null || data.getList().isEmpty()) {
            mRefreshLayout.setNoMoreData(true);
        }

    }
}
