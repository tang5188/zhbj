package zhbj.itcast.com.zhbj.base.impl;

import android.app.Activity;
import android.drm.ProcessedData;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.HttpManager;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Array;
import java.util.ArrayList;

import zhbj.itcast.com.zhbj.MainActivity;
import zhbj.itcast.com.zhbj.base.BaseMenuDetailPager;
import zhbj.itcast.com.zhbj.base.BasePager;
import zhbj.itcast.com.zhbj.base.impl.menudetail.InteractMenuDetailPager;
import zhbj.itcast.com.zhbj.base.impl.menudetail.NewsMenuDetailPager;
import zhbj.itcast.com.zhbj.base.impl.menudetail.PhotosMenuDetailPager;
import zhbj.itcast.com.zhbj.base.impl.menudetail.TopicMenuDetailPager;
import zhbj.itcast.com.zhbj.domain.NewsMenu;
import zhbj.itcast.com.zhbj.fragment.LeftMenuFragment;
import zhbj.itcast.com.zhbj.global.GlobalConstants;
import zhbj.itcast.com.zhbj.utils.CacheUtils;

/**
 * 新闻中心
 */
public class NewsCenterPager extends BasePager {

    private ArrayList<BaseMenuDetailPager> mPagers;
    private NewsMenu newsMenu;

    public NewsCenterPager(Activity activity) {
        super(activity);
    }

    @Override
    public void InitData() {
        System.out.println("新闻中心初始化");

        //没有必要，后续直接替换为新闻、专题、组图、互动等页
//        //给空的帧布局添加布局对象
//        TextView view = new TextView(mActivity);
//        view.setTextSize(22);
//        view.setTextColor(Color.RED);
//        view.setGravity(Gravity.CENTER);    //居中显示
//        view.setText("新闻中心");
//        flContainer.addView(view);      //给帧布局添加对象
//        //修改标题
//        tvTitle.setText("新闻");

        String cache = CacheUtils.getCache(mActivity, GlobalConstants.CATEGORY_URL);
        if (!TextUtils.isEmpty(cache)) {
            System.out.println("发现缓存数据...");
            //有缓存
            processData(cache);
        }
//        else {
//            System.out.println("从服务器获取数据...");
//            //从服务器获取数据
//            getDataFromServer();
//        }

        //继续请求服务器数据，保证缓存最新
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

                //写缓存
                CacheUtils.setCache(mActivity, GlobalConstants.CATEGORY_URL, result);
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
        newsMenu = gson.fromJson(json, NewsMenu.class);
        System.out.println("解析结果：" + newsMenu);

        //找到侧边栏对象
        MainActivity mainUI = (MainActivity) mActivity;
        LeftMenuFragment fragment = mainUI.getLeftMenuFragment();
        fragment.setMenuData(newsMenu.data);

        //网络请求成功之后，初始化四个菜单详情页
        mPagers = new ArrayList<BaseMenuDetailPager>();
        mPagers.add(new NewsMenuDetailPager(mActivity));
        mPagers.add(new TopicMenuDetailPager(mActivity));
        mPagers.add(new PhotosMenuDetailPager(mActivity));
        mPagers.add(new InteractMenuDetailPager(mActivity));
        //设置新闻菜单详情页为默认页面
        setMenuDetailPager(0);
    }

    //修改菜单详情页
    public void setMenuDetailPager(int position) {
        System.out.println("新闻中心要修改菜单详情页：" + position);

        //清除之前帧布局显示的内容
        flContainer.removeAllViews();
        BaseMenuDetailPager pager = mPagers.get(position);
        //修改当前帧布局显示的内容
        flContainer.addView(pager.mRootView);
        //初始化当前页面的数据
        pager.initData();

        //修改标题栏
        tvTitle.setText(newsMenu.data.get(position).title);
    }
}
