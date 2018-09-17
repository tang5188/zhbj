package zhbj.itcast.com.zhbj.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.NestedScrollingChild;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import zhbj.itcast.com.zhbj.base.BaseMenuDetailPager;
import zhbj.itcast.com.zhbj.domain.NewsMenu;

/**
 * 页签详情页：北京/中国/国际……
 * 继承BaseMenuDetailPager，从代码角度来讲比较简洁，但当前页不属于菜单详情页
 */
public class TabDetailPager extends BaseMenuDetailPager {

    //当前页签的网络数据
    private NewsMenu.NewsTabData newsTabData;

    private TextView view;

    public TabDetailPager(Activity activity, NewsMenu.NewsTabData newsTabData) {
        super(activity);
        this.newsTabData = newsTabData;
    }

    @Override
    public View initViews() {

        //给空的帧布局添加布局对象
        view = new TextView(mActivity);
        view.setTextSize(22);
        view.setTextColor(Color.RED);
        view.setGravity(Gravity.CENTER);    //居中显示
        view.setText("页签");

        return view;
    }

    @Override
    public void initData() {
        view.setText(newsTabData.title);    //膝盖当前布局数据
    }
}
