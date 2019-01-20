package com.zjjf.autopos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zjjf.autopos.bean.UserInfo;
import com.zjjf.autopos.common.Constant;
import com.zjjf.autopos.network.BaseResponse;
import com.zjjf.autopos.network.Novate;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //private static

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }
}
