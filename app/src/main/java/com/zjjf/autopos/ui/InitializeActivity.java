package com.zjjf.autopos.ui;


import android.os.Bundle;

import com.socks.library.KLog;
import com.zjjf.autopos.MainActivity;
import com.zjjf.autopos.R;
import com.zjjf.autopos.bean.GoodsBase;
import com.zjjf.autopos.bean.GoodsListBean;
import com.zjjf.autopos.common.Constant;
import com.zjjf.autopos.custemview.RxProgressBar;
import com.zjjf.autopos.network.BaseResponse;
import com.zjjf.autopos.network.Novate;
import com.zjjf.autopos.network.utils.NetworkUtil;
import com.zjjf.autopos.utils.SceneUtil;
import com.zjjf.autopos.utils.ToastUtils;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.SaveCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 初始化数据界面
 * Created by Yekai on 2017/11/28.
 */

public class InitializeActivity extends BaseActivity  {


    private RxProgressBar rxBar;
    /**
     * 每次获取2000个商品,分页加载
     */
    private int page = 0;
    private float progress;
    private boolean reLogin = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_initialize;
    }

    @Override
    public void initViews() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            reLogin = bundle.getBoolean("RELOGIN");
        }
        if (NetworkUtil.isNetworkAvailable()) {
            loadGoods();
            Observable.create(new Observable.OnSubscribe<Float>() {
                @Override
                public void call(Subscriber<? super Float> subscriber) {
                    try {
                        for (int i = 0; i < 20; i++) {
                            Thread.sleep(1000);
                            progress = rxBar.getProgress();
                            progress += 10;
                            subscriber.onNext(progress);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        subscriber.onNext(progress);
                    }
                    subscriber.onCompleted();
                }
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Float>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Float f) {
                            rxBar.setProgress(f);
                        }
                    });

        }

    }

    @Override
    public void initEvent() {

    }

    private void loadGoods() {
        Map<String, String> params = new HashMap<>();
        params.put("productStatus", "1");
        Novate.getDefault().executeJson(Constant.URL_GOODS_LIST + "?page=" + page + "&size=2000", params, new Novate.ResponseCallBack<BaseResponse<GoodsListBean>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onSuccee(BaseResponse<GoodsListBean> response) {
                if (page == 0) {
                    DataSupport.deleteAll(GoodsBase.class);
                }
                List<GoodsBase> temp = DataSupport.findAll(GoodsBase.class);
                KLog.d(temp.size() + "");
                GoodsListBean goodsList = response.getMsg();
                final List<GoodsBase> goodsBases = new ArrayList<>();
                for (GoodsListBean.ContentBean goodsBean : goodsList.getContent()) {
                    GoodsBase goodsBase = new GoodsBase();
                    goodsBase.setGoodsId(goodsBean.getProducts().get(0).getmId());
                    goodsBase.setName(goodsBean.getName());
                    goodsBase.setNo(goodsBean.getNo());
                    goodsBase.setSpec(goodsBean.getSpec());
                    goodsBase.setBarcode(goodsBean.getBarcode());
                    goodsBase.setPkg(goodsBean.getPkg());
                    goodsBase.setPkgId(goodsBean.getPkgId());
                    goodsBase.setBrand(goodsBean.getBrand());
                    goodsBase.setType(goodsBean.getType());
                    goodsBase.setFirstClassifyId(goodsBean.getSecondCategory().getmId());
                    goodsBase.setSecondClassifyId(goodsBean.getSecondCategory().getParentId());
                    goodsBase.setPurchasePrice(goodsBean.getProducts().get(0).getPurchasePrice());
                    goodsBase.setSellingPrice(goodsBean.getProducts().get(0).getSellingPrice());
                    goodsBase.setInventory(goodsBean.getProducts().get(0).getInventory());
                    goodsBases.add(goodsBase);
                }
                DataSupport.saveAllAsync(goodsBases).listen(new SaveCallback() {
                    @Override
                    public void onFinish(boolean success) {
                        if (success) {
                            if (goodsBases.size() >= 2000) {
                                page++;
                                loadGoods();
                            } else {
                                if (rxBar != null) {
                                    rxBar.setProgress(100);
                                }
                                if(reLogin){
                                    finish();
                                }else{
                                    SceneUtil.toScene(InitializeActivity.this, HomeActivity.class, null);
                                    finish();
                                }

                            }
                        }
                    }
                });
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                if (baseResponse.getMsg() != null) {
                    ToastUtils.showShortToast(InitializeActivity.this, baseResponse.getMsg().toString());
                }
            }
        });
    }
}
