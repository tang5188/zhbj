package zhbj.itcast.com.zhbj.base.impl;

import android.app.Activity;
import android.drm.ProcessedData;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import zhbj.itcast.com.zhbj.R;
import zhbj.itcast.com.zhbj.base.BaseMenuDetailPager;
import zhbj.itcast.com.zhbj.domain.NewsMenu;
import zhbj.itcast.com.zhbj.domain.NewsTab;
import zhbj.itcast.com.zhbj.global.GlobalConstants;
import zhbj.itcast.com.zhbj.utils.CacheUtils;

/**
 * 页签详情页：北京/中国/国际……
 * 继承BaseMenuDetailPager，从代码角度来讲比较简洁，但当前页不属于菜单详情页
 */
public class TabDetailPager extends BaseMenuDetailPager {

    //当前页签的网络数据
    private NewsMenu.NewsTabData newsTabData;
    private ArrayList<NewsTab.TopNews> mTopNewsList;
    private String mUrl;

    @ViewInject(R.id.vp_tab_detail)
    private ViewPager mViewPager;

    public TabDetailPager(Activity activity, NewsMenu.NewsTabData newsTabData) {
        super(activity);
        this.newsTabData = newsTabData;
        mUrl = GlobalConstants.SERVER_URL + newsTabData.url;
        System.out.println(newsTabData.title + "url:" + mUrl);
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.pager_tab_detail, null);
        x.view().inject(this, view);

        return view;
    }

    @Override
    public void initData() {
        String cache = CacheUtils.getCache(mActivity, mUrl);
        if (!TextUtils.isEmpty(cache)) {
            processData(cache);
        }
        getDataFromServer();
    }

    //请求服务器获取页签详细数据
    private void getDataFromServer() {
        x.http().get(new RequestParams(mUrl), new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                processData(result);
                CacheUtils.setCache(mActivity, mUrl, result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(mActivity, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    //解析数据
    private void processData(String result) {
        Gson gson = new Gson();
        NewsTab newsTab = gson.fromJson(result, NewsTab.class);
        System.out.println("newsTab:" + newsTab);

        //初始化头条新闻数据
        mTopNewsList = newsTab.data.topnews;
        if (mTopNewsList != null) {
            mViewPager.setAdapter(new TopNewsAdapter());
        }
    }

    //头条新闻的数据解析适配器
    class TopNewsAdapter extends PagerAdapter {

        private ImageOptions imageOptions;

        public TopNewsAdapter() {
            imageOptions = new ImageOptions.Builder()
                    .setLoadingDrawableId(R.drawable.pic_item_list_default)
                    .build();
        }

        @Override
        public int getCount() {
            return mTopNewsList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView view = new ImageView(mActivity);
            view.setScaleType(ImageView.ScaleType.FIT_XY);  //设置缩放模式，图片宽高匹配View

            NewsTab.TopNews topNews = mTopNewsList.get(position);
            String topimage = topNews.topimage;

            //1.根据url下载图片；2.将图片设置给ImageView；3.图片缓存；4.避免内存溢出
            //BitmapUtils：xUtils
            x.image().bind(view, topimage, imageOptions);

            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
