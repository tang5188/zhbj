package zhbj.itcast.com.zhbj;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import zhbj.itcast.com.zhbj.utils.PrefUtils;

public class GuideActivity extends Activity {

    private ViewPager mViewPager;
    private LinearLayout llContainer;
    private ImageView ivRedPoint;
    private Button btnStart;

    private int[] mImageIds = new int[]{R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};
    private ArrayList<ImageView> mImageViews;

    private int mPointDis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initViews();
        initData();
    }

    private void initViews() {
        mViewPager = findViewById(R.id.vp_guide);
        llContainer = findViewById(R.id.ll_container);
        ivRedPoint = findViewById(R.id.iv_red_point);
        btnStart = findViewById(R.id.btn_Start);
    }

    private void initData() {
        mImageViews = new ArrayList<>();
        for (int i = 0; i < mImageIds.length; i++) {
            ImageView view = new ImageView(this);
            view.setBackgroundResource(mImageIds[i]);
            mImageViews.add(view);

            //初始化小圆点
            ImageView point = new ImageView(this);
            point.setImageResource(R.drawable.shape_point_normal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                params.leftMargin = 15;
            }
            point.setLayoutParams(params);
            llContainer.addView(point);
        }
        mViewPager.setAdapter(new GuideAdapter());

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //通过修改小红点的左边距，设定小红点的位置
                int leftMargin = (int) (mPointDis * positionOffset + position * mPointDis + 0.5f);
                //获取小红点的布局参数
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivRedPoint.getLayoutParams();
                params.leftMargin = leftMargin;
                ivRedPoint.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                if (position == mImageIds.length - 1) {
                    btnStart.setVisibility(View.VISIBLE);
                } else {
                    btnStart.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //监听layout执行结束的事件，一旦结束之后，执行测量逻辑
        //视图树
        ivRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            //一旦视图树Layout方法调用完成，就会回调此方法
            @Override
            public void onGlobalLayout() {
                //布局位置已经确定，可以拿到位置信息了
                //计算圆点移动距离
                mPointDis = llContainer.getChildAt(1).getLeft() -
                        llContainer.getChildAt(0).getLeft();
                //移除观察者
                ivRedPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        //开始体验按钮点击
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //在sp中记录访问过引导页的状态
                PrefUtils.putBoolean(getApplicationContext(), "is_guide_show", true);
                //跳到主页面
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }

    private class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageIds.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView view = mImageViews.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
