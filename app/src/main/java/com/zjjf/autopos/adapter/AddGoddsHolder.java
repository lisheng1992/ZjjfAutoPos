package com.zjjf.autopos.adapter;


import android.view.ViewGroup;

import com.zjjf.autopos.R;
import com.zjjf.autopos.bean.GoodsBean;
import com.zjjf.autopos.common.Constant;
import com.zjjf.autopos.utils.DateUtils;

import easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by liwinner on 2019/1/21.
 */

public class AddGoddsHolder extends BaseViewHolder<GoodsBean> {


    public AddGoddsHolder(ViewGroup parent, int res) {
        super(parent, res);
    }

    @Override
    public void setData(GoodsBean data, int position) {
        super.setData(data, position);
        setImageUrl(R.id.iv_goods_img,data.getImgUrl());
        if (data.getImgUrl() != null) {
            setImageUrl(R.id.iv_goods_img,data.getImgUrl());
        } else {
            setImageResource(R.id.iv_goods_img,R.drawable.null_img);
        }
        setText(R.id.tv_goods_name,data.getName());
        //setText(R.id.tv_discounts,data.getExpirationDate());
        setText(R.id.tv_unit_price, Constant.RMB+ DateUtils.getOneDecimals(data.getSellingPrice())+"/"+data.getPkg());
        setText(R.id.tv_goods_num,String.valueOf(data.getGoodsNum()));
        setText(R.id.tv_all_price,DateUtils.getOneDecimals(data.getTotalPrice()));
    }
}
