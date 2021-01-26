package vn.com.nghiemduong.moneykeeper.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/*
    Không cho ViewPager vuốt

    @created_by nxduong 25/1/2021
 */

public class HackyViewPager extends ViewPager {

    private boolean isScrollable = false;
    private boolean smoothScroll = false;

    public HackyViewPager(@NonNull Context context) {
        super(context);
    }

    public HackyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isScrollable) {
            return false;
        }

        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return isScrollable && super.onTouchEvent(event);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, smoothScroll);
    }
}
