package com.zjjf.autopos.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.zjjf.autopos.R;
import com.zjjf.autopos.common.Constant;
import com.zjjf.autopos.utils.DateUtils;

public class PaySucceedActivity extends BaseActivity {

    private TextView tv_all_price,tv_discounts_price,tv_all_number,tv_should_pay;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_succeed;
    }

    @Override
    public void initViews() {
        tv_all_price = findViewById(R.id.tv_all_price);
        tv_discounts_price = findViewById(R.id.tv_discounts_price);
        tv_all_number = findViewById(R.id.tv_all_number);
        tv_should_pay = findViewById(R.id.tv_should_pay);
    }

    private int goodsNum;
    private double needPay,totalPrice,discountsPrice;
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
            }
        }
        tv_all_price.setText(Constant.RMB+ DateUtils.getOneDecimals(totalPrice));
        tv_discounts_price.setText("-"+Constant.RMB+DateUtils.getOneDecimals(discountsPrice));
        tv_all_number.setText("已付（共"+goodsNum+"件）：");
        tv_should_pay.setText(Constant.RMB+DateUtils.getOneDecimals(needPay));
    }

}
