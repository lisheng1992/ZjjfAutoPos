package com.zjjf.autopos.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.zjjf.autopos.R;
import com.zjjf.autopos.dialog.BarCodeDialog;

import easyrecyclerview.EasyRecyclerView;

public class ScanActivity extends BaseActivity implements View.OnClickListener, BarCodeDialog.BarCodeCallback {

    private EasyRecyclerView erv_goods_list;
    private LinearLayout ll_empty_goods;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scan;
    }

    @Override
    public void initViews() {
        ll_empty_goods = findViewById(R.id.ll_empty_goods);
        erv_goods_list = findViewById(R.id.erv_goods_list);
        findViewById(R.id.fl_input_barcode).setOnClickListener(this);
    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fl_input_barcode:
                BarCodeDialog barCodeDialog = new BarCodeDialog(this);
                barCodeDialog.setBarCodeCallback(this);
                barCodeDialog.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void barCodeCallback(String barCode) {

    }
}
