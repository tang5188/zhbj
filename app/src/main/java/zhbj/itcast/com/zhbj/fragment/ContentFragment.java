package zhbj.itcast.com.zhbj.fragment;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import org.xutils.ViewInjector;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import zhbj.itcast.com.zhbj.R;
import zhbj.itcast.com.zhbj.base.BasePager;
import zhbj.itcast.com.zhbj.base.impl.GovAffairsPager;
import zhbj.itcast.com.zhbj.base.impl.HomePager;
import zhbj.itcast.com.zhbj.base.impl.NewsCenterPager;
import zhbj.itcast.com.zhbj.base.impl.SettingPager;
import zhbj.itcast.com.zhbj.base.impl.SmartServicePager;
import zhbj.itcast.com.zhbj.view.NoScrollViewPager;

import static android.view.View.*;

/**
 * 主页面的fragment
 */
public class ContentFragment extends BaseFragment {
    @ViewInject(R.id.vp_content)
    private NoScrollViewPager mViewPager;
    @ViewInject(R.id.rg_group)
    private RadioGroup rgGroup;

    private ArrayList<BasePager> mList;     //5个标签页的集合

    @Override
    public View initView() {
        View view = inflate(mActivity, R.layout.fragment_content, null);
        mViewPager = view.findViewById(R.id.vp_content);
        rgGroup = view.findViewById(R.id.rg_group);
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

        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    //首页
                    case R.id.rb_home:
                        mViewPager.setCurrentItem(0, false);    //false:去掉页面平滑的动画
                        break;
                    //新闻
                    case R.id.rb_news:
                        mViewPager.setCurrentItem(1, false);
                        break;
                    //智慧服务
                    case R.id.rb_smart:
                        mViewPager.setCurrentItem(2, false);
                        break;
                    //政务
                    case R.id.rb_gov:
                        mViewPager.setCurrentItem(3, false);
                        break;
                    //设置
                    case R.id.rb_setting:
                        mViewPager.setCurrentItem(4, false);
                        break;
                    default:
                        break;
                }
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //在此处初始化页面数据
                BasePager pager = mList.get(position);
                pager.InitData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //手动初始化第一个页面的数据
        mList.get(0).InitData();
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

            //默认情况下，viewPager会自动初始化下一个页面，性能不佳，所以不在此处初始化数据
            //pager.InitData();

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
