package com.zjjf.autopos.network;

import static android.R.id.message;

/**
 * Created by ls on 2017/3/14.
 */

public class BaseResponse<T> {
    private boolean success;
    private T msg;
    private String url;
    private String nowTime;

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public T getMsg() {
        return msg;
    }

    public void setMsg(T msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "success=" + success +
                ", message=" + message +
                ", url='" + url + '\'' +
                ", nowTime='" + nowTime + '\'' +
                '}';
    }
}
