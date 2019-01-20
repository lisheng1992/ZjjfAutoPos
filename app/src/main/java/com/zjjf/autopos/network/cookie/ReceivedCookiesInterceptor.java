package com.zjjf.autopos.network.cookie;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.socks.library.KLog;
import com.zjjf.autopos.ZjjfPosApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * copy by 孟家敏 on 16/12/8 15:51
 * <p>
 * 邮箱：androidformjm@sina.com
 * Created by Tamic on 2016-12-08.
 */
public class ReceivedCookiesInterceptor implements Interceptor {
    SharedPreferences sharedPreferences;

    public ReceivedCookiesInterceptor() {
        super();
        sharedPreferences = ZjjfPosApplication.getContext().getSharedPreferences("cookie", Context.MODE_PRIVATE);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (chain == null)
            KLog.d("http", "Receivedchain == null");
        Response originalResponse = chain.proceed(chain.request());
        Log.d("http", "originalResponse" + originalResponse.toString());
        if (!originalResponse.headers("set-cookie").isEmpty()) {
            final StringBuffer cookieBuffer = new StringBuffer();
            Observable.from(originalResponse.headers("set-cookie"))
                    .map(new Func1<String, String>() {
                        @Override
                        public String call(String s) {
                            String[] cookieArray = s.split(";");
                            return cookieArray[0];
                        }
                    })
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String cookie) {
                            cookieBuffer.append(cookie).append(";");
                        }
                    });
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("cookie", cookieBuffer.toString());
            KLog.d("http", "ReceivedCookiesInterceptor" + cookieBuffer.toString());
            editor.commit();
        }

        return originalResponse;
    }
}