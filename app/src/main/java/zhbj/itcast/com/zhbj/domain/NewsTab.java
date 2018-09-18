package zhbj.itcast.com.zhbj.domain;

import java.util.ArrayList;

/**
 * 页签网络数据
 */
public class NewsTab {

    public NewsTabData data;

    public class NewsTabData {
        public String more;
        public ArrayList<TopNews> topnews;
        public ArrayList<TopNews> news;

        @Override
        public String toString() {
            return "NewsTabData{" +
                    "more='" + more + '\'' +
                    ", topnews=" + topnews +
                    ", news=" + news +
                    '}';
        }
    }

    public class TopNews {
        public String id;
        public String title;
        public String pubdate;
        public String topimage;
        public String url;

        @Override
        public String toString() {
            return "TopNews{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }

    public class News {
        public String id;
        public String title;
        public String pubdate;
        public String listimage;
        public String url;

        @Override
        public String toString() {
            return "News{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NewsTab{" +
                "data=" + data +
                '}';
    }
}
