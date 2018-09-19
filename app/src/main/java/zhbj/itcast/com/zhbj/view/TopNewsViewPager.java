package zhbj.itcast.com.zhbj.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 头条新闻的ViewPager
 */
public class TopNewsViewPager extends ViewPager {

    private int startX;
    private int startY;

    public TopNewsViewPager(@NonNull Context context) {
        super(context);
    }

    public TopNewsViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //1.上下滑动，需要拦截
    //2.左滑动时，最后一个页面需要拦截
    //3.右滑动时，第一个页面需要拦截
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //请求父控件不要拦截
        getParent().requestDisallowInterceptTouchEvent(true);

        switch (ev.getAction()) {
            //按下
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                break;
            //移动
            case MotionEvent.ACTION_MOVE:
                int endX = (int) ev.getX();
                int endY = (int) ev.getY();

                int dx = endX - startX;
                int dy = endY - startY;
                if (Math.abs(dx) > Math.abs(dy)) {
                    int currentItem = getCurrentItem();
                    //左右滑动
                    if (dx > 0) {
                        //右滑动
                        if (currentItem == 0) {
                            //右滑动第一个：父控件拦截滑动
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    } else {
                        //左滑动
                        int count = getAdapter().getCount();    //当前ViewPager的Item总数
                        if (currentItem == count - 1) {
                            //左滑动最后一个：父控件拦截滑动
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                } else {
                    //上下滑动：父控件拦截滑动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }
}
