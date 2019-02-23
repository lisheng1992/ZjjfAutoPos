package com.zjjf.autopos.utils;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.socks.library.KLog;

/**
 * Created by liwinner on 2019/2/13.
 */

public class BarcodeEditTextHelper {
    public static final int BARCODE_MAX_LENGTH = 14;
    private int textSelectIndex;
    private AfterTextChanged afterTextChanged;
    private StringBuffer barcode = new StringBuffer();
    private EditText tv_barcode;

    private CommonAfterTextChanged mCommonAfterTextChanged;

    public  BarcodeEditTextHelper(Activity activity, EditText editText, AfterTextChanged afterTextChanged) {
        this.tv_barcode = editText;
        this.tv_barcode.addTextChangedListener(textWatcher);
        this.afterTextChanged = afterTextChanged;
    }

    public void deleteBarcodeNum() {
        int length = barcode.length();
        if (length == 0)
            return;
        if (length == 1) {
            clearBarcode();
            return;
        }
        barcode.deleteCharAt(barcode.length() - 1);
        tv_barcode.setText(barcode);
    }


    public void clearBarcode() {
        textSelectIndex = 0;
        barcode = new StringBuffer();
        tv_barcode.setText("");
    }

    public void confirmSearch(Context context) {
        if (TextUtils.isEmpty(barcode.toString())) {
            Toast.makeText(context,"请输入或扫描条形码",Toast.LENGTH_SHORT).show();
            clearBarcode();
            return;
        }
        if (validateBarcode(barcode.toString())) {
            afterTextChanged.afterTextChanged(barcode.toString());
        } else {
            clearBarcode();
            Toast.makeText(context,"请输入正确的条码",Toast.LENGTH_SHORT).show();
        }
    }

    /***
     * TextWatcher afterTextChanged专门针对商品条形码扫码场景的处理
     */
    public interface AfterTextChanged {
        void afterTextChanged(String barcode);
    }

    /***
     * TextWatcher afterTextChanged通用接口
     */
    public interface CommonAfterTextChanged {
        void afterTextChanged(Editable s);
    }

    public void setCommonAfterTextChanged(CommonAfterTextChanged commonAfterTextChanged){
        this.mCommonAfterTextChanged = commonAfterTextChanged;
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(mCommonAfterTextChanged != null){
                mCommonAfterTextChanged.afterTextChanged(s);
            }

            String barCode = s.toString();
            KLog.d("afterTextChanged barCode = " + barCode);
            if(barCode.startsWith("\n")){
                barCode = barCode.replace("\n", "");
                tv_barcode.setText(barCode);
                tv_barcode.setSelection(barCode.length());
            }
            if (barCode.contains("\n")) {
                barCode = barCode.replace("\n", "");
                tv_barcode.setText(barCode);
                tv_barcode.setSelection(barCode.length());

                KLog.d("barcodeEditText helper ---" + barCode);
                if (validateBarcode(barCode)) {
                    afterTextChanged.afterTextChanged(barCode);
                } else {
                    clearBarcode();
                    //ToastCommom.ToastShow("请输入正确的条码");
                }
            } else if (textSelectIndex > 0) {
                tv_barcode.setSelection(textSelectIndex);
            }
        }
    };

    public  boolean validateBarcode(String barcode) {
        if (TextUtils.isEmpty(barcode)) {
            return false;
        }
        return barcode.length() > 0;
    }

}
