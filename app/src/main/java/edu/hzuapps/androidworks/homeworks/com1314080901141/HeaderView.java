package com.example.yys.com1314080901141;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class HeaderView extends LinearLayout{
    private LayoutInflater mInflater;
    private View mHeader;
    private LinearLayout mLayoutLeftContainer;//HeaderView控件左侧容器
    private LinearLayout mLayoutRightContainer;//HeaderView控件右侧容器
    private TextView mTitle;//标题
    private LinearLayout mLayoutRightImageButtonLayout;//右侧按钮布局
    private ImageButton mRightImageButton;//右侧按钮
    //右侧按钮监听接口
    private onRightImageButtonClickListener mRightImageButtonClickListener;
    //左侧按钮布局
    private LinearLayout mLayoutLeftImageButtonLayout;
    //左侧按钮
    private ImageButton mLeftImageButton;
    //左侧按钮监听接口
    private TextView mHtvSubTitle;
    private onLeftImageButtonClickListener mLeftImageButtonClickListener;
    public enum HeaderStyle {// 头部整体样式
        DEFAULT_TITLE,TITLE_LIFT_IMAGEBUTTON,TITLE_RIGHT_IMAGEBUTTON, TITLE_DOUBLE_IMAGEBUTTON
    }
    public HeaderView(Context context) {
        super(context);
        init(context);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    //实现初始化，加载布局文件
    public void init(Context context) {
        mInflater = LayoutInflater.from(context);
        mHeader = mInflater.inflate(R.layout.headerview, null);
        addView(mHeader);
        initViews();
    }
    //初始化控件
    public void initViews(){
        mLayoutLeftContainer
                = (LinearLayout) findViewByHeaderId(R.id.header_layout_leftview_container);
        mLayoutRightContainer
                =(LinearLayout)findViewByHeaderId(R.id.header_layout_rightview_container);
        mHtvSubTitle = (TextView) findViewByHeaderId(R.id.header_htv_subtitle);
    }
    public View findViewByHeaderId(int id) {
        return mHeader.findViewById(id);    }
    //设置控件样式
    public void initStyle(HeaderStyle hStyle) {
        switch (hStyle) {
            case DEFAULT_TITLE:
                defaultTitle();
                break;
            case TITLE_LIFT_IMAGEBUTTON:
                defaultTitle();
                titleLeftImageButton();
                break;

            case TITLE_RIGHT_IMAGEBUTTON:
                defaultTitle();
                titleRightImageButton();
                break;

            case TITLE_DOUBLE_IMAGEBUTTON:
                defaultTitle();
                titleLeftImageButton();
                titleRightImageButton();
                break;
        }
    }

    // 默认文字标题
    private void defaultTitle() {
        mLayoutLeftContainer.removeAllViews();
        mLayoutRightContainer.removeAllViews();
    }

    // 左侧自定义按钮
    private void titleLeftImageButton() {
        View mleftImageButtonView=
                mInflater.inflate(R.layout.include_header_imagebutton, null);
        mLayoutLeftContainer.addView(mleftImageButtonView);
        mLayoutLeftImageButtonLayout =
                (LinearLayout)mleftImageButtonView.findViewById(R.id.header_layout_imagebuttonlayout);
        mLeftImageButton =
                (ImageButton)mleftImageButtonView.findViewById(R.id.header_ib_imagebutton);
        mLayoutLeftImageButtonLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (mLeftImageButtonClickListener != null) {
                    //回调方法，调用onLeftImageButtonClickListener接口实现类的方法
                    mLeftImageButtonClickListener.onClick();
                }
            }
        });
    }

    // 右侧自定义按钮
    private void titleRightImageButton() {
        View mRightImageButtonView
                = mInflater.inflate(R.layout.include_header_imagebutton, null);
        mLayoutRightContainer.addView(mRightImageButtonView);
        mLayoutRightImageButtonLayout =
                (LinearLayout)mRightImageButtonView.findViewById(R.id.header_layout_imagebuttonlayout);
        mRightImageButton =
                (ImageButton)mRightImageButtonView.findViewById(R.id.header_ib_imagebutton);
        mLayoutRightImageButtonLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (mRightImageButtonClickListener != null) {
//回调方法，调用onRightImageButtonClickListener接口实现类的方法

                    mRightImageButtonClickListener.onClick();
                }
            }
        });

    }

    public void setDefaultTitle(CharSequence title) {
        if (title != null) {
            mHtvSubTitle.setText(title);
        } else {
            mHtvSubTitle.setVisibility(View.GONE);
        }
    }
    //重要目的是设置右侧按钮侦听接的实现类，还包括了标题文本、按钮图片
    public void setTitleAndRightImageButton(CharSequence title, int id,
                                            onRightImageButtonClickListener onRightImageButtonClickListener) {
        setDefaultTitle(title);
        if (mRightImageButton != null && id > 0) {
            mRightImageButton.setImageResource(id);
            setOnRightImageButtonClickListener(onRightImageButtonClickListener);
        }
    }
    //重要目的是左侧按钮设置侦听接的实现类还包，括了标题文本、按钮图片
    public void setTitleAndLeftImageButton(CharSequence title, int id,
                                           onLeftImageButtonClickListener listener) {
        setDefaultTitle(title);
        if (mLeftImageButton != null && id > 0) {
            mLeftImageButton.setImageResource(id);
            setOnLeftImageButtonClickListener(listener);
        }
    }

    public void setOnRightImageButtonClickListener(
            onRightImageButtonClickListener listener) {
        mRightImageButtonClickListener = listener;
    }
    //设置右侧按钮监听接口
    public interface onRightImageButtonClickListener {
        void onClick();
    }

    public void setOnLeftImageButtonClickListener(
            onLeftImageButtonClickListener  listener) {
        mLeftImageButtonClickListener = listener;
    }
    //设置左侧按钮监听接口
    public interface onLeftImageButtonClickListener {
        void onClick();
    }

}
