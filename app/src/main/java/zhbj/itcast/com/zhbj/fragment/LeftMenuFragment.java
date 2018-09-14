package zhbj.itcast.com.zhbj.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import zhbj.itcast.com.zhbj.MainActivity;
import zhbj.itcast.com.zhbj.R;
import zhbj.itcast.com.zhbj.domain.NewsMenu;

/**
 * 侧边栏
 */
public class LeftMenuFragment extends BaseFragment {
    @ViewInject(R.id.lv_menu)
    private ListView lvList;

    private ArrayList<NewsMenu.NewsMenuData> data;  //分类的网络数据


    private int mCurrentPos;    //当前选中的菜单位置

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
        x.view().inject(this, view);
        return view;
    }

    //通过此方法，可以从新闻中心将网络数据传递过来
    public void setMenuData(ArrayList<NewsMenu.NewsMenuData> data) {
        System.out.println("侧边栏拿到的数量：" + data.size());
        this.data = data;

        final LeftMenuAdapter mAdapter = new LeftMenuAdapter();
        lvList.setAdapter(mAdapter);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurrentPos = position;     //更新当前点击位置
                //刷新ListView
                mAdapter.notifyDataSetChanged();

                //收回侧边栏
                toggle();
            }
        });
    }

    private void toggle() {
        MainActivity mainUI = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        slidingMenu.toggle();
    }

    class LeftMenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public NewsMenu.NewsMenuData getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mActivity, R.layout.list_item_left_menu, null);
            TextView tvMenu = view.findViewById(R.id.tv_menu);

            //设置按钮的可用或不可用，控制按钮的颜色
            if (mCurrentPos == position) {
                //当前item被选中
                tvMenu.setEnabled(true);
            } else {
                //未选中
                tvMenu.setEnabled(false);
            }

            NewsMenu.NewsMenuData info = getItem(position);
            tvMenu.setText(info.title);

            return view;
        }
    }
}
