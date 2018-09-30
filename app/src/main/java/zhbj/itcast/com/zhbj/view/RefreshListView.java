package zhbj.itcast.com.zhbj.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import zhbj.itcast.com.zhbj.R;

/**
 * 下拉刷新的ListView
 */
public class RefreshListView extends ListView {

    private View mHeaderView;
    private int measuredHeight;

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

        //隐藏头布局
        //获取头布局高度，设置负padingTop
        //int height = mHeaderView.getHeight();     //控件没有绘制完成，获取不到height
        mHeaderView.measure(0, 0);   //手动测量，传值为0，表示由系统决定测量
        this.measuredHeight = mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0, -this.measuredHeight, 0, 0);
    }
}
