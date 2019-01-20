package com.zjjf.autopos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zjjf.autopos.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwinner on 2019/1/20.
 */

public class BarCodeDialog extends Dialog implements View.OnClickListener {

    private EditText et_bar_code;
    private List<TextView> tvNumbers = new ArrayList<>();
    private BarCodeCallback mBarCodeCallback;

    public void setBarCodeCallback(BarCodeCallback barCodeCallback) {
        mBarCodeCallback = barCodeCallback;
    }

    public BarCodeDialog(@NonNull Context context) {
        super(context, R.style.full_screen_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bar_code);
        et_bar_code = findViewById(R.id.et_bar_code);
        findViewById(R.id.iv_cancel_barcode).setOnClickListener(this);
        tvNumbers.add((TextView) findViewById(R.id.tv_num_zero));
        tvNumbers.add((TextView) findViewById(R.id.tv_num_one));
        tvNumbers.add((TextView) findViewById(R.id.tv_num_two));
        tvNumbers.add((TextView) findViewById(R.id.tv_num_three));
        tvNumbers.add((TextView) findViewById(R.id.tv_num_four));
        tvNumbers.add((TextView) findViewById(R.id.tv_num_five));
        tvNumbers.add((TextView) findViewById(R.id.tv_num_six));
        tvNumbers.add((TextView) findViewById(R.id.tv_num_seven));
        tvNumbers.add((TextView) findViewById(R.id.tv_num_eigth));
        tvNumbers.add((TextView) findViewById(R.id.tv_num_nine));
        for (int i = 0;i < 10;i++){
            tvNumbers.get(i).setOnClickListener(this);
        }
        findViewById(R.id.tv_num_clear).setOnClickListener(this);
        findViewById(R.id.fl_back).setOnClickListener(this);
        findViewById(R.id.bt_sure).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String text = et_bar_code.getText().toString();
        switch (v.getId()){
            case R.id.iv_cancel_barcode:
                dismiss();
                break;
            case R.id.tv_num_zero:
                et_bar_code.setText(text+"0");
                break;
            case R.id.tv_num_one:
                et_bar_code.setText(text+"1");
                break;
            case R.id.tv_num_two:
                et_bar_code.setText(text+"2");
                break;
            case R.id.tv_num_three:
                et_bar_code.setText(text+"3");
                break;
            case R.id.tv_num_four:
                et_bar_code.setText(text+"4");
                break;
            case R.id.tv_num_five:
                et_bar_code.setText(text+"5");
                break;
            case R.id.tv_num_six:
                et_bar_code.setText(text+"6");
                break;
            case R.id.tv_num_seven:
                et_bar_code.setText(text+"7");
                break;
            case R.id.tv_num_eigth:
                et_bar_code.setText(text+"8");
                break;
            case R.id.tv_num_nine:
                et_bar_code.setText(text+"9");
                break;
            case R.id.tv_num_clear:
                et_bar_code.setText("");
                break;
            case R.id.fl_back:
                if (!TextUtils.isEmpty(text)) {
                    text = text.substring(0,text.length() - 1);
                    et_bar_code.setText(text);
                }
                break;
            case R.id.bt_sure:
                if (!TextUtils.isEmpty(text)) {
                    if (mBarCodeCallback != null) {
                        mBarCodeCallback.barCodeCallback(text);
                    }
                }
                break;
             default:
                 break;
        }
    }

    public interface BarCodeCallback {
        void barCodeCallback(String barCode);
    }
}
