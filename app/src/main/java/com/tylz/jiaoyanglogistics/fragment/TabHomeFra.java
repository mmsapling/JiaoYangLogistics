package com.tylz.jiaoyanglogistics.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.activity.LoginActivity;
import com.tylz.jiaoyanglogistics.activity.MainUserActivity;
import com.tylz.jiaoyanglogistics.adapter.LoopPicPagerAdapter;
import com.tylz.jiaoyanglogistics.base.BaseFragment;
import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.task.AutoScrollTask;
import com.tylz.jiaoyanglogistics.util.ToastUtils;
import com.tylz.jiaoyanglogistics.util.UIUtils;
import com.tylz.jiaoyanglogistics.view.DAlertDialog;
import com.tylz.jiaoyanglogistics.view.DSearchView;
import com.tylz.jiaoyanglogistics.view.InnerViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author tylz
 * @time 2016/3/20 0020 16:48
 * @des 首页
 * @updateAuthor tylz
 * @updateDate 2016/3/20 0020
 * @updateDes 首页
 */
public class TabHomeFra
        extends BaseFragment
{

    @Bind(R.id.home_searchview)
    DSearchView    mSearchview;
    @Bind(R.id.home_viewpager)
    InnerViewPager mViewpager;
    @Bind(R.id.home_contianer_point)
    LinearLayout   mContianerPoint;
    @Bind(R.id.home_tv_hiddensoft)
    TextView       mTvHiddensoft;//起到隐藏软键盘自动弹出来效果
    @Bind(R.id.home_container_onlineorder)
    LinearLayout   mContainerOnlineorder;
    @Bind(R.id.home_container_timeprice)
    LinearLayout   mContainerTimeprice;
    @Bind(R.id.home_container_goodstrack)
    LinearLayout   mContainerGoodstrack;
    @Bind(R.id.home_container_signorder)
    LinearLayout   mContainerSignorder;
    @Bind(R.id.home_container_recorders)
    LinearLayout   mContainerRecorders;
    @Bind(R.id.home_container_service)
    LinearLayout   mContainerService;
    private MainUserActivity mContext;
    private int[]           mLoopIcons = new int[]{R.mipmap.loop1,
                                                   R.mipmap.loop2,
                                                   R.mipmap.loop3,
                                                   R.mipmap.loop4};
    private List<ImageView> mLoopDatas = new ArrayList<ImageView>();
    private AutoScrollTask mAutoScrollTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = (MainUserActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = View.inflate(getActivity(), R.layout.fra_user_home, null);
        ButterKnife.bind(this, view);
        mTvHiddensoft.requestFocus();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        mAutoScrollTask.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mAutoScrollTask.stop();
    }

    private void initData() {
        for (int i = 0; i < mLoopIcons.length; i++) {
            ImageView view = new ImageView(mContext);
            view.setImageResource(mLoopIcons[i]);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            mLoopDatas.add(view);
            //添加指示器
            ImageView indicator = new ImageView(mContext);
            indicator.setImageResource(R.drawable.shape_point_normal);
            if (i == 0) {
                indicator.setImageResource(R.drawable.shape_point_pressed);
            }
            int width = UIUtils.dp2Px(Constants.POINT_SIZE);
            int height = UIUtils.dp2Px(Constants.POINT_SIZE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            params.leftMargin = UIUtils.dp2Px(Constants.POINT_MARGIN);
            indicator.setLayoutParams(params);
            mContianerPoint.addView(indicator);
        }
        LoopPicPagerAdapter mLoopPicAdapter = new LoopPicPagerAdapter(mContext, mLoopDatas);
        mViewpager.setAdapter(mLoopPicAdapter);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position) {
                position = position % mLoopDatas.size();
                //还原所有的效果
                for (int i = 0; i < mLoopDatas.size(); i++) {
                    ImageView indicator = (ImageView) mContianerPoint.getChildAt(i);
                    indicator.setImageResource(R.drawable.shape_point_normal);
                }
                //设置选中的效果
                ImageView selectIndicator = (ImageView) mContianerPoint.getChildAt(position);
                selectIndicator.setImageResource(R.drawable.shape_point_pressed);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //左右无限轮播
        int index = Integer.MAX_VALUE / 2;
        int diffx = Integer.MAX_VALUE / 2 % mLoopDatas.size();
        index = index - diffx;
        mViewpager.setCurrentItem(index);
        //实现自动轮播
        mAutoScrollTask = new AutoScrollTask(mViewpager);
        //按压下去停止轮播
        mViewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mAutoScrollTask.stop();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        mAutoScrollTask.start();
                        break;
                }

                return false;
            }
        });
    }

    @OnClick({R.id.home_container_onlineorder,
              R.id.home_container_timeprice,
              R.id.home_container_goodstrack,
              R.id.home_container_signorder,
              R.id.home_container_recorders,
              R.id.home_container_service})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_container_service:
                //不用处理登陆事件
                doClickCustomService();
                break;
            case R.id.home_container_onlineorder:
                if (isLogin()) {
                    return;
                }

                break;
            case R.id.home_container_timeprice:
                isLogin();
                break;
            case R.id.home_container_goodstrack:
                isLogin();
                break;
            case R.id.home_container_signorder:
                isLogin();
                break;
            case R.id.home_container_recorders:
                isLogin();
                break;
            default:
                break;

        }
    }

    /**
     *
     * @return true 中断流程 false 继续往下执行
     */
    private boolean isLogin() {
        //判段是否登陆，没有登陆跳转到登陆界面，否则往下执行
        if (!mContext.isLogin()) {
            //到登陆界面
            Intent intent = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(intent);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 处理一键客服
     */
    private void doClickCustomService() {
        new DAlertDialog(mContext).builder()
                                  .setTitle(UIUtils.getString(R.string.tip))
                                  .setMsg(UIUtils.getString(R.string.home_confirm_call))
                                  .setPositiveButton(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          //确定拨打电话
                                          Intent intent = new Intent(Intent.ACTION_CALL,
                                                                     Uri.parse("tel:" + Constants.MOBILE));

                                          if (ActivityCompat.checkSelfPermission(mContext,
                                                                                 Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                                          {
                                              ToastUtils.makePicTextShortToast(mContext,
                                                                               Constants.ICON_TIP,
                                                                               R.string.tip_no_call);
                                              return;
                                          }
                                          mContext.startActivity(intent);

                                      }
                                  })
                                  .setNegativeButton(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          //空实现
                                      }
                                  })
                                  .show();
    }

}
