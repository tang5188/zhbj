package zhbj.itcast.com.zhbj.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import zhbj.itcast.com.zhbj.R;

/**
 * 下拉刷新的ListView
 */
public class RefreshListView extends ListView {

    private View mHeaderView;
    private int measuredHeight;

    private static final int STATE_PULL_TO_REFRESH = 0;
    private static final int STATE_RELEASE_TO_REFRESH = 1;
    private static final int STATE_REFRESHING = 2;

    //默认是下拉刷新状态
    private int mCurrentState = STATE_PULL_TO_REFRESH;

    private TextView tvState;
    private TextView tvTime;
    private ImageView ivArrow;
    private ProgressBar pbLoading;

    private RotateAnimation animUp;
    private RotateAnimation animDown;

    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
    }

    //初始化头布局
    private void initHeaderView() {
        mHeaderView = View.inflate(getContext(), R.layout.pull_to_refresh_header, null);
        addHeaderView(mHeaderView); //给listView添加头布局

        tvState = findViewById(R.id.tv_state);
        tvTime = findViewById(R.id.tv_time);
        ivArrow = findViewById(R.id.iv_arrow);
        pbLoading = findViewById(R.id.pb_loading);
        initArrowAnim();

        //隐藏头布局
        //获取头布局高度，设置负padingTop
        //int height = mHeaderView.getHeight();     //控件没有绘制完成，获取不到height
        mHeaderView.measure(0, 0);   //手动测量，传值为0，表示由系统决定测量
        this.measuredHeight = mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0, -this.measuredHeight, 0, 0);
    }

    private int startY = -1;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //没有获取到按下的事件（按住新闻头条滑动时，按下事件被viewPager消费了）
                if (startY == -1) {
                    startY = (int) ev.getY();
                }
                int endY = (int) ev.getY();
                int dy = endY - startY;

                //如果正在刷新，什么都不做
                if (mCurrentState == STATE_REFRESHING) {
                    break;
                }

                int firstVisiblePosition = this.getFirstVisiblePosition();  //当前显示的第一个item的位置
                if (dy > 0 && firstVisiblePosition == 0) {
                    //下拉动作，并且当前的listview的顶部
                    int padding = -this.measuredHeight + dy;

                    if (padding > 0 &&
                            mCurrentState != STATE_RELEASE_TO_REFRESH) {
                        //切换到松开刷新状态
                        mCurrentState = STATE_RELEASE_TO_REFRESH;
                        refreshState();
                    } else if (padding <= 0 &&
                            mCurrentState != STATE_PULL_TO_REFRESH) {
                        //切换到下拉刷新状态
                        mCurrentState = STATE_PULL_TO_REFRESH;
                        refreshState();
                    }
                    //通过修改padding来设置当前刷新控件的最新位置
                    mHeaderView.setPadding(0, padding, 0, 0);
                    //消费此事件，处理下拉刷新控件的滑动，不需要listview原生效果参与
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;

                if (mCurrentState == STATE_RELEASE_TO_REFRESH) {
                    //切换成正在刷新
                    mCurrentState = STATE_REFRESHING;
                    //显示刷新控件
                    mHeaderView.setPadding(0, 0, 0, 0);
                    refreshState();
                } else if (mCurrentState == STATE_PULL_TO_REFRESH) {
                    //隐藏刷新控件
                    mHeaderView.setPadding(0, -measuredHeight, 0, 0);
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void initArrowAnim() {
        animUp = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animUp.setDuration(500);
        animUp.setFillAfter(true);  //保持住动画结束的状态

        animDown = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animDown.setDuration(500);
        animDown.setFillAfter(true);  //保持住动画结束的状态
    }

    //根据当前状态刷新界面
    private void refreshState() {
        switch (mCurrentState) {
            case STATE_PULL_TO_REFRESH:
                tvState.setText("下拉刷新");
                pbLoading.setVisibility(View.INVISIBLE);
                ivArrow.setVisibility(View.VISIBLE);
                ivArrow.startAnimation(animDown);
                break;
            case STATE_RELEASE_TO_REFRESH:
                tvState.setText("松开刷新");
                pbLoading.setVisibility(View.INVISIBLE);
                ivArrow.setVisibility(View.VISIBLE);
                ivArrow.startAnimation(animUp);
                break;
            case STATE_REFRESHING:
                tvState.setText("正在刷新...");
                pbLoading.setVisibility(View.VISIBLE);
                ivArrow.clearAnimation();
                ivArrow.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }
}
