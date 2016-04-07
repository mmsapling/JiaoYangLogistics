package com.tylz.jiaoyanglogistics.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.base.BaseFragment;
import com.tylz.jiaoyanglogistics.util.DateFormatUtil;
import com.tylz.jiaoyanglogistics.view.TopMenu;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;

/**
 * @author tylz
 * @time 2016/4/6 0006 14:56
 * @des 在线下单
 *
 * @updateAuthor
 * @updateDate 2016/4/6 0006
 * @updateDes
 */
public class OnlineOrderFra
        extends BaseFragment
{
    @Bind(R.id._top_menu)
    TopMenu      mTopMenu;
    @Bind(R.id._tv_date)
    TextView     mTvDate;
    @Bind(R.id._container_date)
    LinearLayout mContainerDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = mLayoutInflater.inflate(R.layout.fra_online_order, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initView() {
        mTopMenu.setTitle("在线下单");
        mTopMenu.setLeftIcon(R.drawable.selector_menu_back);
        mTopMenu.setLeftIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabHomeFra fra = (TabHomeFra) getParentFragment();
                fra.switchTab(TabHomeFra.TAG_USER_HOME);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({
              R.id._container_date})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id._container_date:
                selectDate();
                break;
        }
    }

    /**
     * 选择日期
     */
    private void selectDate() {
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        dialog.show();
        DatePicker picker =  new DatePicker(mContext);
        picker.setDate(DateFormatUtil.getCurrentYear(), DateFormatUtil.getCurrentMonth());
        picker.setMode(DPMode.SINGLE);
        picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                mTvDate.setText(date);
                dialog.dismiss();
            }
        });
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                   ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setContentView(picker,params);
        dialog.getWindow().setGravity(Gravity.CENTER);
    }
}
