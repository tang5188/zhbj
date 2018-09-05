package zhbj.itcast.com.zhbj.fragment;

import android.view.View;

import zhbj.itcast.com.zhbj.R;

/**
 * 侧边栏
 */
public class LeftMenuFragment extends BaseFragment {
    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
        return view;
    }
}
