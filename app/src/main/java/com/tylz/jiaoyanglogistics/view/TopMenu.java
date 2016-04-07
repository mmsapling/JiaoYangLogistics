package com.tylz.jiaoyanglogistics.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.util.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author tylz
 * @time 2016/3/18 0018 15:02
 * @des 自定义菜单栏
 * @updateAuthor tylz
 * @updateDate 2016/3/18 0018
 * @updateDes
 */
public class TopMenu extends RelativeLayout {
    @Bind(R.id.menu_iv_lefticon)
    ImageView mIvLeftIcon;
    @Bind(R.id.menu_tv_lefttext)
    TextView mTvLeftText;
    @Bind(R.id.menu_tv_title)
    TextView mTvTitle;
    @Bind(R.id.menu_tv_righttext)
    TextView mTvRightText;
    @Bind(R.id.menu_iv_righticon)
    ImageView mIvRightIcon;

    public TopMenu(Context context) {
        super(context);
        initView();
    }

    public TopMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_topmenu, this);
        ButterKnife.bind(this);
    }
    public void setTitle(String title){
        mTvTitle.setText(title);
    }
    public void setTitle(int resId){
        mTvTitle.setText(UIUtils.getString(resId));
    }
    public void setLeftText(String text){
        mIvLeftIcon.setVisibility(View.GONE);
        mTvLeftText.setText(text);
        mTvLeftText.setVisibility(View.VISIBLE);
    }
    public void setRightText(String text){
        mIvRightIcon.setVisibility(View.GONE);
        mTvRightText.setText(text);
        mTvRightText.setVisibility(View.VISIBLE);
    }
    public void setRightText(int resId){
        mIvRightIcon.setVisibility(View.GONE);
        mTvRightText.setText(UIUtils.getString(resId));
        mTvRightText.setVisibility(View.VISIBLE);
    }
    public void setLeftIcon(int resId){
        mTvLeftText.setVisibility(View.GONE);
        mIvLeftIcon.setImageResource(resId);
        mIvLeftIcon.setVisibility(View.VISIBLE);
    }
    public void setRightIcon(int resId){
        mTvRightText.setVisibility(View.GONE);
        mIvRightIcon.setImageResource(resId);
        mIvRightIcon.setVisibility(View.VISIBLE);
    }
    public void setLeftIconOnClickListener(OnClickListener listener){
       mIvLeftIcon.setOnClickListener(listener);
    }
    public void setRightIconOnClickListener(OnClickListener listener){
        mIvRightIcon.setOnClickListener(listener);
    }
    public void setLeftTextOnClickListener(OnClickListener listener){
        mTvLeftText.setOnClickListener(listener);
    }
    public void setRightTextOnClickListener(OnClickListener listener){
        mTvRightText.setOnClickListener(listener);
    }

    /**
     * 隐藏左边所有的控件
     */
    public void hideLeftView(){
        mTvLeftText.setVisibility(View.GONE);
        mIvLeftIcon.setVisibility(View.GONE);
    }
    /**
     * 隐藏右边所有的控件
     */
    public void hideRightView(){
        mTvRightText.setVisibility(View.GONE);
        mIvRightIcon.setVisibility(View.GONE);
    }
}
