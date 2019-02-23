package com.zjjf.autopos.ui;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zjjf.autopos.R;
import com.zjjf.autopos.bean.PayCallbackBean;
import com.zjjf.autopos.common.Constant;
import com.zjjf.autopos.network.BaseResponse;
import com.zjjf.autopos.network.Novate;
import com.zjjf.autopos.presenter.QueryPayStatus;
import com.zjjf.autopos.utils.DateUtils;
import com.zjjf.autopos.utils.QRCodeUtil;
import com.zjjf.autopos.utils.SceneUtil;

public class PayActivity extends BaseActivity implements Novate.ResponseCallBack<PayCallbackBean> {

    private TextView tv_goods_num,tv_price,tv_discard_price,tv_discounts_price;
    private ImageView iv_wx_pay,iv_zfb_pay;

    private QueryPayStatus mQueryPayStatus;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 100) {
                if (mQueryPayStatus != null) {
                    mQueryPayStatus.queryPayStatus(orderNo,PayActivity.this);
                }
            }
            super.handleMessage(msg);
        }
    } ;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    public void initViews() {
        tv_goods_num = findViewById(R.id.tv_goods_num);
        tv_price = findViewById(R.id.tv_price);
        tv_discard_price = findViewById(R.id.tv_discard_price);
        tv_discounts_price = findViewById(R.id.tv_discounts_price);
        iv_wx_pay = findViewById(R.id.iv_wx_pay);
        iv_zfb_pay = findViewById(R.id.iv_zfb_pay);
    }

    private String WXUrl,ZFBUrl;
    private int goodsNum;
    private double needPay,totalPrice,discountsPrice;
    private String orderNo;
    @Override
    public void initEvent() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            WXUrl = bundle.getString("WXURL");
            ZFBUrl = bundle.getString("ZFBURL");
            goodsNum = bundle.getInt("NUMBER");
            needPay = bundle.getDouble("needPay");
            totalPrice = bundle.getDouble("totalPrice");
            discountsPrice = bundle.getDouble("discountsPrice");
            orderNo = bundle.getString("OrderNo","");
        }
        tv_goods_num.setText("应付（共"+goodsNum+"件）");
        tv_price.setText(Constant.RMB+ DateUtils.getOneDecimals(needPay));
        if (discountsPrice > 0) {
            tv_discard_price.setText(Constant.RMB+DateUtils.getOneDecimals(totalPrice));
            tv_discard_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
            tv_discounts_price.setText("已优惠"+Constant.RMB+DateUtils.getOneDecimals(discountsPrice));
        }

        iv_wx_pay.setImageBitmap(QRCodeUtil.createQRCodeBitmap(WXUrl,320));
        iv_zfb_pay.setImageBitmap(QRCodeUtil.createQRCodeBitmap(ZFBUrl,320));
        mQueryPayStatus = new QueryPayStatus();
        mHandler.sendEmptyMessageDelayed(100,2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        payError();
    }

    @Override
    public void onSuccee(PayCallbackBean response) {
        if (response.getPayStatus() == 2) {
            payError();
        } else if (response.getPayStatus() == 9) {
            Bundle bundle = new Bundle();
            bundle.putInt("NUMBER",goodsNum);
            bundle.putDouble("needPay",needPay);
            bundle.putDouble("totalPrice",totalPrice);
            bundle.putDouble("discountsPrice",discountsPrice);
            bundle.putString("OrderNo",orderNo);
            SceneUtil.toScene(PayActivity.this,PayProcessActivity.class,bundle);
        } else if (response.getPayStatus() == 0) {
            mHandler.sendEmptyMessageDelayed(100,1000);
        } else if (response.getPayStatus() == 1) {
            Bundle bundle = new Bundle();
            bundle.putInt("NUMBER",goodsNum);
            bundle.putDouble("needPay",needPay);
            bundle.putDouble("totalPrice",totalPrice);
            bundle.putDouble("discountsPrice",discountsPrice);
            bundle.putString("OrderNo",orderNo);
            SceneUtil.toScene(PayActivity.this,PaySucceedActivity.class,bundle);
        }
    }

    @Override
    public void onFail(BaseResponse baseResponse) {
        payError();
    }

    private void payError() {
        Toast toast = new Toast(PayActivity.this);
        View view = LayoutInflater.from(PayActivity.this).inflate(R.layout.custom_toast,null);
        TextView textView = view.findViewById(R.id.tv_hint);
        textView.setText("支付异常，请重新扫码支付");
        toast.setView(view);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
