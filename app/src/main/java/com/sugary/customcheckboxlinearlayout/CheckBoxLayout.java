package com.sugary.customcheckboxlinearlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import javax.security.auth.login.LoginException;

/**
 * Created by sugarya on 16/5/23.
 * 用来显示星星评分,提供显示/可选中两种方式
 */
public class CheckBoxLayout extends LinearLayout {

    private static final String TAG = "CheckBoxLayout";

    /**
     * 可否选中的使能标识
     */
    private boolean mCheckEnable = true;

    /**
     * 总数量
     */
    private int mBoxCount = 5;

    /**
     * 选中数量
     */
    private int mCheckedCount = 0;

    private
    @DrawableRes
    int mResId;

    private int itemMarginRight;

    private boolean needRun = false;

    private onSelectedListener onSelectedListener;


    public CheckBoxLayout(Context context) {
        super(context);
        initializeLayout(context);
    }

    public CheckBoxLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.checkBoxLayout);
        mCheckEnable = typedArray.getBoolean(R.styleable.checkBoxLayout_checkEnable, true);
        mBoxCount = typedArray.getInteger(R.styleable.checkBoxLayout_boxCount, 5);
        mResId = typedArray.getResourceId(R.styleable.checkBoxLayout_boxSrc, R.drawable.star_selector);
        mCheckedCount = typedArray.getInt(R.styleable.checkBoxLayout_checkedCount, 0);
        itemMarginRight = (int) typedArray.getDimension(R.styleable.checkBoxLayout_item_margin_right, 1);
        typedArray.recycle();
        initializeLayout(getContext());
    }

    private void initializeLayout(Context context) {
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, itemMarginRight, 0);
        for (int i = 0; i < mBoxCount; i++) {
            final int position = i;
            AppCompatCheckBox checkBox = new AppCompatCheckBox(context);
            checkBox.setLayoutParams(layoutParams);
            checkBox.setButtonDrawable(mResId);
            if (i < mCheckedCount) {
                checkBox.setChecked(true);
            }

            checkBox.setEnabled(mCheckEnable);
            checkBox.setTag(position);
            checkBox.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int order = (Integer) v.getTag();
                    initializeCheckedStatus(order);
                    mCheckedCount = position + 1;
                    if (v != null && onSelectedListener != null) {
                        onSelectedListener.onSelected(v, position);
                    }

                }
            });
            if (getChildCount() < mBoxCount) {
                this.addView(checkBox);
            }
        }

    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.e(TAG,"onLayout");
        if (needRun) {
            reDraw();
            needRun = false;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "onDraw");
    }

    private void refreshCheckedStatus(int order) {
        //清空选中状态
        for (int i = 0; i < mBoxCount; i++) {
            CompoundButton childAt = (CompoundButton) getChildAt(i);
            if (childAt != null) {
                childAt.setChecked(false);
            }
        }
        for (int i = 0; i < mBoxCount; i++) {
            CompoundButton childAt = (CompoundButton) getChildAt(i);
            if (childAt != null) {
                if (i < order) {
                    childAt.setChecked(true);
                }
            }
        }
    }

    private void refreshEnable(boolean enable) {
        for (int i = 0; i < mBoxCount; i++) {
            CompoundButton childAt = (CompoundButton) getChildAt(i);
            if (childAt != null) {
                childAt.setEnabled(enable);
            }
        }
    }

    private void refreshResId(@DrawableRes int resId) {
        for (int i = 0; i < mBoxCount; i++) {
            CompoundButton childAt = (CompoundButton) getChildAt(i);
            if (childAt != null) {
                childAt.setButtonDrawable(resId);
            }
        }
    }

    private void initializeCheckedStatus(int order) {
        //清空选中状态
        for (int i = 0; i < mBoxCount; i++) {
            CompoundButton childAt = (CompoundButton) getChildAt(i);
            if (childAt != null) {
                childAt.setChecked(false);
            }
        }
        for (int i = 0; i < mBoxCount; i++) {
            CompoundButton childAt = (CompoundButton) getChildAt(i);
            if (childAt != null) {
                if (i <= order) {
                    childAt.setChecked(true);
                }
            }
        }
    }


    /**
     * 刷新视图
     */
    private void reDraw() {
        Log.e(TAG, "reDraw");
        this.removeAllViews();
        initializeLayout(this.getContext());
    }


    public void setCheckEnable(boolean checkEnable) {
        this.mCheckEnable = checkEnable;
        refreshEnable(checkEnable);
    }

    public void setBoxCount(int boxCount) {
        needRun = true;
        mBoxCount = boxCount;
        requestLayout();
    }

    private void reCountCheckBox(int delta) {
        if(delta >= 0) {
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, itemMarginRight, 0);
            for (int i = 0; i < delta; i++) {
                final int position = i + mBoxCount;
                AppCompatCheckBox checkBox = new AppCompatCheckBox(this.getContext());
                checkBox.setLayoutParams(layoutParams);
                checkBox.setButtonDrawable(mResId);

                checkBox.setEnabled(mCheckEnable);
                checkBox.setTag(position);
                checkBox.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int order = (Integer) v.getTag();
                        initializeCheckedStatus(order);
                        mCheckedCount = position + 1;
                        if (v != null && onSelectedListener != null) {
                            onSelectedListener.onSelected(v, position);
                        }
                    }
                });

                this.addView(checkBox);
            }
        }else{
            for(int i = mBoxCount-1; i >= mBoxCount + delta; i--){
                this.removeViewAt(i);
            }
        }
    }

    public void setCheckedCount(int checkedCount) {
        if (checkedCount <= mBoxCount) {
            this.mCheckedCount = checkedCount;
            refreshCheckedStatus(checkedCount);
        } else {
            throw new IllegalStateException("the count of checked box cannot more than capacity itself");
        }


    }

    public void setResId(int boxResId) {
        mResId = boxResId;
        refreshResId(boxResId);
    }

    public boolean isCheckEnable() {
        return mCheckEnable;
    }

    public int getBoxCount() {
        return mBoxCount;
    }

    public int getCheckedCount() {
        return mCheckedCount;
    }

    public @DrawableRes int getResId() {
        return mResId;
    }


    public void setOnSelectedListener(onSelectedListener onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
    }




    public interface onSelectedListener {
        void onSelected(View view, int position);
    }
}
