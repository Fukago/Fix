package com.skylinetan.energycloud.support;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skylinetan.energycloud.R;

import java.util.List;

/**
 * Created by skylineTan on 16/12/2.
 */
public class ViewPagerIndicator extends LinearLayout{

    private Paint mPaint;
    private Path mPath;
    private int mTriangleWidth;
    private int mTriangleHeight;
    private static final float RADIO_TRIANGEL = 1.0f / 6;
    private final int DIMENSION_TRIANGEL_WIDTH = (int) (getScreenWidth() / 3 * RADIO_TRIANGEL);
    private int mInitTranslationX;
    private float mTranslationX;
    private static final int COUNT_DEFAULT_TAB = 4;
    private int mTabVisibleCount = COUNT_DEFAULT_TAB;
    private List<String> mTabTitles;
    private ViewPager mViewPager;
    private static final int COLOR_TEXT_NORMAL = 0x77FFFFFF;
    private static final int COLOR_TEXT_HIGHLIGHTCOLOR = 0xFFFFFFFF;

    private PageChangeListener onPageChangeListener;

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewPageIndicator);
        mTabVisibleCount = a.getInt(R.styleable.ViewPageIndicator_item_count, COUNT_DEFAULT_TAB);
        if(mTabVisibleCount < 0)
            mTabVisibleCount = COUNT_DEFAULT_TAB;
        a.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#ffffffff"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(3));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int cCount = getChildCount();

        if(cCount == 0)
            return;

        for(int i = 0;i< cCount;i++) {
            View view = getChildAt(i);
            LinearLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
            lp.weight = 0;
            lp.width = getScreenWidth() / mTabVisibleCount;
            view.setLayoutParams(lp);
        }
        setItemClickEvent();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //每一个tab的长度*1/6
        mTriangleWidth = (int)(w / mTabVisibleCount * RADIO_TRIANGEL);

        mTriangleWidth = Math.min(DIMENSION_TRIANGEL_WIDTH, mTriangleWidth);

        initTriangle();

        mInitTranslationX = getWidth() / mTabVisibleCount / 2 - mTriangleWidth / 2;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(mInitTranslationX + mTranslationX, getHeight() + 1);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    public void setViewPager(ViewPager mViewPager, int pos){
        this.mViewPager = mViewPager;

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                scroll(position, positionOffset);
                if(onPageChangeListener != null){
                    onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                resetTextViewColor();
                highLightTextView(position);
                if(onPageChangeListener != null){
                    onPageChangeListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(onPageChangeListener != null){
                    onPageChangeListener.onPageScrollStateChanged(state);
                }
            }
        });

        mViewPager.setCurrentItem(pos);
        highLightTextView(pos);
    }

    private void highLightTextView(int position){
        View view = getChildAt(position);
        if(view instanceof TextView){
            ((TextView)view).setTextColor(COLOR_TEXT_HIGHLIGHTCOLOR);
        }
    }

    private void resetTextViewColor(){
        for(int i = 0; i<getChildCount();i++){
            View view = getChildAt(i);
            if(view instanceof TextView){
                ((TextView)view).setTextColor(COLOR_TEXT_NORMAL);
            }
        }
    }

    private void scroll(int position, float offset){
        mTranslationX = getWidth() / mTabVisibleCount * (position + offset);

        int tabWidth = getScreenWidth() / mTabVisibleCount;

        if(offset > 0 && position >= (mTabVisibleCount - 2) && getChildCount() > mTabVisibleCount){
            if(mTabVisibleCount != 1){
                this.scrollTo((position - (mTabVisibleCount - 2)) * tabWidth + (int)(tabWidth * offset),0);
            }else {
                this.scrollTo(position * tabWidth + (int)(tabWidth * offset), 0);
            }
        }

        invalidate();
    }

    private void initTriangle() {
        mPath = new Path();

        mTriangleHeight = (int)(mTriangleWidth / 2 / Math.sqrt(2));
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.close();
    }

    public int getScreenWidth(){
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    private void setItemClickEvent(){
        int cCount = getChildCount();
        for(int i = 0;i<cCount;i++){
            final int j = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mViewPager.setCurrentItem(j);
                }
            });
        }
    }

    public void setTabVisibleCount(int count){
        this.mTabVisibleCount = count;
    }

    public void setTabItemTitles(List<String> datas){
        if(datas != null && datas.size() > 0){
            this.removeAllViews();
            this.mTabTitles = datas;
            for(String title:mTabTitles){
                addView(generateTextView(title));
            }
            setItemClickEvent();
        }
    }

    private TextView generateTextView(String text){
        TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.width = getScreenWidth() / mTabVisibleCount;
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(COLOR_TEXT_NORMAL);
        tv.setText(text);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setLayoutParams(lp);
        return tv;
    }

    public interface PageChangeListener{

        void onPageScrolled(int position, float positionoffset, int positionOffsetPixels);

        void onPageSelected(int position);

        void onPageScrollStateChanged(int state);
    }

    public void setOnPageChangeListener(PageChangeListener pageChangeListener){
        this.onPageChangeListener = pageChangeListener;
    }
}
