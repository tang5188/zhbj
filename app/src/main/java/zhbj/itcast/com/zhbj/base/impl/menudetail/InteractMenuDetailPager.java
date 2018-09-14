package zhbj.itcast.com.zhbj.base.impl.menudetail;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import zhbj.itcast.com.zhbj.base.BaseMenuDetailPager;

public class InteractMenuDetailPager extends BaseMenuDetailPager {
    public InteractMenuDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initViews() {
        //给空的帧布局添加布局对象
        TextView view = new TextView(mActivity);
        view.setTextSize(22);
        view.setTextColor(Color.RED);
        view.setGravity(Gravity.CENTER);    //居中显示
        view.setText("菜单详情页-互动");
        return view;
    }
}
