package zhbj.itcast.com.zhbj.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import zhbj.itcast.com.zhbj.MainActivity;
import zhbj.itcast.com.zhbj.R;

/**
 * 5个标签页的基类
 * 共性：
 * 子类都有标题栏，所以可以直接在父类中加载布局页面
 */
public class BasePager {

    public Activity mActivity;
    public View mRootView;

    public TextView tvTitle;
    public ImageButton btnMenu;
    public FrameLayout flContainer;     //空的帧布局，由子类动态填充布局

    public BasePager(Activity activity) {
        mActivity = activity;
        mRootView = InitViews();
    }

    //初始化布局
    public View InitViews() {
        View view = View.inflate(mActivity, R.layout.base_pager, null);
        tvTitle = view.findViewById(R.id.tv_title);
        btnMenu = view.findViewById(R.id.btn_menu);
        flContainer = view.findViewById(R.id.fl_container);

        //点击侧边栏按钮，控制侧边栏开关
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });

        return view;
    }

    private void toggle() {
        MainActivity mainUI = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        slidingMenu.toggle();
    }

    //初始化数据
    public void InitData() {

    }
}
