package zhbj.itcast.com.zhbj;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import zhbj.itcast.com.zhbj.fragment.ContentFragment;
import zhbj.itcast.com.zhbj.fragment.LeftMenuFragment;

/**
 * 主页面
 * 1.当一个activity要展示fragment时，必须继承FragmentActivity
 * 2.使用FragmentManager进行布局替换
 * 3.将现有布局掏空，根布局建议使用FrameLayout
 * 4.开始事务，进行替换操作，并提交
 */
public class MainActivity extends SlidingFragmentActivity {

    private static final String TAG_CONTENT = "TAG_CONTENT";
    private static final String TAG_LEFT_MENU = "TAG_LEFT_MENU";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //设置侧边栏的布局
        setBehindContentView(R.layout.left_menu);

        //获取当前侧边栏布局对象
        SlidingMenu slidingMenu = getSlidingMenu();
        //设置触摸模式为全屏模式
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //设置侧边栏宽度
        slidingMenu.setBehindOffset(900);   //屏幕预留900px的宽度

        initFragment();
    }

    /**
     * 初始化fragment
     */
    private void initFragment() {
        //获取fragment的管理器
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction(); //开启一个事务
        transaction.replace(R.id.fl_content, new ContentFragment(), TAG_CONTENT);  //使用fragment替换现有布局，参1：当前布局id，参2：要替换的布局
        transaction.replace(R.id.fl_left_menu, new LeftMenuFragment(), TAG_LEFT_MENU);  //TAG标记，方便以后找到对象
        transaction.commit();       //提交事务

        //通过此方法，查找fragment对象
        //ContentFragment fragment = (ContentFragment) fm.findFragmentByTag(TAG_CONTENT);
    }

    //返回侧边栏对象
    public LeftMenuFragment getLeftMenuFragment() {
        FragmentManager fm = getSupportFragmentManager();
        LeftMenuFragment fragment = (LeftMenuFragment) fm.findFragmentByTag(TAG_LEFT_MENU);
        return fragment;
    }
}
