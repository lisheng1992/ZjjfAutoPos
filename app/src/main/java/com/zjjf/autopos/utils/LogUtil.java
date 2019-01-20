package com.zjjf.autopos.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *  logz封装
 * Created by yekai on 17-5-4.
 */

public class LogUtil {

    // 是否屏蔽Log日志, 建议在Application中初始化, 默认为true输出日志
    public static boolean isDebug = true;
    private static final String TAG = "LogUtil";

    private LogUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }

    public static String getParams(Map<String, Object> requst) {
        if (requst != null) {
            String param = null;
            // String url = makeBaseUrl(module);
            List<String> keyList = new ArrayList<>();{
                Set set = requst.keySet();
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    keyList.add(key);
                }
            }
            for (int i = 0; i < keyList.size(); i++) {

                String key = keyList.get(i);
                Object val = requst.get(key);

                if (param == null) {
                    param = "?"+key + "=" + val;
                } else {
                    param += "&" + key + "=" + val;
                }
            }
            keyList = null;
            if (param == null) return null;
            return param;
        } else {
            return "";
        }
    }

}
