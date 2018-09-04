package zhbj.itcast.com.zhbj;

import android.os.Bundle;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

public class MainActivity extends SlidingActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置侧边栏的布局
        setBehindContentView(R.layout.left_menu);

        //获取当前侧边栏布局对象
        SlidingMenu slidingMenu = getSlidingMenu();
        //设置触摸模式为全屏模式
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //设置侧边栏宽度
        slidingMenu.setBehindOffset(900);   //屏幕预留900px的宽度
    }
}
