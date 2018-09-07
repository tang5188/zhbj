package zhbj.itcast.com.zhbj.fragment;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import zhbj.itcast.com.zhbj.R;
import zhbj.itcast.com.zhbj.base.BasePager;
import zhbj.itcast.com.zhbj.base.impl.GovAffairsPager;
import zhbj.itcast.com.zhbj.base.impl.HomePager;
import zhbj.itcast.com.zhbj.base.impl.NewsCenterPager;
import zhbj.itcast.com.zhbj.base.impl.SettingPager;
import zhbj.itcast.com.zhbj.base.impl.SmartServicePager;

import static android.view.View.*;

/**
 * 主页面的fragment
 */
public class ContentFragment extends BaseFragment {

    private ViewPager mViewPager;
    private ArrayList<BasePager> mList;     //5个标签页的集合

    @Override
    public View initView() {
        View view = inflate(mActivity, R.layout.fragment_content, null);
        mViewPager = view.findViewById(R.id.vp_content);
        return view;
    }

    @Override
    public void initData() {
        //初始化5个标签页面对象
        mList = new ArrayList<BasePager>();
        mList.add(new HomePager(mActivity));
        mList.add(new NewsCenterPager(mActivity));
        mList.add(new SmartServicePager(mActivity));
        mList.add(new GovAffairsPager(mActivity));
        mList.add(new SettingPager(mActivity));
        mViewPager.setAdapter(new ContentAdapter());
    }

    class ContentAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            //获取当前页面的对象
            BasePager pager = mList.get(position);
            pager.InitData();
            //布局对象
            //pager.mRootView：当前页面的根布局
            container.addView(pager.mRootView);

            return pager.mRootView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
