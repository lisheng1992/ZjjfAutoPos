//package com.zjjf.autopos.ui;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.text.Editable;
//import android.text.InputType;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.EditText;
//
//
//import com.ruffian.library.widget.REditText;
//import com.zjjf.autopos.R;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//
///**
// * @描述 扫码输入
// * @作者 wangweifeng
// * @时间 2018/11/21 0021
// */
//public class ScanBarcodeActivity extends BaseActivity {
//
//    private REditText etSearch;
//
//    public static void start(Context context){
//        Intent intent = new Intent(context,ScanBarcodeActivity.class);
//        context.startActivity(intent);
//    }
//
//    private AfterTextChanged afterTextChanged = new AfterTextChanged() {
//        @Override
//        public void afterTextChanged(String barcode) {
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    };
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_scan_barcode;
//    }
//
//    @Override
//    public void initViews() {
//        etSearch = findViewById(R.id.et_code);
//
//        hideSoftInputMethod(etSearch);
//        getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
//                        | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        etSearch.requestFocus();
//
//        etSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String barCode = s.toString();
//                if(!TextUtils.isEmpty(barCode) && barCode.length() > 2 && barCode.endsWith("\n")){
//                    barCode = barCode.replace("\n","");
//                    etSearch.setText("");
//                    if(validateBarcode(barCode)) {
//                        afterTextChanged.afterTextChanged(barCode);
//                    }
//                }
//            }
//        });
//
//        findViewById(R.id.tv_handle_clean).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                etSearch.setEnabled(true);
//                etSearch.requestFocus();
//                etSearch.setText("");
//            }
//        });
//
//        findViewById(R.id.tv_handle_enable).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                etSearch.setEnabled(false);
//                etSearch.clearFocus();
//            }
//        });
//    }
//
//    @Override
//    public void initEvent() {
//
//    }
//
//    public interface AfterTextChanged {
//        void afterTextChanged(String barcode);
//    }
//
//    public static boolean validateBarcode(String barcode) {
//        if (TextUtils.isEmpty(barcode)) {
//            return false;
//        }
//        return barcode.length() > 0 && barcode.length() <= 14;
//    }
//
//    // 隐藏系统键盘
//    public void hideSoftInputMethod(EditText ed) {
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//
//        int currentVersion = android.os.Build.VERSION.SDK_INT;
//        String methodName = null;
//        if (currentVersion >= 16) {
//            // 4.2
//            methodName = "setShowSoftInputOnFocus";
//        } else if (currentVersion >= 14) {
//            // 4.0
//            methodName = "setSoftInputShownOnFocus";
//        }
//
//        if (methodName == null) {
//            ed.setInputType(InputType.TYPE_NULL);
//        } else {
//            Class<EditText> cls = EditText.class;
//            Method setShowSoftInputOnFocus;
//            try {
//                setShowSoftInputOnFocus = cls.getMethod(methodName, boolean.class);
//                setShowSoftInputOnFocus.setAccessible(true);
//                setShowSoftInputOnFocus.invoke(ed, false);
//            } catch (NoSuchMethodException e) {
//                ed.setInputType(InputType.TYPE_NULL);
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IllegalArgumentException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//    }
//
//}
