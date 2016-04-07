package com.tylz.jiaoyanglogistics.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.activity.ExchangeRecordActivity;
import com.tylz.jiaoyanglogistics.activity.PointDetailActivity;
import com.tylz.jiaoyanglogistics.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author tylz
 * @time 2016/3/25 0025 15:25
 * @des 我的积分
 *
 * @updateAuthor
 * @updateDate 2016/3/25 0025
 * @updateDes
 */
public class MyPointFra
        extends BaseFragment
{
    @Bind(R.id._tv_avaible_score)
    TextView mTvAvaibleScore;
    @Bind(R.id._tv_detail)
    TextView mTvDetail;
    @Bind(R.id._tv_record)
    TextView mTvRecord;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = mLayoutInflater.inflate(R.layout.fra_my_point, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id._tv_detail,
              R.id._tv_record})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._tv_detail:
                Intent intent1 = new Intent(mContext, PointDetailActivity.class);
                startActivity(intent1);
                break;
            case R.id._tv_record:
                Intent intent2 = new Intent(mContext, ExchangeRecordActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
