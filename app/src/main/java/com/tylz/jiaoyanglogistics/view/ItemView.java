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
 * @time 2016/3/25 0025 08:55
 * @des 一般条目的view
 *
 * @updateAuthor
 * @updateDate 2016/3/25 0025
 * @updateDes
 */
public class ItemView
        extends RelativeLayout
{
    @Bind(R.id.item_view_icon)
    ImageView mIvIcon;
    @Bind(R.id.item_view_text)
    TextView  mTvText;
    @Bind(R.id.item_view_arrow)
    ImageView mIvArrow;
    @Bind(R.id.item_view_line)
    View      mViewLine;
    @Bind(R.id.item_view_right_text)
    TextView  mTvRightText;

    public ItemView(Context context) {
        super(context);
        initView();
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_item, this);
        ButterKnife.bind(this);
    }

    public void setText(String text) {
        mTvText.setText(text);
    }

    public void setText(int resId) {
        mTvText.setText(UIUtils.getString(resId));
    }

    /**
     * 设置文本，并且隐藏图标
     * @param resId  文本资源id
     */
    public void setTextAndHideIcon(int resId) {
        mIvIcon.setVisibility(View.GONE);
        mTvText.setText(UIUtils.getString(resId));
        RelativeLayout.LayoutParams params = (LayoutParams) mTvText.getLayoutParams();
        params.leftMargin = (int) UIUtils.getResources().getDimension(R.dimen.view_line_margin);
    }

    /**
     * 设置文本，并且隐藏图标
     * @param text  文本
     */
    public void setTextAndHideIcon(String text) {
        mIvIcon.setVisibility(View.GONE);
        mTvText.setText(text);
        RelativeLayout.LayoutParams params = (LayoutParams) mTvText.getLayoutParams();
        params.leftMargin = (int) UIUtils.getResources().getDimension(R.dimen.view_line_margin);

    }

    /**
     * 设置左侧图标
     * @param resId 图片资源id
     */
    public void setIcon(int resId) {
        mIvIcon.setImageResource(resId);
    }

    /**
     * 是否展示左侧图标
     * @param isShow  ture 展示 false 不展示
     */
    public void isShowIcon(boolean isShow) {
        mIvIcon.setVisibility(isShow
                              ? View.VISIBLE
                              : View.GONE);
    }

    /**
     * 设置右侧文本，并且隐藏箭头图标
     * @param resId  文本资源id
     */
    public void setRightTextAndHideArrow(int resId) {
        mIvArrow.setVisibility(View.GONE);
        mTvRightText.setText(UIUtils.getString(resId));

    }

    /**
     * 是否显示下划线
     * @param isShow true 显示， false 不不显示
     */
    public void isShowLine(boolean isShow){
        mViewLine.setVisibility(isShow
                              ? View.VISIBLE
                              : View.GONE);
    }
}
