package zhbj.itcast.com.zhbj.fragment;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import zhbj.itcast.com.zhbj.R;

import static android.view.View.*;

/**
 * 主页面的fragment
 */
public class ContentFragment extends BaseFragment {

    private ViewPager mViewPager;

    @Override
    public View initView() {
        View view = inflate(mActivity, R.layout.fragment_content, null);
        mViewPager = view.findViewById(R.id.vp_content);
        return view;
    }

    @Override
    public void initData() {
        // mViewPager.setAdapter();
    }

    class ContentAdapter extends PagerAdapter {

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
