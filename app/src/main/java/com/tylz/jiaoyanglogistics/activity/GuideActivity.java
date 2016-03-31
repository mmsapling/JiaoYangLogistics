package com.tylz.jiaoyanglogistics.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.adapter.GuidePageAdapter;
import com.tylz.jiaoyanglogistics.base.BaseActivity;
import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.util.UIUtils;
import com.tylz.jiaoyanglogistics.view.ControlScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author tylz
 * @time 2016/3/19 0019 09:18
 * @des 引导页面
 * @updateAuthor
 * @updateDate 2016/3/19 0019
 * @updateDes 引导页面
 */

public class GuideActivity
        extends BaseActivity
        implements ViewPager.OnPageChangeListener, View.OnClickListener
{


    @Bind(R.id.guide_viewpager)
    ControlScrollViewPager mGuideViewpager;
    @Bind(R.id.guide_container_point)
    LinearLayout           mGuideContainerPoint;
    @Bind(R.id.guide)
    ImageView              mGuideSelectPoint;
    @Bind(R.id.guide_btn)
    Button                 mGuideBtn;
    private int[] mGuideIcons = new int[]{R.mipmap.guide_1,
                                          R.mipmap.guide_2,
                                          R.mipmap.guide_3};
    private List<ImageView> mImageDatas;
    private int             mPointSpace;
    private boolean isEnable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();

    }

    private void initView() {
        //设置全局布布局布局完成改变时的回调
        mGuideSelectPoint.getViewTreeObserver()
                         .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                             @Override
                             public void onGlobalLayout() {
                                 int left1 = mGuideContainerPoint.getChildAt(1)
                                                                 .getLeft();
                                 int left0 = mGuideContainerPoint.getChildAt(0)
                                                                 .getLeft();
                                 mPointSpace = left1 - left0;
                                 //移除监听
                                 mGuideSelectPoint.getViewTreeObserver()
                                                  .removeGlobalOnLayoutListener(this);
                             }
                         });
    }

    /**
     * 事件监听
     */
    private void initListener() {
        mGuideViewpager.setOnPageChangeListener(this);
        mGuideBtn.setOnClickListener(this);
    }


    /**
     * 初始化数据
     */
    private void initData() {
        mImageDatas = new ArrayList<ImageView>();
        for (int i = 0; i < mGuideIcons.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setImageResource(mGuideIcons[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            //添加到集合中
            mImageDatas.add(iv);
            //动态的添加点
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.shape_point_normal);
            //点的大小为10dp
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UIUtils.dp2Px(Constants.POINT_SIZE),
                                                                             UIUtils.dp2Px(Constants.POINT_SIZE));
            if (i != 0) {
                params.leftMargin = UIUtils.dp2Px(Constants.POINT_MARGIN);
            }
            point.setLayoutParams(params);
            mGuideContainerPoint.addView(point);
        }
        mGuideViewpager.setAdapter(new GuidePageAdapter(this, mImageDatas));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //positionOffset 滑动的比例
        // positionOffsetPixels 滑动的像素
        //偏移量 marginleft
        int                         marginLeft = (int) (mPointSpace * positionOffset + mPointSpace * position + 0.5f);
        RelativeLayout.LayoutParams params     = (RelativeLayout.LayoutParams) mGuideSelectPoint.getLayoutParams();
        params.leftMargin = marginLeft;
        mGuideSelectPoint.setLayoutParams(params);
    }

    @Override
    public void onPageSelected(int position) {
        //设置buttun的显示和隐藏
        if (position == mImageDatas.size() - 1) {
            mGuideBtn.setVisibility(View.VISIBLE);
        } else {
            mGuideBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.guide_btn:
                /*
                  让用户选择，是司机端还是用户端， 一旦选择后，以后进入都是选择的那一端
                 */
                selectAPP();
                break;
            default:
                break;
        }
    }

    private void selectAPP() {
        final PopupWindow popupWindow = new PopupWindow(UIUtils.dp2Px(100), UIUtils.dp2Px(140));
        View        view        = View.inflate(this, R.layout.dialog_selectapp, null);
        Button      btDriver    = (Button) view.findViewById(R.id.guide_btn_driver);
        Button      btUser      = (Button) view.findViewById(R.id.guide_btn_user);
        btUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择用户端
                mSpUtils.putBoolean(Constants.IS_DRIVER, false);
                //mSpUtils.putBoolean(Constants.IS_FIRST_ENTER,false);
                //进入到用户端
                Intent intent = new Intent(GuideActivity.this, MainUserActivity.class);
                startActivity(intent);
                popupWindow.dismiss();
                finish();
            }
        });
        btDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择司机端
                mSpUtils.putBoolean(Constants.IS_DRIVER, true);
                //mSpUtils.putBoolean(Constants.IS_FIRST_ENTER,false);
                //进入到司机端
                finish();
            }
        });
        popupWindow.setContentView(view);
        int[] location = new int[2];
        getShowLocation(location);
        popupWindow.showAsDropDown(mGuideBtn, location[0], location[1]);
        //设置 开始体验按钮不能点击
        mGuideBtn.setEnabled(false);
        //设置viewpager不能滑动
        mGuideViewpager.setScrollable(false);
    }


    private void getShowLocation(int[] location) {
        WindowManager wm = getWindowManager();
        int height = wm.getDefaultDisplay()
                       .getHeight();

        location[0] = 0; //宽
        location[1] = -height / 2; // 高
    }


    /**
     * 一次性选择，如果用户选择了一端，那么以后他只能进入这一端
     * 小米手机升级后出现问题 改用PopupWindow做
     */
    @Deprecated
    private void selectAPP1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog         dialog  = builder.create();
        dialog.show();
        View   view     = View.inflate(this, R.layout.dialog_selectapp, null);
        Button btDriver = (Button) view.findViewById(R.id.guide_btn_driver);
        Button btUser   = (Button) view.findViewById(R.id.guide_btn_user);
        btUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择用户端
                mSpUtils.putBoolean(Constants.IS_DRIVER, false);
                //mSpUtils.putBoolean(Constants.IS_FIRST_ENTER,false);
                //进入到用户端
                Intent intent = new Intent(GuideActivity.this, MainUserActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择司机端
                mSpUtils.putBoolean(Constants.IS_DRIVER, true);
                //mSpUtils.putBoolean(Constants.IS_FIRST_ENTER,false);
                //进入到司机端
                finish();
            }
        });
        Window window = dialog.getWindow();
        window.setContentView(view);
        window.setLayout(UIUtils.dp2Px(100), UIUtils.dp2Px(140));
    }


}
