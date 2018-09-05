package zhbj.itcast.com.zhbj.fragment;

import android.view.View;

import zhbj.itcast.com.zhbj.R;

import static android.view.View.*;

/**
 * 主页面的fragment
 */
public class ContentFragment extends BaseFragment {
    @Override
    public View initView() {
        View view = inflate(mActivity, R.layout.fragment_content, null);
        return view;
    }
}
