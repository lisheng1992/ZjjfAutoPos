package com.zjjf.autopos.adapter;


import android.view.View;
import android.view.ViewGroup;

import com.zjjf.autopos.R;
import com.zjjf.autopos.bean.GoodsBean;
import com.zjjf.autopos.dialog.DeleteGoodsDialog;

import easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by liwinner on 2019/1/21.
 */

public class AddGoodsHolder extends BaseViewHolder<GoodsBean> implements DeleteGoodsDialog.DeleteGoodsCallback {


    private GoodsCallback mGoodsCallback;

    public AddGoodsHolder setGoodsCallback(GoodsCallback goodsCallback) {
        mGoodsCallback = goodsCallback;
        return this;
    }

    public AddGoodsHolder(ViewGroup parent, int res) {
        super(parent, res);
    }

    @Override
    public void setData(GoodsBean data,final int position) {
        super.setData(data, position);
//        setImageUrl(R.id.iv_goods_img,data.getImgUrl());
//        if (data.getImgUrl() != null) {
//            setImageUrl(R.id.iv_goods_img,data.getImgUrl());
//        } else {
//            setImageResource(R.id.iv_goods_img,R.drawable.null_img);
//        }
//        setText(R.id.tv_goods_name,data.getName());
//        //setText(R.id.tv_discounts,data.getExpirationDate());
//        setText(R.id.tv_unit_price, Constant.RMB+ DateUtils.getOneDecimals(data.getSellingPrice())+"/"+data.getPkg());
//        setText(R.id.tv_goods_num,String.valueOf(data.getGoodsNum()));
//        setText(R.id.tv_all_price,DateUtils.getOneDecimals(data.getTotalPrice()));
        setOnClickLisenter(R.id.iv_delete_goods, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteGoodsDialog deleteGoodsDialog = new DeleteGoodsDialog(mContext,position);
                deleteGoodsDialog.setDeleteGoodsCallback(AddGoodsHolder.this);
                deleteGoodsDialog.show();
            }
        });

        setOnClickLisenter(R.id.iv_subtract_goods, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGoodsCallback != null) {
                    mGoodsCallback.subtractGoods(position);
                }
            }
        });

        setOnClickLisenter(R.id.iv_add_goods, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGoodsCallback != null) {
                    mGoodsCallback.addGoods(position);
                }
            }
        });
    }

    @Override
    public void deleteGoods(int position) {
        if (mGoodsCallback != null) {
            mGoodsCallback.deleteGoods(position);
        }
    }

    public interface GoodsCallback{
        void deleteGoods(int position);
        void subtractGoods(int position);
        void addGoods(int position);
    }

}
