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
import com.tylz.jiaoyanglogistics.activity.GoodsGoActivity;
import com.tylz.jiaoyanglogistics.activity.LoginActivity;
import com.tylz.jiaoyanglogistics.activity.MainUserActivity;
import com.tylz.jiaoyanglogistics.activity.OnlineOrderActivity;
import com.tylz.jiaoyanglogistics.activity.SendGoodRecordActivity;
import com.tylz.jiaoyanglogistics.activity.SignRecordActivity;
import com.tylz.jiaoyanglogistics.activity.TimePriceActivity;
import com.tylz.jiaoyanglogistics.adapter.LoopPicPagerAdapter;
import com.tylz.jiaoyanglogistics.base.BaseFragment;
import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.conf.NetManager;
import com.tylz.jiaoyanglogistics.model.Common;
import com.tylz.jiaoyanglogistics.model.LoopPic;
import com.tylz.jiaoyanglogistics.task.AutoScrollTask;
import com.tylz.jiaoyanglogistics.util.ToastUtils;
import com.tylz.jiaoyanglogistics.util.UIUtils;
import com.tylz.jiaoyanglogistics.view.DAlertDialog;
import com.tylz.jiaoyanglogistics.view.DSearchView;
import com.tylz.jiaoyanglogistics.view.InnerViewPager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

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

    private static final String TAG_ONLINE_ORDER = "online_order";
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
    private int[]                     mLoopIcons = new int[]{R.mipmap.loop1,
                                                             R.mipmap.loop2,
                                                             R.mipmap.loop3,
                                                             R.mipmap.loop4};
    private List<LoopPic.LoopPicInfo> mLoopDatas = new ArrayList<LoopPic.LoopPicInfo>();
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
        loadData_LoopPic();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mAutoScrollTask != null) {
            mAutoScrollTask.stop();
        }
    }

    private void processLoopPicData(LoopPic loopPic) {
        mLoopDatas = loopPic.sliders;
        for (int i = 0; i < loopPic.sliders.size(); i++) {
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

//        int index = Integer.MAX_VALUE / 2;
//        int diffx = Integer.MAX_VALUE / 2 % mLoopDatas.size();
//        index = index - diffx;
//        mViewpager.setCurrentItem(index);
//        mViewpager.getHandler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        },2000);
        autoLoop();
    }

    /**
     * 轮播
     */
    private void autoLoop() {//左右无限轮播
        mViewpager.setCurrentItem(mLoopDatas.size() * 1000);
        //实现自动轮播
        mAutoScrollTask = new AutoScrollTask(mViewpager);
        mAutoScrollTask.start();
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
                loadData_phone();
                break;
            case R.id.home_container_onlineorder:
                if (isLogin()) {
                    return;
                }
                Intent intent1 = new Intent(mContext, OnlineOrderActivity.class);
                startActivity(intent1);
                break;
            case R.id.home_container_timeprice:
                if (isLogin()) {
                    return;
                }
                Intent intent2 = new Intent(mContext, TimePriceActivity.class);
                startActivity(intent2);
                break;
            case R.id.home_container_goodstrack:
                if (isLogin()) {
                    return;
                }
                Intent intent3 = new Intent(mContext, GoodsGoActivity.class);
                startActivity(intent3);
                break;
            case R.id.home_container_signorder:
                if (isLogin()) {
                    return;
                }
                Intent intent4 = new Intent(mContext, SignRecordActivity.class);
                startActivity(intent4);
                break;
            case R.id.home_container_recorders:
                if (isLogin()) {
                    return;
                }
                Intent intent5 = new Intent(mContext, SendGoodRecordActivity.class);
                startActivity(intent5);
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
    private void doClickCustomService(final String phone) {
        new DAlertDialog(mContext).builder()
                                  .setTitle(UIUtils.getString(R.string.tip))
                                  .setMsg(UIUtils.getString(R.string.home_confirm_call))
                                  .setPositiveButton(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          //确定拨打电话
                                          Intent intent = new Intent(Intent.ACTION_CALL,
                                                                     Uri.parse("tel:" + phone));

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

    private void loadData_phone() {
        showProgress();
        OkHttpUtils.post()
                   .url(NetManager.User.CUSTOMER_SERVICE_PHONE)
                   .build()
                   .execute(new DCallback<Common>() {
                       @Override
                       public void onError(Call call, Exception e) {
                           connectError();
                       }

                       @Override
                       public void onResponse(Common response) {
                           if (isSuccess(response)) {
                               doClickCustomService(response.phone);
                           }
                       }
                   });
    }

    private void loadData_LoopPic() {

        OkHttpUtils.post()
                   .url(NetManager.User.SLIDERS)
                   .build()
                   .execute(new DCallback<LoopPic>() {
                       @Override
                       public void onError(Call call, Exception e) {
                           connectError();
                       }

                       @Override
                       public void onResponse(LoopPic response) {
                           if (isSuccess(response)) {
                               processLoopPicData(response);
                           }
                       }
                   });

    }
}
