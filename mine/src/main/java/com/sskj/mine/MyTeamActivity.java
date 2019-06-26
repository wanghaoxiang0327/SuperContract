package com.sskj.mine;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.Guideline;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.sskj.common.adapter.BaseAdapter;
import com.sskj.common.adapter.ViewHolder;
import com.sskj.common.base.BaseActivity;
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

    BaseAdapter<TeamBean> teamAdapter;

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
        teamList.setLayoutManager(new LinearLayoutManager(this));
        teamAdapter = new BaseAdapter<TeamBean>(R.layout.mine_item_team, null, teamList) {
            @Override
            public void bind(ViewHolder holder, TeamBean item) {

            }
        };
    }

    @Override
    public void initData() {

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, MyTeamActivity.class);
        context.startActivity(intent);
    }


}
