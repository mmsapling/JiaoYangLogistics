package com.tylz.jiaoyanglogistics.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tylz.jiaoyanglogistics.R;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * @author tylz
 * @time 2016/3/18 0018 15:02
 * @des	   自定义searchview
 *
 * @updateAuthor tylz
 * @updateDate 2016/3/18 0018
 * @updateDes
 */
public class DSearchView extends RelativeLayout implements View.OnTouchListener, TextWatcher {

    @Bind(R.id.dsearch_search)
    ImageView mIvSearch;
    @Bind(R.id.dsearch_edittext)
    EditText mEtInput;
    @Bind(R.id.dsearch_error)
    ImageView mIvError;
    private boolean isEditing = false;
    public DSearchView(Context context) {
        super(context);
        initView();
    }


    public DSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        View.inflate(getContext(), R.layout.view_search, this);
        ButterKnife.bind(this);
        mIvError.setVisibility(GONE);
        mIvSearch.setVisibility(VISIBLE);
        initListener();
    }

    private void initListener() {
        mEtInput.setOnTouchListener(this);
        mEtInput.addTextChangedListener(this);

        mIvError.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtInput.setText("");
                mIvSearch.setVisibility(VISIBLE);
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mIvSearch.setVisibility(GONE);
                isEditing = true;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                //当松开时，如果输入框中的内容为空，搜索图标显示
                String content = mEtInput.getText().toString();
                if(TextUtils.isEmpty(content)){
                    //mIvSearch.setVisibility(VISIBLE);
                    isEditing = false;
                }
                break;

        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            mIvSearch.setVisibility(GONE);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(TextUtils.isEmpty(s)){
            mIvError.setVisibility(GONE);
            //mIvSearch.setVisibility(isEditing ? GONE :VISIBLE);
            if(isEditing){ //如果正在编辑
                mIvSearch.setVisibility(VISIBLE);
                isEditing = true;
            }else{
                mIvSearch.setVisibility(GONE);
                isEditing = false;
            }
            return;
        }
        mIvError.setVisibility(VISIBLE);
    }

    @Override
    public void afterTextChanged(Editable s) {
        if(TextUtils.isEmpty(s)){
            isEditing = false;
        }
    }

    /**
     * @return
     *      返回DSearchView中的EditText控件
     */
    public EditText getEditText(){
        return this.mEtInput;
    }

    /**
     * @return
     *  返回DSearchView中的输入框的文本
     */
    public String getText(){
        return this.mEtInput.getText().toString();
    }
    /**
     * 打开或关闭软件盘
     */
    public void closeInputMethod()
    {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        if (isOpen)
        {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
