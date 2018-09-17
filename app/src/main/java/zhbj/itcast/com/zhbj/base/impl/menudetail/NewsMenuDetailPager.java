package zhbj.itcast.com.zhbj.base.impl.menudetail;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Array;
import java.util.ArrayList;

import zhbj.itcast.com.zhbj.R;
import zhbj.itcast.com.zhbj.base.BaseMenuDetailPager;
import zhbj.itcast.com.zhbj.base.impl.TabDetailPager;
import zhbj.itcast.com.zhbj.domain.NewsMenu;

public class NewsMenuDetailPager extends BaseMenuDetailPager {

    @ViewInject(R.id.vp_news_menu_detail)
    private ViewPager mViewPager;

    private ArrayList<NewsMenu.NewsTabData> children;

    private ArrayList<TabDetailPager> mPagers;

    public NewsMenuDetailPager(Activity activity, ArrayList<NewsMenu.NewsTabData> children) {
        super(activity);
        this.children = children;
    }

    @Override
    public View initViews() {
//        //给空的帧布局添加布局对象
//        TextView view = new TextView(mActivity);
//        view.setTextSize(22);
//        view.setTextColor(Color.RED);
//        view.setGravity(Gravity.CENTER);    //居中显示
//        view.setText("菜单详情页-新闻");

        View view = View.inflate(mActivity, R.layout.pager_news_menu_detail, null);
        x.view().inject(this, view);

        return view;
    }

    @Override
    public void initData() {
        mPagers = new ArrayList<TabDetailPager>();
        //初始化页签对象，数量以数据为准
        for (int i = 0; i < children.size(); i++) {
            TabDetailPager pager = new TabDetailPager(mActivity, children.get(i));
            mPagers.add(pager);
        }
        mViewPager.setAdapter(new NewsMenuDetailAdapter());
    }

    class NewsMenuDetailAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPagers.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            TabDetailPager pager = mPagers.get(position);
            pager.initData();   //初始化数据
            container.addView(pager.mRootView);

            return pager.mRootView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
