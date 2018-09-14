package zhbj.itcast.com.zhbj.base.impl;

import android.app.Activity;
import android.drm.ProcessedData;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.HttpManager;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import zhbj.itcast.com.zhbj.base.BasePager;
import zhbj.itcast.com.zhbj.domain.NewsMenu;
import zhbj.itcast.com.zhbj.global.GlobalConstants;

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
        //从服务器获取数据
        getDataFromServer();
    }

    //从服务器获取数据
    //  需要添加网络权限
    private void getDataFromServer() {
        x.http().get(new RequestParams(GlobalConstants.CATEGORY_URL), new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                System.out.println("get category list success：" + result);
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(mActivity, "get category list error:" + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(mActivity, "get category list cancel:" + cex.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinished() {
                System.out.println("get category list finished");
            }
        });
    }

    //解析数据
    private void processData(String json) {
        Gson gson = new Gson();
        //通过json和对象类，生成一个对象
        NewsMenu newsMenu = gson.fromJson(json, NewsMenu.class);
        System.out.println(newsMenu.toString());
    }
}
