package com.zjjf.autopos;

import android.app.Activity;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;
import com.zjjf.autopos.bean.GoodsBase;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;
import org.litepal.crud.DataSupport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Administrator on 2019/1/15 0015.
 */

public class ZjjfPosApplication extends LitePalApplication {

    private static Context sContext;
    private RefWatcher refWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = ZjjfPosApplication.this;
        initUmeng();
        initLeakCanary();
        initActivityLifecycleLogs();
        LitePal.initialize(this);
        initStrictMode();
        KLog.init(BuildConfig.DEBUG, "ZjjfPos");
    }

    private void initLeakCanary() {
        if (BuildConfig.DEBUG) {
            refWatcher = LeakCanary.install(this);
        } else {
            refWatcher = installLeakCanary();
        }
    }

    /**
     * release版本使用此方法
     */
    protected RefWatcher installLeakCanary() {
        return RefWatcher.DISABLED;
    }

    public static Context getAppContext() {
        return sContext;
    }

    private void initActivityLifecycleLogs() {
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                KLog.v("=========", activity + "  onActivityCreated");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                KLog.v("=========", activity + "  onActivityStarted");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                KLog.v("=========", activity + "  onActivityResumed");
            }

            @Override
            public void onActivityPaused(Activity activity) {
                KLog.v("=========", activity + "  onActivityPaused");
            }

            @Override
            public void onActivityStopped(Activity activity) {
                KLog.v("=========", activity + "  onActivityStopped");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                KLog.v("=========", activity + "  onActivitySaveInstanceState");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                KLog.v("=========", activity + "  onActivityDestroyed");
            }
        });
    }

    private void initStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                    new StrictMode.ThreadPolicy.Builder()
                            .detectAll()
//                            .penaltyDialog() // 弹出违规提示对话框
                            .penaltyLog() // 在logcat中打印违规异常信息
                            .build());
            StrictMode.setVmPolicy(
                    new StrictMode.VmPolicy.Builder()
                            .detectAll()
                            .penaltyLog()
                            .build());
        }
    }

    public static RefWatcher getRefWatcher(Context context) {
        ZjjfPosApplication application = (ZjjfPosApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    private void initUmeng() {
        UMConfigure.init(this,"5c5e3a73b465f51506000b56","umeng",UMConfigure.DEVICE_TYPE_PHONE,"ab8415498585c7ecfc9d7cde426a5bcf");
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setNotificaitonOnForeground(false);
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                KLog.i("注册成功：deviceToken：-------->  " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                KLog.e("注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });
        UmengMessageHandler messageHandler = new UmengMessageHandler(){

            @Override
            public void dealWithCustomMessage(final Context context,final UMessage uMessage) {
                super.dealWithCustomMessage(context, uMessage);
                Observable.create(new Observable.OnSubscribe<UMessage>() {
                    @Override
                    public void call(Subscriber<? super UMessage> subscriber) {
                        subscriber.onNext(uMessage);
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<UMessage>() {
                            @Override
                            public void call(UMessage uMessage) {
                                Map<String, String> map = uMessage.extra;
                                String type = map.get("type");
                                if ("1".equals(type) || "2".equals(type)) {
                                    String goodsMsg = map.get("msg");
                                    if (!TextUtils.isEmpty(goodsMsg)) {
                                        List<GoodsBase> goodsBaseList = new ArrayList<GoodsBase>();
                                        Type listType = new TypeToken<List<GoodsBase>>() {
                                        }.getType();
                                        goodsBaseList = new Gson().fromJson(goodsMsg, listType);
                                        if (goodsBaseList.size() > 0) {
                                            for (GoodsBase goodsBase : goodsBaseList) {
                                                if ("2".equals(type)) {
                                                    if (DataSupport.isExist(GoodsBase.class, "goodsId=?", goodsBase.getGoodsId())) {
//                                                        baiduVoiceUtils.speakText("有商品下架");
                                                        GoodsBase bean = DataSupport.where("goodsId=?", goodsBase.getGoodsId()).find(GoodsBase.class).get(0);
                                                        DataSupport.delete(GoodsBase.class, bean.getId());
                                                    }
                                                } else {
                                                    if (DataSupport.isExist(GoodsBase.class, "goodsId=?", goodsBase.getGoodsId())) {
//                                                        baiduVoiceUtils.speakText("有商品修改");
                                                        GoodsBase bean = DataSupport.where("goodsId=?", goodsBase.getGoodsId()).find(GoodsBase.class).get(0);
                                                        ContentValues values = new ContentValues();
                                                        values.put("goodsId", goodsBase.getGoodsId());
                                                        values.put("name", goodsBase.getName());
                                                        values.put("no", goodsBase.getNo());
                                                        values.put("spec", goodsBase.getSpec());
                                                        values.put("barcode", goodsBase.getBarcode());
                                                        values.put("pkg", goodsBase.getPkg());
                                                        values.put("pkgId", goodsBase.getPkgId());
                                                        values.put("brand", goodsBase.getBrand());
                                                        values.put("expirationDays", goodsBase.getExpirationDays());
                                                        values.put("type", goodsBase.getType());
                                                        values.put("firstClassifyId", goodsBase.getFirstClassifyId());
                                                        values.put("secondClassifyId", goodsBase.getSecondClassifyId());
                                                        values.put("purchasePrice", goodsBase.getPurchasePrice());
                                                        values.put("sellingPrice", goodsBase.getSellingPrice());
                                                        values.put("inventory", goodsBase.getInventory());
                                                        DataSupport.update(GoodsBase.class, values, bean.getId());
                                                    } else {
//                                                        baiduVoiceUtils.speakText("有新增商品");
                                                        goodsBase.save();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        });
            }
        };
        mPushAgent.setMessageHandler(messageHandler);
    }
}
