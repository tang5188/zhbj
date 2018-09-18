package zhbj.itcast.com.zhbj.base.impl;

import android.app.Activity;
import android.drm.ProcessedData;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import zhbj.itcast.com.zhbj.R;
import zhbj.itcast.com.zhbj.base.BaseMenuDetailPager;
import zhbj.itcast.com.zhbj.domain.NewsMenu;
import zhbj.itcast.com.zhbj.domain.NewsTab;
import zhbj.itcast.com.zhbj.global.GlobalConstants;

/**
 * 页签详情页：北京/中国/国际……
 * 继承BaseMenuDetailPager，从代码角度来讲比较简洁，但当前页不属于菜单详情页
 */
public class TabDetailPager extends BaseMenuDetailPager {

    //当前页签的网络数据
    private NewsMenu.NewsTabData newsTabData;

    @ViewInject(R.id.vp_tab_detail)
    private ViewPager mViewPager;

    public TabDetailPager(Activity activity, NewsMenu.NewsTabData newsTabData) {
        super(activity);
        this.newsTabData = newsTabData;
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.pager_tab_detail, null);
        x.view().inject(this, view);

        return view;
    }

    @Override
    public void initData() {
        getDataFromServer();
    }

    private void getDataFromServer() {
        x.http().get(new RequestParams(GlobalConstants.SERVER_URL + newsTabData.url), new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                processData(result);
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

    private void processData(String result) {
        Gson gson = new Gson();
        NewsTab newsTab = gson.fromJson(result, NewsTab.class);
        System.out.println("newsTab:" + newsTab);
    }

    class TopNewsAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
