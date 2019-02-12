package com.zjjf.autopos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjjf.autopos.R;
import com.zjjf.autopos.common.Constant;
import com.zjjf.autopos.utils.DateUtils;
import com.zjjf.autopos.utils.GlideUtils;

/**
 * Created by liwinner on 2019/2/3.
 */

public class GoodsDetailsDialog extends Dialog{

    private ImageView iv_goods_img;
    private TextView tv_goods_name,tv_price;
    private String url,goodsName;
    private double price;
    private Context mContext;
    public GoodsDetailsDialog(@NonNull Context context,String url,String goodsName,double price) {
        super(context, R.style.full_screen_dialog);
        this.url = url;
        this.goodsName = goodsName;
        this.price = price;
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_goods_details);
        iv_goods_img = findViewById(R.id.iv_goods_img);
        tv_goods_name = findViewById(R.id.tv_goods_name);
        tv_price = findViewById(R.id.tv_price);
        GlideUtils.loadImageViewDiskCache(mContext,url,iv_goods_img);
        tv_goods_name.setText(goodsName);
        tv_price.setText(Constant.RMB+ DateUtils.getOneDecimals(price));
    }
}
