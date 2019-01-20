/*
 *    Copyright (C) 2016 Tamic
 *
 *    link :https://github.com/Tamicer/Novate
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.zjjf.autopos.network;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.socks.library.KLog;
import com.zjjf.autopos.BuildConfig;
import com.zjjf.autopos.ZjjfPosApplication;
import com.zjjf.autopos.common.Constant;
import com.zjjf.autopos.network.cache.CookieCacheImpl;
import com.zjjf.autopos.network.cookie.AddCookiesInterceptor;
import com.zjjf.autopos.network.cookie.NovateCookieManager;
import com.zjjf.autopos.network.cookie.ReceivedCookiesInterceptor;
import com.zjjf.autopos.network.cookie.SharedPrefsCookiePersistor;
import com.zjjf.autopos.network.download.DownLoadCallBack;
import com.zjjf.autopos.network.download.DownSubscriber;
import com.zjjf.autopos.network.exception.FormatException;
import com.zjjf.autopos.network.exception.NovateException;
import com.zjjf.autopos.network.request.NovateRequest;
import com.zjjf.autopos.network.utils.FileUtil;
import com.zjjf.autopos.network.utils.Utils;
import com.zjjf.autopos.ui.LoginActivity;
import com.zjjf.autopos.utils.LogUtil;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Cache;
import okhttp3.CertificatePinner;
import okhttp3.ConnectionPool;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.BaseUrl;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FieldMap;
import retrofit2.http.Part;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Novate adapts a Java interface to Retrofit call by using annotations on the declared methods to
 * define how requests are made. Create instances using {@linkplain Builder
 * the builder} and pass your interface to {@link #} to generate an implementation.
 * <p/>
 * For example,
 * <pre>{@code
 * Novate novate = new Novate.Builder()
 *     .baseUrl("http://api.example.com")
 *     .addConverterFactory(GsonConverterFactory.create())
 *     .build();
 * <p/>
 * MyApi api = Novate.create(MyApi.class);
 * Response<User> user = api.getUser().execute();
 * }</pre>
 *
 * @author Tamic (skay5200@163.com)
 */
public final class Novate {

    private static Map<String, String> headers;
    private static Map<String, String> parameters;
    private static Retrofit.Builder retrofitBuilder;
    private static Retrofit retrofit;
    private static OkHttpClient.Builder okhttpBuilder;
    public static BaseApiService apiManager;
    private static OkHttpClient okHttpClient;
    private static Novate sNovate;
    private final okhttp3.Call.Factory callFactory;
    private final String baseUrl;
    private final List<Converter.Factory> converterFactories;
    private final List<CallAdapter.Factory> adapterFactories;
    private final Executor callbackExecutor;
    private final boolean validateEagerly;
    private Observable<ResponseBody> downObservable;
    private Map<String, Observable<ResponseBody>> downMaps = new HashMap<String, Observable<ResponseBody>>() {
    };
    private Observable.Transformer exceptTransformer = null;
    public static final String TAG = "Novate";

    /**
     * Mandatory constructor for the Novate
     */
    Novate(okhttp3.Call.Factory callFactory, String baseUrl, Map<String, String> headers,
           Map<String, String> parameters, BaseApiService apiManager,
           List<Converter.Factory> converterFactories, List<CallAdapter.Factory> adapterFactories,
           Executor callbackExecutor, boolean validateEagerly) {
        this.callFactory = callFactory;
        this.baseUrl = baseUrl;
        this.headers = headers;
        this.parameters = parameters;
        this.apiManager = apiManager;
        this.converterFactories = converterFactories;
        this.adapterFactories = adapterFactories;
        this.callbackExecutor = callbackExecutor;
        this.validateEagerly = validateEagerly;
    }

    /**
     * create ApiService
     */
    public <T> T create(final Class<T> service) {

        return retrofit.create(service);
    }

    /**
     * @param subscriber
     */
    public <T> T call(Observable<T> observable, Subscriber<T> subscriber) {
        return (T) observable.compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(subscriber);
    }

    /**
     * @param subscriber
     */
    public <T> T execute(NovateRequest request, Subscriber<T> subscriber) {
        return handleCall(request, subscriber);
    }

    private <T> T handleCall(NovateRequest request, Subscriber<T> subscriber) {
        //todo dev
     return null;
    }

    /**
     * Novate execute get
     * <p>
     * return parsed data
     * <p>
     * you don't need to parse ResponseBody
     */
    public <T> Subscription executeGet(final String url, final Map<String, Object> maps, final ResponseCallBack<T> callBack) {
        KLog.d(url+ LogUtil.getParams(maps));
        final Type[] types = callBack.getClass().getGenericInterfaces();
        if (MethodHandler(types) == null || MethodHandler(types).size() == 0) {
            return null;
        }
        final Type finalNeedType = MethodHandler(types).get(0);
        return  apiManager.executeGet(url, maps)
                .compose(schedulersTransformer)
                .subscribe(new NovateSubscriber<T>(finalNeedType, callBack));
    }

