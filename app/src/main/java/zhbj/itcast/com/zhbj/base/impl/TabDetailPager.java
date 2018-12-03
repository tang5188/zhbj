package zhbj.itcast.com.zhbj.base.impl;

import android.app.Activity;
import android.drm.ProcessedData;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.viewpagerindicator.CirclePageIndicator;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;

import zhbj.itcast.com.zhbj.R;
import zhbj.itcast.com.zhbj.base.BaseMenuDetailPager;
import zhbj.itcast.com.zhbj.domain.NewsMenu;
import zhbj.itcast.com.zhbj.domain.NewsTab;
import zhbj.itcast.com.zhbj.global.GlobalConstants;
import zhbj.itcast.com.zhbj.utils.CacheUtils;
import zhbj.itcast.com.zhbj.view.RefreshListView;
import zhbj.itcast.com.zhbj.view.TopNewsViewPager;

/**
 * 页签详情页：北京/中国/国际……
 * 继承BaseMenuDetailPager，从代码角度来讲比较简洁，但当前页不属于菜单详情页
 */
public class TabDetailPager extends BaseMenuDetailPager {

    //当前页签的网络数据
    private NewsMenu.NewsTabData newsTabData;
    private ArrayList<NewsTab.TopNews> mTopNewsList;
    private ArrayList<NewsTab.News> mNewsList;
    private String mUrl;

    @ViewInject(R.id.vp_tab_detail)
    private TopNewsViewPager mViewPager;
    @ViewInject(R.id.tv_title)
    private TextView tvTitle;
    @ViewInject(R.id.indicator)
    private CirclePageIndicator mIndicator;
    @ViewInject(R.id.lv_list)
    private RefreshListView lvList;

    public TabDetailPager(Activity activity, NewsMenu.NewsTabData newsTabData) {
        super(activity);
        this.newsTabData = newsTabData;
        mUrl = GlobalConstants.SERVER_URL + newsTabData.url;
        System.out.println(newsTabData.title + "url:" + mUrl);
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.pager_tab_detail, null);
        //加载头条新闻的头布局
        View headerView = View.inflate(mActivity, R.layout.list_item_header, null);

        x.view().inject(this, view);
        x.view().inject(this, headerView);

        lvList.addHeaderView(headerView);   //给listView添加头布局

        //设置下拉刷新的监听
        lvList.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void OnRefresh() {
                System.out.println("下拉刷新...");
                getDataFromServer();
            }
        });

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

                System.out.println("result:" + result);
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
                //隐藏下拉刷新控件
                lvList.OnRefreshComplete();
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
            //将圆形指示器绑定到ViewPager
            mIndicator.setViewPager(mViewPager);
            mIndicator.setSnap(true);   //快照展示方式
            mIndicator.onPageSelected(0); //将圆点位置归零，保证页面和圆点同步
            mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    //更新头条新闻的标题
                    tvTitle.setText(mTopNewsList.get(position).title);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            //初始化第一页头条新闻的标题
            if (mTopNewsList.size() > 0) {
                tvTitle.setText(mTopNewsList.get(0).title);
            }
        }

        //初始化新闻列表数据
        mNewsList = newsTab.data.news;
        if (mNewsList != null) {
            lvList.setAdapter(new NewsAdapter());
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

    //新闻列表适配器
    class NewsAdapter extends BaseAdapter {

        private ImageOptions imageOptions = null;

        public NewsAdapter() {
            imageOptions = new ImageOptions.Builder()
                    .setLoadingDrawableId(R.drawable.pic_item_list_default)
                    .build();
        }

        @Override
        public int getCount() {
            return mNewsList.size();
        }

        @Override
        public NewsTab.News getItem(int position) {
            return mNewsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.list_item_news, null);

                holder = new ViewHolder();
                holder.ivIcon = convertView.findViewById(R.id.iv_icon);
                holder.tvTitle = convertView.findViewById(R.id.tv_title);
                holder.tvTime = convertView.findViewById(R.id.tv_time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            NewsTab.News info = getItem(position);
            holder.tvTitle.setText(info.title);
            holder.tvTime.setText(info.pubdate);
            x.image().bind(holder.ivIcon, info.listimage, imageOptions);

            return convertView;
        }
    }

    static class ViewHolder {
        public ImageView ivIcon;
        public TextView tvTitle;
        public TextView tvTime;
    }
}
