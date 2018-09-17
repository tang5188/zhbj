package zhbj.itcast.com.zhbj.utils;

import android.content.Context;

public class CacheUtils {

    //写缓存
    //以url为key，以json为值，保存在本地
    public static void setCache(Context context, String key, String json) {
        //有时候也会保存在本地文件中，以MD5(key)为文件名，以json为文件内容，保存在sd卡中
        PrefUtils.putString(context, key, json);
    }

    //读缓存
    public static String getCache(Context context, String key) {
        return PrefUtils.getString(context, key, null);
    }
}