    /**
     * MethodHandler
     */
    private List<Type> MethodHandler(Type[] types) {
        List<Type> needtypes = new ArrayList<>();
        Type needParentType = null;

        for (Type paramType : types) {
            // if Type is T
            if (paramType instanceof ParameterizedType) {
                Type[] parentypes = ((ParameterizedType) paramType).getActualTypeArguments();
                for (Type childtype : parentypes) {
                    needtypes.add(childtype);
                    //needParentType = childtype;
                    if (childtype instanceof ParameterizedType) {
                        Type[] childtypes = ((ParameterizedType) childtype).getActualTypeArguments();
                        for (Type type : childtypes) {
                            needtypes.add(type);
                            //needChildType = type;
                        }
                    }
                }
            }
        }
        return needtypes;
    }

    final Observable.Transformer schedulersTransformer = new Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            return ((Observable) observable).subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    final Observable.Transformer schedulersTransformerDown = new Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            return ((Observable) observable).subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io());
        }
    };

    /**
     * @param <T>
     * @return
     */
    public <T> Observable.Transformer<NovateResponse<T>, T> handleErrTransformer() {

        if (exceptTransformer != null) return exceptTransformer;

        else return exceptTransformer = new Observable.Transformer() {
            @Override
            public Object call(Object observable) {
                return ((Observable) observable)/*.map(new HandleFuc<T>())*/.onErrorResumeNext(new HttpResponseFunc<T>());
            }
        };
    }

    private static class HttpResponseFunc<T> implements Func1<Throwable, Observable<T>> {
        @Override
        public Observable<T> call(Throwable t) {
            return Observable.error(NovateException.handleException(t));
        }
    }

    private class HandleFuc<T> implements Func1<NovateResponse<T>, T> {
        @Override
        public T call(NovateResponse<T> response) {
            if (response == null || (response.getData() == null && response.getResult() == null)) {
                throw new JsonParseException("后端数据不对");
            }
            /*if (!response.isOk()) {
                throw new RuntimeException(response.getCode() + "" + response.getMsg() != null ? response.getMsg() : "");
            }
*/
            return response.getData();
        }
    }

    /**
     * Retroift get
     *
     * @param url
     * @param maps
     * @param subscriber
     * @param <T>
     * @return no parse data
     */
    public <T> Subscription get(String url, Map<String, Object> maps, BaseSubscriber<ResponseBody> subscriber) {
        return  apiManager.executeGet(url, maps)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(subscriber);
    }

    /**
     * /**
     * Novate executePost
     *
     * @return no parse data
     * <p>
     * you must to be parse ResponseBody
     * <p>
     * <p/>
     * For example,
     * <pre>{@code
     * Novate novate = new Novate.Builder()
     *     .baseUrl("http://api.example.com")
     *     .addConverterFactory(GsonConverterFactory.create())
     *     .build();
     *
     * novate.post("url", parameters, new BaseSubscriber<ResponseBody>(context) {
     *    @Override
     *   public void onError(Throwable e) {
     *
     *   }
     *
     *  @Override
     *  public void onNext(ResponseBody responseBody) {
     *
     *   // todo you need to parse responseBody
     *
     *  }
     *  });
     * <p/>
     *
     * }</pre>
     */
    public <T> Subscription post(String url, @FieldMap(encoded = true) Map<String, Object> parameters, Subscriber<ResponseBody> subscriber) {
        return  apiManager.executePost(url, (Map<String, Object>) parameters)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(subscriber);
    }

    /**
     * Novate executePost
     *
     * @return parsed data
     * you don't need to   parse ResponseBody
     */
    public <T> Subscription executePost(final String url, @FieldMap(encoded = true) Map<String, Object> parameters, final ResponseCallBack<T> callBack) {
        final Type[] types = callBack.getClass().getGenericInterfaces();
        if (MethodHandler(types) == null || MethodHandler(types).size() == 0) {
            return null;
        }
        final Type finalNeedType = MethodHandler(types).get(0);
        Log.d(TAG, "-->:" + "Type:" + types[0]);

        return  apiManager.executePost(url, parameters)
                .compose(schedulersTransformer)
                .subscribe(new NovateSubscriber<T>(finalNeedType, callBack));
    }


    /**
     * Novate Post by Form
     *
     * @param url
     * @param subscriber
     */
    public <T> T form(String url, @FieldMap(encoded = true) Map<String, Object> fields, Subscriber<ResponseBody> subscriber) {
        return (T) apiManager.postForm(url, fields)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(subscriber);
    }


    /**
     * Novate execute Post by Form
     *
     * @return parsed data
     * you don't need to   parse ResponseBody
     */
    public <T> Subscription executeForm(final String url, final @FieldMap(encoded = true) Map<String, Object> fields, final ResponseCallBack<T> callBack) {
        final Type[] types = callBack.getClass().getGenericInterfaces();
        if (MethodHandler(types) == null || MethodHandler(types).size() == 0) {
            return null;
        }
        final Type finalNeedType = MethodHandler(types).get(0);
        Log.d(TAG, "-->:" + "Type:" + types[0]);

        return  apiManager.postForm(url, fields)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(new NovateSubscriber<T>(finalNeedType, callBack));
    }


    /**
     * http Post by Body
     * you  need to parse ResponseBody
     *
     * @param url
     * @param subscriber
     */
    public void body(String url, Object body, Subscriber<ResponseBody> subscriber) {
        apiManager.executePostBody(url, body)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(subscriber);
    }

    /**
     * http execute Post by body
     *
     * @return parsed data
     * you don't need to   parse ResponseBody
     */
    public <T> T executeBody(final String url, final Object body, final ResponseCallBack<T> callBack) {
        final Type[] types = callBack.getClass().getGenericInterfaces();
        if (MethodHandler(types) == null || MethodHandler(types).size() == 0) {
            return null;
        }
        final Type finalNeedType = MethodHandler(types).get(0);
        Log.d(TAG, "-->:" + "Type:" + types[0]);

        return (T) apiManager.executePostBody(url, body)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(new NovateSubscriber<T>(finalNeedType, callBack));
    }


    /**
     * http Post by json
     * you  need to parse ResponseBody
     *
     * @param url
     * @param jsonStr    Json String
     * @param subscriber
     */
    public void json(String url, String jsonStr, Subscriber<ResponseBody> subscriber) {
        apiManager.postRequestBody(url, Utils.createJson(jsonStr))
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(subscriber);    }

    /**
     * http execute Post by Json
     *
     * @param url
     * @return parsed data
     * you don't need to   parse ResponseBody
     */
    public <T> Subscription executeJson(final String url, final Map<String,Object> params, final ResponseCallBack<T> callBack) {
        KLog.d(url+LogUtil.getParams(params));
        final Type[] types = callBack.getClass().getGenericInterfaces();
        if (MethodHandler(types) == null || MethodHandler(types).size() == 0) {
            return null;
        }
        final Type finalNeedType = MethodHandler(types).get(0);
        return  apiManager.postRequestBody(url, Utils.createJson(new Gson().toJson(params)))
                .compose(schedulersTransformer)
                .subscribe(new NovateSubscriber<T>(finalNeedType, callBack));
    }

    /**
     * http execute Post by Json
     *
     * @param url
     * @return parsed data
     * you don't need to   parse ResponseBody
     */
    public <T> Subscription executeJson(final String url, final Object params, final ResponseCallBack<T> callBack) {
        KLog.d(url+new Gson().toJson(params));
        final Type[] types = callBack.getClass().getGenericInterfaces();
        if (MethodHandler(types) == null || MethodHandler(types).size() == 0) {
            return null;
        }
        final Type finalNeedType = MethodHandler(types).get(0);
        return  apiManager.postRequestBody(url, Utils.createJson(new Gson().toJson(params)))
                .compose(schedulersTransformer)
                .subscribe(new NovateSubscriber<T>(finalNeedType, callBack));
    }

    /**
     * Novate delete
     *
     * @param url
     * @param maps
     * @param subscriber
     * @param <T>
     * @return no parse data
     */
    public <T> T delete(String url, Map<String, T> maps, BaseSubscriber<ResponseBody> subscriber) {
        return (T) apiManager.executeDelete(url, (Map<String, Object>) maps)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(subscriber);
    }

    /**
     * Novate Execute http by Delete
     *
     * @return parsed data
     * you don't need to   parse ResponseBody
     */
    public <T> Subscription executeDelete(final String url, final Map<String, Object> maps, final ResponseCallBack<T> callBack) {
        final Type[] types = callBack.getClass().getGenericInterfaces();
        if (MethodHandler(types) == null || MethodHandler(types).size() == 0) {
            return null;
        }
        final Type finalNeedType = MethodHandler(types).get(0);
        Log.d(TAG, "-->:" + "Type:" + types[0]);
        return apiManager.executeDelete(url, maps)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(new NovateSubscriber<T>(finalNeedType, callBack));
    }

    /**
     * Novate put
     *
     * @param url
     * @param parameters
     * @param subscriber
     * @param <T>
     * @return no parse data
     */
    public <T> T put(String url, final @FieldMap(encoded = true) Map<String, T> parameters, BaseSubscriber<ResponseBody> subscriber) {
        return (T) apiManager.executePut(url, Utils.createJson(new Gson().toJson(parameters)))
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(subscriber);
    }

    /**
     * Novate Execute  Http by Put
     *
     * @return parsed data
     * you don't need to parse ResponseBody
     */
    public <T> Subscription executePut(final String url, final @FieldMap(encoded = true) Map<String, Object> parameters, final ResponseCallBack<T> callBack) {
        final Type[] types = callBack.getClass().getGenericInterfaces();

        if (MethodHandler(types) == null || MethodHandler(types).size() == 0) {
            return null;
        }
        final Type finalNeedType = MethodHandler(types).get(0);
        return apiManager.executePut(url, Utils.createJson(new Gson().toJson(parameters)))
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(new NovateSubscriber<T>(finalNeedType, callBack));
    }

    /**
     * Novate Execute  Http by Put
     *
     * @return parsed data
     * you don't need to parse ResponseBody
     */
    public <T> Subscription executePut(final String url, Object param, final ResponseCallBack<T> callBack) {
        String json = new Gson().toJson(param);
        KLog.d(url+json);
        final Type[] types = callBack.getClass().getGenericInterfaces();

        if (MethodHandler(types) == null || MethodHandler(types).size() == 0) {
            return null;
        }
        final Type finalNeedType = MethodHandler(types).get(0);
        return apiManager.executePut(url, Utils.createJson(json))
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(new NovateSubscriber<T>(finalNeedType, callBack));
    }


    /**
     * Novate Test
     *
     * @param url        url
     * @param maps       maps
     * @param subscriber subscriber
     * @param <T>        T
     * @return
     */
    public <T> T test(String url, Map<String, T> maps, Subscriber<ResponseBody> subscriber) {
        return (T) apiManager.getTest(url, (Map<String, Object>) maps)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(subscriber);
    }

    /**
     * Novate upload
     *
     * @param url
     * @param requestBody requestBody
     * @param subscriber  subscriber
     * @param <T>         T
     * @return
     */
    public <T> T upload(String url, RequestBody requestBody, Subscriber<ResponseBody> subscriber) {
        return (T) apiManager.postRequestBody(url, requestBody)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(subscriber);
    }

    /**
     * uploadImage
     *
     * @param url        url
     * @param file       file
     * @param subscriber
     * @param <T>
     * @return
     */
    public <T> T uploadImage(String url, File file, Subscriber<ResponseBody> subscriber) {
        return (T) apiManager.upLoadImage(url, Utils.createImage(file))
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(subscriber);
    }

    /**
     * Novate upload Flie
     *
     * @param url
     * @param file       file
     * @param subscriber subscriber
     * @param <T>        T
     * @return
     */
    public <T> Subscription uploadFlie(String url, RequestBody file, Subscriber<ResponseBody> subscriber) {
        return  apiManager.uploadFile(url, file)
                .compose(schedulersTransformer)
                .subscribe(subscriber);
    }

    /**
     * Novate upload Flie
     *
     * @param url
     * @param file       file
     * @param
     * @param <T>        T
     * @return
     */
    public <T> Subscription uploadFlie(String url, RequestBody description, MultipartBody.Part file, final ResponseCallBack<T> callBack) {
        final Type[] types = callBack.getClass().getGenericInterfaces();

        if (MethodHandler(types) == null || MethodHandler(types).size() == 0) {
            return null;
        }
        final Type finalNeedType = MethodHandler(types).get(0);
        Log.d(TAG, "-->:" + "Type:" + types[0]);
        return apiManager.uploadFlie(url, description, file)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(new NovateSubscriber<T>(finalNeedType, callBack));
    }

    /**
     * Novate upload Flies
     *
     * @param url
     * @param
     * @param <T>        T
     * @return
     */
    public <T> Subscription uploadFlies(String url, Map<String, RequestBody> files, final ResponseCallBack<T> callBack) {
        final Type[] types = callBack.getClass().getGenericInterfaces();

        if (MethodHandler(types) == null || MethodHandler(types).size() == 0) {
            return null;
        }
        final Type finalNeedType = MethodHandler(types).get(0);
        Log.d(TAG, "-->:" + "Type:" + types[0]);
        return  apiManager.uploadFiles(url, files)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(new NovateSubscriber<T>(finalNeedType, callBack));
    }


    /**
     * Novate upload Flies WithPartMap
     * @param url
     * @param partMap
     * @param file
     * @param subscriber
     * @param <T>
     * @return
     */
    public <T> Subscription uploadFileWithPartMap(String url, Map<String, RequestBody> partMap,
                                                  @Part("file") MultipartBody.Part file, Subscriber<ResponseBody> subscriber) {
        return  apiManager.uploadFileWithPartMap(url, partMap, file)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(subscriber);
    }


    /**
     * Novate download
     *
     * @param url
     * @param callBack
     */
    public void download(String url, DownLoadCallBack callBack) {
        download(url, FileUtil.getFileNameWithURL(url), callBack);
    }

    /**
     * @param url
     * @param name
     * @param callBack
     */
    public void download(String url, String name, DownLoadCallBack callBack) {
        download(FileUtil.generateFileKey(url, name), url, null, name, callBack);
    }

    /**
     * downloadMin
     *
     * @param url
     * @param callBack
     */
    public void downloadMin(String url, DownLoadCallBack callBack) {
        downloadMin(FileUtil.generateFileKey(url, FileUtil.getFileNameWithURL(url)), url, callBack);
    }

    /**
     * downloadMin
     *
     * @param key
     * @param url
     * @param callBack
     */
    public void downloadMin(String key, String url, DownLoadCallBack callBack) {
        downloadMin(key, url, FileUtil.getFileNameWithURL(url), callBack);
    }

    /**
     * downloadMin
     *
     * @param url
     * @param name
     * @param callBack
     */
    public void downloadMin(String key, String url, String name, DownLoadCallBack callBack) {
        downloadMin(key, url, null, name, callBack);
    }

    /**
     * download small file
     *
     * @param url
     * @param savePath
     * @param name
     * @param callBack
     */
    public void downloadMin(String key, String url, String savePath, String name, DownLoadCallBack callBack) {

        if (downMaps.get(key) == null) {
            downObservable = apiManager.downloadSmallFile(url);
        } else {
            downObservable = downMaps.get(key);
        }
        downMaps.put(key, downObservable);
        executeDownload(key, savePath, name, callBack);
    }

    /**
     * @param url
     * @param url
     * @param url
     * @param savePath
     * @param name
     * @param callBack
     */
    public void download(String key, String url, String savePath, String name, DownLoadCallBack callBack) {
        if (downMaps.get(key) == null) {
            downObservable = apiManager.downloadFile(url);
        } else {
            downObservable = downMaps.get(url);
        }
        downMaps.put(key, downObservable);
        executeDownload(key, savePath, name, callBack);
    }

    /**
     * executeDownload
     *
     * @param savePath
     * @param name
     *
     * @param callBack
     */
    private void executeDownload(String key, String savePath, String name, DownLoadCallBack callBack) {
        /*if (NovateDownLoadManager.isDownLoading) {
            downMaps.get(key).unsubscribeOn(Schedulers.io());
            NovateDownLoadManager.isDownLoading = false;
            NovateDownLoadManager.isCancel = true;
            return;
        }*/
        //NovateDownLoadManager.isDownLoading = true;
        if(downMaps.get(key)!= null) {
            downMaps.get(key).compose(schedulersTransformerDown)
                    .compose(handleErrTransformer())
                    .subscribe(new DownSubscriber<ResponseBody>(key, savePath, name, callBack));
        }

    }

    /**
     * Mandatory Builder for the Builder
     */
    public static final class Builder {

        private static final int DEFAULT_TIMEOUT = 20;
        private static final int DEFAULT_MAXIDLE_CONNECTIONS = 5;
        private static final long DEFAULT_KEEP_ALIVEDURATION = 8;
        private static final long caheMaxSize = 10 * 1024 * 1024;

        private okhttp3.Call.Factory callFactory;
        private String baseUrl;
        private Boolean isLog = false;
        private Boolean isCookie = false;
        private Boolean isCache = false;
        private List<InputStream> certificateList;
        private HostnameVerifier hostnameVerifier;
        private CertificatePinner certificatePinner;
        private List<Converter.Factory> converterFactories = new ArrayList<>();
        private List<CallAdapter.Factory> adapterFactories = new ArrayList<>();
        private Executor callbackExecutor;
        private boolean validateEagerly;
        private NovateCookieManager cookieManager;
        private Cache cache = null;
        private Proxy proxy;
        private File httpCacheDirectory;
        private SSLSocketFactory sslSocketFactory;
        private ConnectionPool connectionPool;
        private Converter.Factory converterFactory;
        private CallAdapter.Factory callAdapterFactory;
        private Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR;
        private Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR_OFFLINE;

        public Builder() {
            // Add the base url first. This prevents overriding its behavior but also
            // ensures correct behavior when using novate that consume all types.
            okhttpBuilder = new OkHttpClient.Builder();
            retrofitBuilder = new Retrofit.Builder();
        }

        /**
         * The HTTP client used for requests. default OkHttpClient
         * <p/>
         * This is a convenience method for calling {@link #callFactory}.
         * <p/>
         * Note: This method <b>does not</b> make a defensive copy of {@code client}. Changes to its
         * settings will affect subsequent requests. Pass in a {@linkplain OkHttpClient#clone() cloned}
         * instance to prevent this if desired.
         */
        @NonNull
        public Builder client(OkHttpClient client) {
            retrofitBuilder.client(Utils.checkNotNull(client, "client == null"));
            return this;
        }

        /**
         * Add ApiManager for serialization and deserialization of objects.
         *//*
        public Builder addApiManager(final Class<ApiManager> service) {

            apiManager = retrofit.create((Utils.checkNotNull(service, "apiManager == null")));
            //return retrofit.create(service);
            return this;
        }*/

        /**
         * Specify a custom call factory for creating {@link } instances.
         * <p/>
         * Note: Calling {@link #client} automatically sets this value.
         */
        public Builder callFactory(okhttp3.Call.Factory factory) {
            this.callFactory = Utils.checkNotNull(factory, "factory == null");
            return this;
        }

        /**
         * Sets the default connect timeout for new connections. A value of 0 means no timeout,
         * otherwise values must be between 1 and {@link Integer#MAX_VALUE} when converted to
         * milliseconds.
         */
        public Builder connectTimeout(int timeout) {
            return connectTimeout(timeout, TimeUnit.SECONDS);
        }

        /**
         * Sets the default connect timeout for new connections. A value of 0 means no timeout,
         * otherwise values must be between 1 and {@link Integer#MAX_VALUE} when converted to
         * milliseconds.
         */
        public Builder writeTimeout(int timeout) {
            return writeTimeout(timeout, TimeUnit.SECONDS);
        }

        public Builder readTimeout(int timeout) {
            return readTimeout(timeout, TimeUnit.SECONDS);
        }

        /**
         * open default logcat
         *
         * @param isLog
         * @return
         */
        public Builder addLog(boolean isLog) {
            this.isLog = isLog;
            return this;
        }

        /**
         * open sync default Cookie
         *
         * @param isCookie
         * @return
         */
        public Builder addCookie(boolean isCookie) {
            this.isCookie = isCookie;
            return this;
        }

        /**
         * open default Cache
         *
         * @param isCache
         * @return
         */
        public Builder addCache(boolean isCache) {
            this.isCache = isCache;
            return this;
        }

        public Builder proxy(Proxy proxy) {
            okhttpBuilder.proxy(Utils.checkNotNull(proxy, "proxy == null"));
            return this;
        }

        /**
         * Sets the default write timeout for new connections. A value of 0 means no timeout,
         * otherwise values must be between 1 and {@link TimeUnit #MAX_VALUE} when converted to
         * milliseconds.
         */
        public Builder writeTimeout(int timeout, TimeUnit unit) {
            if (timeout != -1) {
                okhttpBuilder.writeTimeout(timeout, unit);
            } else {
                okhttpBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            }
            return this;
        }

        public Builder readTimeout(int timeout, TimeUnit unit){
            if (timeout != -1) {
                okhttpBuilder.readTimeout(timeout,unit);
            } else {
                okhttpBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            }
            return this;
        }

        /**
         * Sets the connection pool used to recycle HTTP and HTTPS connections.
         * <p>
         * <p>If unset, a new connection pool will be used.
         */
        public Builder connectionPool(ConnectionPool connectionPool) {
            if (connectionPool == null) throw new NullPointerException("connectionPool == null");
            this.connectionPool = connectionPool;
            return this;
        }

        /**
         * Sets the default connect timeout for new connections. A value of 0 means no timeout,
         * otherwise values must be between 1 and {@link TimeUnit #MAX_VALUE} when converted to
         * milliseconds.
         */
        public Builder connectTimeout(int timeout, TimeUnit unit) {
            if (timeout != -1) {
                okhttpBuilder.connectTimeout(timeout, unit);
            } else {
                okhttpBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            }
            return this;
        }


        /**
         * Set an API base URL which can change over time.
         *
         * @see BaseUrl(HttpUrl)
         */
        public Builder baseUrl(String baseUrl) {
            this.baseUrl = Utils.checkNotNull(baseUrl, "baseUrl == null");
            return this;
        }

        /**
         * Add converter factory for serialization and deserialization of objects.
         */
        public Builder addConverterFactory(Converter.Factory factory) {
            this.converterFactory = factory;
            return this;
        }

        /**
         * Add a call adapter factory for supporting service method return types other than {@link CallAdapter
         * }.
         */
        public Builder addCallAdapterFactory(CallAdapter.Factory factory) {
            this.callAdapterFactory = factory;
            return this;
        }

        /**
         * Add Header for serialization and deserialization of objects.
         */
        public <T> Builder addHeader(Map<String, T> headers) {
            okhttpBuilder.addInterceptor(new BaseInterceptor(Utils.checkNotNull(headers, "header == null")));
            return this;
        }

        /**
         * Add parameters for serialization and deserialization of objects.
         */
        public <T> Builder addParameters(Map<String, T> parameters) {
            okhttpBuilder.addInterceptor(new BaseInterceptor(Utils.checkNotNull(parameters, "parameters == null")));
            return this;
        }

        /**
         * Returns a modifiable list of interceptors that observe a single network request and response.
         * These interceptors must call {@link Interceptor.Chain#proceed} exactly once: it is an error
         * for a network interceptor to short-circuit or repeat a network request.
         */
        public Builder addInterceptor(Interceptor interceptor) {
            okhttpBuilder.addInterceptor(Utils.checkNotNull(interceptor, "interceptor == null"));
            return this;
        }

        /**
         * The executor on which {@link Call} methods are invoked when returning {@link Call} from
         * your service method.
         * <p/>
         * Note: {@code executor} is not used for {@linkplain #addCallAdapterFactory custom method
         * return types}.
         */
        public Builder callbackExecutor(Executor executor) {
            this.callbackExecutor = Utils.checkNotNull(executor, "executor == null");
            return this;
        }

        /**
         * When calling {@link #create} on the resulting {@link Retrofit} instance, eagerly validate
         * the configuration of all methods in the supplied interface.
         */
        public Builder validateEagerly(boolean validateEagerly) {
            this.validateEagerly = validateEagerly;
            return this;
        }

        /**
         * Sets the handler that can accept cookies from incoming HTTP responses and provides cookies to
         * outgoing HTTP requests.
         * <p/>
         * <p>If unset, {@linkplain NovateCookieManager#NO_COOKIES no cookies} will be accepted nor provided.
         */
        public Builder cookieManager(NovateCookieManager cookie) {
            if (cookie == null) throw new NullPointerException("cookieManager == null");
            this.cookieManager = cookie;
            return this;
        }

        /**
         *
         */
        public Builder addSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
            this.sslSocketFactory = sslSocketFactory;
            return this;
        }

        public Builder addHostnameVerifier(HostnameVerifier hostnameVerifier) {
            this.hostnameVerifier = hostnameVerifier;
            return this;
        }

        public Builder addCertificatePinner(CertificatePinner certificatePinner) {
            this.certificatePinner = certificatePinner;
            return this;
        }


        /**
         * Sets the handler that can accept cookies from incoming HTTP responses and provides cookies to
         * outgoing HTTP requests.
         * <p/>
         * <p>If unset, {@linkplain NovateCookieManager#NO_COOKIES no cookies} will be accepted nor provided.
         */
        public Builder addSSL(String[] hosts, int[] certificates) {
            if (hosts == null) throw new NullPointerException("hosts == null");
            if (certificates == null) throw new NullPointerException("ids == null");


            addSSLSocketFactory(NovateHttpsFactroy.getSSLSocketFactory(certificates));
            addHostnameVerifier(NovateHttpsFactroy.getHostnameVerifier(hosts));
            return this;
        }

        public Builder addNetworkInterceptor(Interceptor interceptor) {
            okhttpBuilder.addNetworkInterceptor(interceptor);
            return this;
        }

        /**
         * setCache
         *
         * @param cache cahe
         * @return Builder
         */
        public Builder addCache(Cache cache) {
            int maxStale = 60 * 60 * 24 * 3;
            return addCache(cache, maxStale);
        }

        /**
         * @param cache
         * @param cacheTime ms
         * @return
         */
        public Builder addCache(Cache cache, final int cacheTime) {
            addCache(cache, "max-age="+cacheTime);
            return this;
        }

        /**
         * @param cache
         * @param cacheControlValue Cache-Control
         * @return
         */
        private Builder addCache(Cache cache, final String cacheControlValue) {
            REWRITE_CACHE_CONTROL_INTERCEPTOR = new CacheInterceptor(cacheControlValue);
            REWRITE_CACHE_CONTROL_INTERCEPTOR_OFFLINE = new CacheInterceptorOffline(cacheControlValue);
            addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
            addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR_OFFLINE);
            addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR_OFFLINE);
            this.cache = cache;
            return this;
        }

        /**
         * Create the {@link Retrofit} instance using the configured values.
         * <p/>
         * Note: If neither {@link #client} nor {@link #callFactory} is called a default {@link
         * OkHttpClient} will be created and used.
         */
        public Novate build() {
            if (baseUrl == null) {
                //throw new IllegalStateException("Base URL required.");
                baseUrl = Constant.CONTEXTPATH;
            }

            if (okhttpBuilder == null) {
                throw new IllegalStateException("okhttpBuilder required.");
            }

            if (retrofitBuilder == null) {
                throw new IllegalStateException("retrofitBuilder required.");
            }
            /** set Context. */
            /**
             * Set a fixed API base URL.
             *
             * @see #baseUrl(HttpUrl)
             */
            retrofitBuilder.baseUrl(baseUrl);

            /** Add converter factory for serialization and deserialization of objects. */
            if (converterFactory == null) {
                converterFactory = GsonConverterFactory.create();
            }

            retrofitBuilder.addConverterFactory(converterFactory);
            /**
             * Add a call adapter factory for supporting service method return types other than {@link
             * Call}.
             */
            if (callAdapterFactory == null) {
                callAdapterFactory = RxJavaCallAdapterFactory.create();
            }
            retrofitBuilder.addCallAdapterFactory(callAdapterFactory);

            if (isLog) {
                okhttpBuilder.addNetworkInterceptor(
                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS));
            }

            if (sslSocketFactory != null) {
                okhttpBuilder.sslSocketFactory(sslSocketFactory);
            }

            if (hostnameVerifier != null) {
                okhttpBuilder.hostnameVerifier(hostnameVerifier);
            }


            if (httpCacheDirectory == null) {
                httpCacheDirectory = new File(ZjjfPosApplication.getAppContext().getCacheDir(), "zjjfpos_cache");
            }

            if (isCache) {
                try {
                    if (cache == null) {
                        cache = new Cache(httpCacheDirectory, caheMaxSize);
                    }

                    addCache(cache);

                } catch (Exception e) {
                    Log.e("OKHttp", "Could not create http cache", e);
                }
                if (cache == null) {
                    cache = new Cache(httpCacheDirectory, caheMaxSize);
                }
            }

            if (cache != null) {
                okhttpBuilder.cache(cache);
            }

            /**
             * Sets the connection pool used to recycle HTTP and HTTPS connections.
             *
             * <p>If unset, a new connection pool will be used.
             */
            if (connectionPool == null) {

                connectionPool = new ConnectionPool(DEFAULT_MAXIDLE_CONNECTIONS, DEFAULT_KEEP_ALIVEDURATION, TimeUnit.SECONDS);
            }
            okhttpBuilder.connectionPool(connectionPool);

            /**
             * Sets the HTTP proxy that will be used by connections created by this client. This takes
             * precedence over {@link #proxySelector}, which is only honored when this proxy is null (which
             * it is by default). To disable proxy use completely, call {@code setProxy(Proxy.NO_PROXY)}.
             */
            if (proxy == null) {
                okhttpBuilder.proxy(proxy);
            }

            /**
             * Sets the handler that can accept cookies from incoming HTTP responses and provides cookies to
             * outgoing HTTP requests.
             *
             * <p>If unset, {@link Novate NovateCookieManager#NO_COOKIES no cookies} will be accepted nor provided.
             */
            if (isCookie && cookieManager == null) {
                //okhttpBuilder.cookieJar(new NovateCookieManger(context));
                okhttpBuilder.cookieJar(new NovateCookieManager(new CookieCacheImpl(), new SharedPrefsCookiePersistor()));
            }

            if (isCookie) {
                /**
                 * Returns a modifiable list of interceptors that observe a single network request and response.
                 * These interceptors must call {@link Interceptor.Chain#proceed} exactly once: it is an error
                 * for a network interceptor to short-circuit or repeat a network request.
                 */
                okhttpBuilder.addInterceptor(new ReceivedCookiesInterceptor());
                okhttpBuilder.addInterceptor(new AddCookiesInterceptor( ""));
            }

            if (cookieManager != null) {
                okhttpBuilder.cookieJar(cookieManager);
            }

            /**
             *okhttp3.Call.Factory callFactory = this.callFactory;
             */
            if (callFactory != null) {
                retrofitBuilder.callFactory(callFactory);
            }
            /**
             * create okHttpClient
             */
            okHttpClient = okhttpBuilder.build();
            /**
             * set Retrofit client
             */

            retrofitBuilder.client(okHttpClient);

            /**
             * create Retrofit
             */
            retrofit = retrofitBuilder.build();
            /**
             *create BaseApiService;
             */
            apiManager = retrofit.create(BaseApiService.class);

            return new Novate(callFactory, baseUrl, headers, parameters, apiManager, converterFactories, adapterFactories,
                    callbackExecutor, validateEagerly);
        }
    }

    public static Novate getDefault() {
        if (sNovate == null) {
            synchronized (Novate.class) {
                if (sNovate == null){
                    sNovate = new Builder()
                            .addCookie(true)
                            .connectTimeout(60)
                            .writeTimeout(60)
                            .readTimeout(60)
                            .addLog(BuildConfig.DEBUG)
                            .build();
                }
            }
        }
        return sNovate;
    }

    /**
     * NovateSubscriber
     *
     * @param <T>
     */
    class NovateSubscriber<T> extends Subscriber<ResponseBody> {

        private ResponseCallBack<T> callBack;

        private Type finalNeedType;

        public NovateSubscriber(Type finalNeedType, ResponseCallBack<T> callBack) {
            super();
            this.callBack = callBack;

            this.finalNeedType = finalNeedType;
        }

        @Override
        public void onStart() {
            super.onStart();
            // todo some common as show loadding  and check netWork is NetworkAvailable
            KLog.d(TAG, "-->:" + "onStart");
            if (callBack != null) {
                callBack.onStart();
            }
        }

        @Override
        public void onCompleted() {
            // todo some common as  dismiss loadding
            KLog.d(TAG, "-->:" + "onCompleted");
            if (callBack != null) {
                callBack.onCompleted();
            }
        }

        @Override
        public void onError(Throwable e) {
            KLog.d(TAG, "-->:" + "onError:"+e.toString());
            if (callBack != null) {
                callBack.onError(e);
            }
        }

        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                final String jsStr = responseBody.string().replaceAll("\"id\"","\"mId\"");
                KLog.json(jsStr);
                if (callBack != null) {
                    try {
                        /**
                         * if need parse baseRespone<T> use ParentType, if parse T use childType . defult parse baseRespone<T>
                         *  callBack.onSuccee((T) JSON.parseArray(jsStr, (Class<Object>) finalNeedType));
                         *  Type finalNeedType = needChildType;
                         */
//                        Observable.create(new Observable.OnSubscribe<BaseResponse>() {
//                            @Override
//                            public void call(Subscriber<? super BaseResponse> subscriber) {
//                                BaseResponse baseResponse = new Gson().fromJson(jsStr,BaseResponse.class);
//                                if (baseResponse.isSuccess()) {
//                                    BaseResponse<T> data = new Gson().fromJson(jsStr,finalNeedType);
//                                    baseResponse.setMsg(data.getMsg());
//                                }
//                                subscriber.onNext(baseResponse);
//                            }
//                        }).compose(schedulersTransformer)
//                          .subscribe(new Action1<BaseResponse>() {
//                              @Override
//                              public void call(BaseResponse response) {
//                                  if (response.isSuccess()) {
//                                      callBack.onSuccee((T) response);
//                                  } else {
//                                      callBack.onFail(response);
////                                      if(response.getMsg() != null){
////                                          if(response.getMsg().toString().contains("请登录")){
////                                              Intent it = new Intent(ZjjfPosApplication.getContext(),LoginActivity.class);
////                                              it.putExtra("ReLogin",true);
////                                              it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                                              ZjjfPosApplication.getAppContext().startActivity(it);
////                                          }
////                                      }
//                                  }
//                              }
//                          });
                        BaseResponse baseResponse = new Gson().fromJson(jsStr, BaseResponse.class);
                        if (baseResponse.isSuccess()) {
                            //callBack.onSuccee((T) JsonUtil.parseObject(jsStr, finalNeedType));
                            BaseResponse<T> data = new Gson().fromJson(jsStr, finalNeedType);
                            callBack.onSuccee((T) data);
                        } else {
                            callBack.onFail(baseResponse);
                            if(baseResponse.getMsg() != null){
                                if(baseResponse.getMsg().toString().contains("请登录")){
                                    Intent it = new Intent(ZjjfPosApplication.getContext(),LoginActivity.class);
                                    it.putExtra("ReLogin",true);
                                    it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    ZjjfPosApplication.getContext().startActivity(it);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (callBack != null) {
                            callBack.onError(NovateException.handleException(new FormatException()));
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                if (callBack != null) {
                    callBack.onError(NovateException.handleException(e));
                }
            }
        }
    }

    /**
     * ResponseCallBack <T> Support your custom data model
     */
    public interface ResponseCallBack<T> {

        void onStart();

        void onCompleted();

        void onError(Throwable e);

        void onSuccee(T response);

        void onFail(BaseResponse baseResponse);

    }

    class RetryWithDelay implements Func1<Observable<? extends Throwable>,Observable<?>>{

        private final int maxRetries;
        private final int retryDelayMillis;
        private int retryCount;
        public RetryWithDelay(int maxRetries, int retryDelayMillis) {
            this.maxRetries = maxRetries;
            this.retryDelayMillis = retryDelayMillis;
        }

        @Override
        public Observable<?> call(Observable<? extends Throwable> observable) {
            return observable
                    .flatMap(new Func1<Throwable, Observable<?>>() {
                        @Override
                        public Observable<?> call(Throwable throwable) {
                            if (++retryCount <= maxRetries) {
                                // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                                KLog.d(TAG, "get error, it will try after " + retryDelayMillis
                                        + " millisecond, retry count " + retryCount);
                                return Observable.timer(retryDelayMillis,
                                        TimeUnit.MILLISECONDS);
                            }
                            // Max retries hit. Just pass the error along.
                            return Observable.error(throwable);
                        }
                    });
        }
    }
}


