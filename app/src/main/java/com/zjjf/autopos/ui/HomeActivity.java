package com.zjjf.autopos.ui;

import android.os.Bundle;
import android.view.View;

import com.zjjf.autopos.R;
import com.zjjf.autopos.utils.SceneUtil;

public class HomeActivity extends BaseActivity implements View.OnClickListener {


    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initViews() {
        findViewById(R.id.bt_start_scan).setOnClickListener(this);
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
        switch (v.getId()) {
            case R.id.bt_start_scan:
                SceneUtil.toScene(HomeActivity.this,ScanActivity.class,null);
                break;
        }
    }
}
