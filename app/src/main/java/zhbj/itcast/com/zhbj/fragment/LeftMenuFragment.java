package zhbj.itcast.com.zhbj.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import zhbj.itcast.com.zhbj.R;
import zhbj.itcast.com.zhbj.domain.NewsMenu;

/**
 * 侧边栏
 */
public class LeftMenuFragment extends BaseFragment {
    @ViewInject(R.id.lv_menu)
    private ListView lvList;

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
        x.view().inject(this, view);
        return view;
    }

    //通过此方法，可以从新闻中心将网络数据传递过来
    public void setMenuData(ArrayList<NewsMenu.NewsMenuData> data) {
        System.out.println("侧边栏拿到的数量：" + data.size());
    }

    class LeftMenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mActivity, R.layout.list_item_left_menu, null);
            TextView tvMenu = view.findViewById(R.id.tv_menu);
            return view;
        }
    }
}
