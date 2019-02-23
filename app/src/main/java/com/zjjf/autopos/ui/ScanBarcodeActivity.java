package com.zjjf.autopos.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.socks.library.KLog;
import com.zjjf.autopos.R;
import com.zjjf.autopos.utils.BarcodeEditTextHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @描述
 * @作者 wangweifeng
 * @时间 2019/2/13 0013
 */
public class ScanBarcodeActivity extends Activity {

    private EditText et_search;
    private BarcodeEditTextHelper barcodeEditTextHelper;
    private Context mContext;
    private Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_barcode);
        mContext = this;
        mActivity = this;
        initSearchaView();
    }

    /**
     * 初始化商品扫码搜索框
     * 备注：这个商品搜索框在UI上不可见，在收银台tab页面全局有效，都可以使用扫码枪搜索商品
     */
    private void initSearchaView() {
        et_search = findViewById(R.id.et_code);
        if(et_search == null)return;
        et_search.requestFocus();
        //初始化扫码搜索框helper类
        barcodeEditTextHelper = initScanCodeSearchEditText(mActivity, et_search, new BarcodeEditTextHelper.AfterTextChanged() {
            @Override
            public void afterTextChanged(String barcode) {
                barcodeEditTextHelper.clearBarcode();
                KLog.d("barcode = " + barcode);
            }
        });
    }

    /***
     * 商品扫码搜索框的处理
     *
     * @param activity
     * @param et_search
     * @param afterTextChanged
     * @return
     */
    public static BarcodeEditTextHelper initScanCodeSearchEditText(Activity activity, EditText et_search, BarcodeEditTextHelper.AfterTextChanged afterTextChanged) {
        hideSoftInputMethod(activity, et_search);
        activity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
                        | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        et_search.requestFocus();

        //初始化搜索框helper类
        return new BarcodeEditTextHelper(activity, et_search, afterTextChanged);
    }

    // 隐藏系统键盘
    public static void hideSoftInputMethod(Activity activity, EditText ed) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        int currentVersion = android.os.Build.VERSION.SDK_INT;
        String methodName = null;
        if (currentVersion >= 16) {
            // 4.2
            methodName = "setShowSoftInputOnFocus";
        } else if (currentVersion >= 14) {
            // 4.0
            methodName = "setSoftInputShownOnFocus";
        }

        if (methodName == null) {
            ed.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName, boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(ed, false);
            } catch (NoSuchMethodException e) {
                ed.setInputType(InputType.TYPE_NULL);
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
