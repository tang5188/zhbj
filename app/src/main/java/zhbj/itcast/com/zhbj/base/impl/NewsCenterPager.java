package zhbj.itcast.com.zhbj.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import zhbj.itcast.com.zhbj.base.BasePager;

/**
 * 新闻中心
 */
public class NewsCenterPager extends BasePager {
    public NewsCenterPager(Activity activity) {
        super(activity);
    }

    @Override
    public void InitData() {
        System.out.println("新闻中心初始化");
        //给空的帧布局添加布局对象
        TextView view = new TextView(mActivity);
        view.setTextSize(22);
        view.setTextColor(Color.RED);
        view.setGravity(Gravity.CENTER);    //居中显示
        view.setText("新闻中心");
        flContainer.addView(view);      //给帧布局添加对象
        //修改标题
        tvTitle.setText("新闻");
    }
}
