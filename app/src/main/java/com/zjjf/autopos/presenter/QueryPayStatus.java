package com.zjjf.autopos.presenter;

import com.zjjf.autopos.bean.PayCallbackBean;
import com.zjjf.autopos.common.Constant;
import com.zjjf.autopos.network.BaseResponse;
import com.zjjf.autopos.network.Novate;

import java.util.HashMap;
import java.util.Map;

import rx.Subscription;

/**
 * Created by liwinner on 2019/2/12.
 */

public class QueryPayStatus {

    public Subscription mSubscription;

    public void queryPayStatus(String orderNo,final Novate.ResponseCallBack<PayCallbackBean> callBack) {

        Map<String,Object> params = new HashMap<>();
        params.put("isQRCode","1");
        params.put("orderNo",orderNo);
        mSubscription = Novate.getDefault().executePost(Constant.URL_QUERY_ORDERINFO_BY_ORDERID, params, new Novate.ResponseCallBack<PayCallbackBean>() {
            @Override
            public void onStart() {
                callBack.onStart();
            }

            @Override
            public void onCompleted() {
                callBack.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                callBack.onError(e);
            }

            @Override
            public void onSuccee(PayCallbackBean response) {
                callBack.onSuccee(response);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
            }
        });

    }
}
