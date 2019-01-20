package com.zjjf.autopos.utils;

import android.content.Context;

/**
 * 作者：Hao on 2017/6/26 17:27
 * 邮箱：shengxuan@izjjf.cn
 */

public class OperateSharedUtils {
    public static String getStoreName(Context context) {
        return (String) SharedUtils.get(context, "StoreInfo", "StoreName", "");
    }

    public static void putStoreName(Context context, String storeName) {
        SharedUtils.put(context, "StoreInfo", "StoreName", storeName);
    }

    public static String getUserName(Context context) {
        return (String) SharedUtils.get(context, "UserInfo", "UserName", "");
    }

    public static void putUserName(Context context, String userName) {
        SharedUtils.put(context, "UserInfo", "UserName", userName);
    }

    public static void putPrintNum(Context context, int printNum) {
        SharedUtils.put(context, "PrintInfo", "PrintNum", printNum);
    }

    public static int getPrintNum(Context context) {
        return (int) SharedUtils.get(context, "PrintInfo", "PrintNum", 1);
    }

    public static void putPrintStr(Context context, String str) {
        SharedUtils.put(context, "PrintInfo", "PrintStr", str);
    }

    public static String getPrintStr(Context context) {
        return (String) SharedUtils.get(context, "PrintInfo", "PrintStr", "");
    }

    public static void putLoginStatus(Context context, boolean str) {
        SharedUtils.put(context, "LoginInfo", "LoginStatus", str);
    }

    public static boolean getLoginStatus(Context context) {
        return (boolean) SharedUtils.get(context, "LoginInfo", "LoginStatus", false);
    }

    public static void putPrintStatus(Context context, boolean str) {
        SharedUtils.put(context, "PrintSet", "PrintStatus", str);
    }

    public static boolean getPrintStatus(Context context) {
        return (boolean) SharedUtils.get(context, "PrintSet", "PrintStatus", false);
    }

    public static String getStoreId(Context context) {
        return (String) SharedUtils.get(context, "StoreInfo", "StoreId", "");
    }

    public static void putStoreId(Context context, String storeName) {
        SharedUtils.put(context, "StoreInfo", "StoreId", storeName);
    }

    public static boolean getTakeOutRemind(Context context) {
        return (boolean) SharedUtils.get(context, "TakeOutInfo", "Remind", false);
    }

    public static void putTakeOutRemind(Context context, boolean remind) {
        SharedUtils.put(context, "TakeOutInfo", "Remind", remind);
    }
}
