package com.zjjf.autopos.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zjjf.autopos.R;
import com.zjjf.autopos.bean.PayCallbackBean;
import com.zjjf.autopos.common.Constant;
import com.zjjf.autopos.custemview.CustomCircleProgressBar;
import com.zjjf.autopos.network.BaseResponse;
import com.zjjf.autopos.network.Novate;
import com.zjjf.autopos.presenter.QueryPayStatus;
import com.zjjf.autopos.utils.DateUtils;
import com.zjjf.autopos.utils.SceneUtil;
import com.zjjf.autopos.utils.ToastUtils;

public class PayProcessActivity extends BaseActivity implements Novate.ResponseCallBack<PayCallbackBean>{

    private TextView tv_all_price,tv_discounts_price,tv_all_number,tv_should_pay;
    private CustomCircleProgressBar pay_progressbar;
    private QueryPayStatus mQueryPayStatus;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_process;
    }

    @Override
    public void initViews() {
        tv_all_price = findViewById(R.id.tv_all_price);
        tv_discounts_price = findViewById(R.id.tv_discounts_price);
        tv_all_number = findViewById(R.id.tv_all_number);
        tv_should_pay = findViewById(R.id.tv_should_pay);
        pay_progressbar = findViewById(R.id.pay_progressbar);
    }

    private int goodsNum;
    private double needPay,totalPrice,discountsPrice;
    private String orderNo;
    private int process = 1;
    @Override
    public void initEvent() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                goodsNum = bundle.getInt("NUMBER");
                needPay = bundle.getDouble("needPay");
                totalPrice = bundle.getDouble("totalPrice");
                discountsPrice = bundle.getDouble("discountsPrice");
                orderNo = bundle.getString("OrderNo","");
            }
        }
        tv_all_price.setText(Constant.RMB+ DateUtils.getOneDecimals(totalPrice));
        tv_discounts_price.setText("-"+Constant.RMB+DateUtils.getOneDecimals(discountsPrice));
        tv_all_number.setText("已付（共"+goodsNum+"件）：");
        tv_should_pay.setText(Constant.RMB+DateUtils.getOneDecimals(needPay));
        mQueryPayStatus = new QueryPayStatus();
        mQueryPayStatus.queryPayStatus(orderNo, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void payError() {
        Toast toast = new Toast(PayProcessActivity.this);
        View view = LayoutInflater.from(PayProcessActivity.this).inflate(R.layout.custom_toast,null);
        TextView textView = view.findViewById(R.id.tv_hint);
        textView.setText("支付异常，请重新扫码支付");
        toast.setView(view);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
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
        PayProcessActivity.this.finish();
    }

    @Override
    public void onSuccee(PayCallbackBean response) {
        if (response.getPayStatus() == 2) {
            payError();
            PayProcessActivity.this.finish();
        } else if (response.getPayStatus() == 9) {
            int processBar =  process * 20;
            if (processBar  >= 100) {
                processBar = 100;
            }
            process ++;
            pay_progressbar.setProgress(processBar);
            mQueryPayStatus.queryPayStatus(orderNo, this);
        } if (response.getPayStatus() == 1) {
            Bundle bundle = new Bundle();
            bundle.putInt("NUMBER",goodsNum);
            bundle.putDouble("needPay",needPay);
            bundle.putDouble("totalPrice",totalPrice);
            bundle.putDouble("discountsPrice",discountsPrice);
            bundle.putString("OrderNo",orderNo);
            SceneUtil.toScene(PayProcessActivity.this,PayActivity.class,bundle);
        }
    }

    @Override
    public void onFail(BaseResponse baseResponse) {
        payError();
        PayProcessActivity.this.finish();
    }
}
