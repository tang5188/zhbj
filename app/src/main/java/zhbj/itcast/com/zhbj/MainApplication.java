package zhbj.itcast.com.zhbj;

import android.app.Application;

import org.xutils.x;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(false);
    }
}
