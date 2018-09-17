package zhbj.itcast.com.zhbj.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 禁止滑动的ViewPager
 */
public class NoScrollViewPager extends ViewPager {
    public NoScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //重写父类的onTouchEvent，此处什么都不做，从而达到禁用事件的目的
        return true;
    }

    //事件中断、拦截
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //false代表不拦截滑档事件，传给子控件
        return false;   //标签ViewPager不拦截页签的ViewPager
    }
}
