package zhbj.itcast.com.zhbj.domain;

import java.util.ArrayList;

/**
 * 新闻菜单分类数据的封装
 */
public class NewsMenu {

    public int retcode;

    public ArrayList<NewsMenuData> data;

    public ArrayList<String> extend;

    //四个分类菜单的信息：新闻、专题、组图、互动
    public class NewsMenuData {
        public String id;
        public String title;
        public int type;
        public ArrayList<NewsTabData> children;

        @Override
        public String toString() {
            return "NewsMenuData{" +
                    "title='" + title + '\'' +
                    ", children=" + children +
                    '}';
        }
    }

    //12个页签的对象
    public class NewsTabData {
        public String id;
        public String title;
        public int type;
        public String url;

        @Override
        public String toString() {
            return "NewsTabData{" +
                    "title='" + title + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NewsMenu{" +
                "data=" + data +
                '}';
    }
}
