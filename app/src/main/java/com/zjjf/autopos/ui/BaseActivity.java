package com.zjjf.autopos.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;


import com.squareup.leakcanary.RefWatcher;
import com.zjjf.autopos.R;
import com.zjjf.autopos.ZjjfPosApplication;
import com.zjjf.autopos.utils.ActManager;

import easyrecyclerview.EasyRecyclerView;
import easyrecyclerview.decoration.DividerDecoration;

import static com.zjjf.autopos.utils.UIUtils.dip2px;


/**
 * Created by ls on 2017/4/18.
 */

public abstract class BaseActivity extends AppCompatActivity {


    public abstract int getLayoutId();

    public abstract void initViews();

    public abstract void initEvent();

    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = getLayoutId();
        setContentView(layoutId);
        initViews();
        initEvent();
        //添加activity到栈顶
        ActManager.getInstances().add(this);
    }

    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //添加返回键处理
    }

    public void showProgressDialog() {
        try {
//            LayoutInflater inflater = getLayoutInflater();
//            View mView = inflater.inflate(R.layout.alertxml,null);
            if (mProgressDialog == null ) {
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();
//                mProgressDialog.getWindow().setContentView(mView);
            } else if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
//                mProgressDialog.getWindow().setContentView(mView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissProgressDialog() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = ZjjfPosApplication.getRefWatcher(this);
        refWatcher.watch(this);
    }

    public void initRecyclerView(EasyRecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerDecoration itemDecoration = new DividerDecoration(getResources().getColor(R.color.color_eeeeee), dip2px(this, 1f), 0, 0);
        itemDecoration.setDrawLastItem(true);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setRefreshingColorResources(R.color.color_0099cc, R.color.color_ff8800, R.color.color_669900);
    }

//
// private void listenScreen(){
//        final IntentFilter filter = new IntentFilter();
//        filter.addAction(Intent.ACTION_SCREEN_OFF);
//        filter.addAction(Intent.ACTION_SCREEN_ON);
//        mBatInfoReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(final Context context, final Intent intent) {
//                String action = intent.getAction();
//                if (Intent.ACTION_SCREEN_ON.equals(action)) {
//                    sendBroadcast(new Intent(ZjjfPosApplication.GET_ADS_PICTURE_ACTION));
//                }
////                else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
////                    stopService(new Intent(getApplicationContext(), DisplayService.class));
////                }
//            }
//        };
//        registerReceiver(mBatInfoReceiver, filter);
//    }
}
