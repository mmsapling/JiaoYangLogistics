package com.tylz.jiaoyanglogistics.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.util.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author tylz
 * @time 2016/3/29 0029 10:42
 * @des 空视图
 *
 * @updateAuthor
 * @updateDate 2016/3/29 0029
 * @updateDes 空视图
 */
public class DEmptyView
        extends LinearLayout
{
    @Bind(R.id._iv_empty)
    ImageView mIvEmpty;
    @Bind(R.id._tv_empty)
    TextView  mTvEmpty;

    public DEmptyView(Context context) {
        super(context);
        initView();
    }

    public DEmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_empty, this);
        ButterKnife.bind(this);
    }
    public void setEmptyIcon(int resId){
        mIvEmpty.setImageResource(resId);
    }
    public void setEmptyText(int resId){
        mTvEmpty.setText(UIUtils.getString(resId));
    }
    public void setEmptyText(String text){
        mTvEmpty.setText(text);
    }
}
