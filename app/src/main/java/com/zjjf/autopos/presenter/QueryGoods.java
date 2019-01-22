package com.zjjf.autopos.presenter;

import com.zjjf.autopos.bean.GoodsBase;
import com.zjjf.autopos.bean.GoodsBean;
import com.zjjf.autopos.bean.GoodsListBean;
import com.zjjf.autopos.common.Constant;
import com.zjjf.autopos.network.BaseResponse;
import com.zjjf.autopos.network.Novate;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liwinner on 2019/1/21.
 */

public class QueryGoods {

    public QueryGoods(){

    }

    public void queryGoods(final String keyWords,final Novate.ResponseCallBack<List<GoodsBean>> callBack){
        callBack.onStart();
        final List<GoodsBean> goodsList = new ArrayList<>();
        Observable<List<GoodsBean>> fromDb = Observable.create(new Observable.OnSubscribe<List<GoodsBean>>() {
            @Override
            public void call(Subscriber<? super List<GoodsBean>> subscriber) {
                // List<GoodsBase> goodsBases = DataSupport.where("barcode like ? or name like ? or no like ?", "%" + keyWords + "%",
                //         "%" + keyWords + "%", "%" + keyWords + "%").order("goodsId asc").find(GoodsBase.class);
                List<GoodsBase> goodsBases = DataSupport.where("barcode = ? ", keyWords ).find(GoodsBase.class);
                if (goodsBases.size() > 0) {
                    for (GoodsBase goodsBase: goodsBases) {
                        GoodsBean goodsBean = new GoodsBean(goodsBase.getGoodsId(), 1, goodsBase.getName(), goodsBase.getNo(),
                                goodsBase.getSpec(), goodsBase.getBarcode(), goodsBase.getPkg(), goodsBase.getBrand(),
                                goodsBase.getType(), goodsBase.getFirstClassifyId(), goodsBase.getSecondClassifyId(),
                                goodsBase.getPurchasePrice(), goodsBase.getSellingPrice(), goodsBase.getInventory(),
                                goodsBase.getSellingPrice());
                        goodsList.add(goodsBean);
                    }
                }
                subscriber.onNext(goodsList);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
        Observable<List<GoodsBean>> fromNetWork =  Observable.create(new Observable.OnSubscribe<List<GoodsBean>>() {
            @Override
            public void call(final Subscriber<? super List<GoodsBean>> subscriber) {
                Map<String, String> params = new HashMap<>();
                params.put("productStatus", "1");
                params.put("searchWord", keyWords);
                Novate.getDefault().executeJson(Constant.URL_GOODS_LIST + "?page=0&size=" + Integer.MAX_VALUE, params, new Novate.ResponseCallBack<GoodsListBean>() {
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
                    public void onSuccee(GoodsListBean response) {
                        for (GoodsListBean.ContentBean goodsBase : response.getContent()) {
                            GoodsBean goodsBean = new GoodsBean(goodsBase.getProducts().get(0).getmId(), 1, goodsBase.getName(), goodsBase.getNo(),
                                    goodsBase.getSpec(), goodsBase.getBarcode(), goodsBase.getPkg(), goodsBase.getBrand(),
                                    goodsBase.getType(), goodsBase.getSecondCategory().getmId(), goodsBase.getSecondCategory().getParentId(),
                                    goodsBase.getProducts().get(0).getPurchasePrice(), goodsBase.getProducts().get(0).getSellingPrice(),
                                    goodsBase.getProducts().get(0).getInventory(), goodsBase.getProducts().get(0).getSellingPrice());
                            goodsList.add(goodsBean);
                        }
                        subscriber.onNext(goodsList);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onFail(BaseResponse baseResponse) {

                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
        //first如果没有值返回会抛异常,takeFirst不会抛异常但是他返回一个空的Observable
        // 该Observable只有onCompleted通知而没有onNext发送Observable。
        Observable.concat(fromDb,fromNetWork)
                .takeFirst(new Func1<List<GoodsBean>, Boolean>() {
                    @Override
                    public Boolean call(List<GoodsBean> goodsBeen) {
                        return goodsBeen.size() > 0;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GoodsBean>>() {

                    @Override
                    public void onCompleted() {
                        callBack.onSuccee(goodsList);
                        callBack.onCompleted();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        callBack.onError(throwable);
                    }

                    @Override
                    public void onNext(List<GoodsBean> goodsBeen) {
                    }
                });
    }
}
