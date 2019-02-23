package com.zjjf.autopos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjjf.autopos.R;
import com.zjjf.autopos.bean.GoodsBean;
import com.zjjf.autopos.common.Constant;
import com.zjjf.autopos.dialog.DeleteGoodsDialog;
import com.zjjf.autopos.utils.DateUtils;
import com.zjjf.autopos.utils.GlideUtils;

import java.util.List;

/**
 * Created by liwinner on 2019/2/23.
 */

public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.ViewHolder> implements DeleteGoodsDialog.DeleteGoodsCallback {

    private Context mContext;
    private List<GoodsBean> mGoodsBeans;
    private GoodsCallback mGoodsCallback;

    public GoodsListAdapter(Context context, List<GoodsBean> goodsBeans,GoodsCallback goodsCallback) {
        this.mContext = context;
        this.mGoodsBeans = goodsBeans;
        this.mGoodsCallback = goodsCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_goods,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        GoodsBean goodsBean = mGoodsBeans.get(position);
        if (goodsBean.getImgUrl() != null) {
            GlideUtils.loadImageViewDiskCache(mContext,goodsBean.getImgUrl(),holder.iv_goods_img);
        } else {
            holder.iv_goods_img.setImageResource(R.drawable.null_img);
        }
        holder.tv_goods_name.setText(goodsBean.getName());
        holder.tv_unit_price.setText(Constant.RMB+ DateUtils.getOneDecimals(goodsBean.getSellingPrice())+"/"+goodsBean.getPkg());
        holder.tv_goods_num.setText(String.valueOf(goodsBean.getGoodsNum()));
        holder.tv_all_price.setText(DateUtils.getOneDecimals(goodsBean.getTotalPrice()));
        holder.iv_delete_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteGoodsDialog deleteGoodsDialog = new DeleteGoodsDialog(mContext,position);
                deleteGoodsDialog.setDeleteGoodsCallback(GoodsListAdapter.this);
                deleteGoodsDialog.show();
            }
        });
        holder.iv_subtract_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGoodsCallback != null) {
                    mGoodsCallback.subtractGoods(position);
                }
            }
        });
        holder.iv_add_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGoodsCallback != null) {
                    mGoodsCallback.addGoods(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGoodsBeans.size();
    }

    @Override
    public void deleteGoods(int position) {
        if (mGoodsCallback != null) {
            mGoodsCallback.deleteGoods(position);
       }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView iv_goods_img;
        private final TextView tv_goods_name;
        private final TextView tv_unit_price;
        private final TextView tv_goods_num;
        private final TextView tv_all_price;
        private final ImageView iv_delete_goods;
        private final ImageView iv_subtract_goods;
        private final ImageView iv_add_goods;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_goods_img = itemView.findViewById(R.id.iv_goods_img);
            tv_goods_name = itemView.findViewById(R.id.tv_goods_name);
            tv_unit_price = itemView.findViewById(R.id.tv_unit_price);
            tv_goods_num = itemView.findViewById(R.id.tv_goods_num);
            tv_all_price = itemView.findViewById(R.id.tv_all_price);
            iv_delete_goods = itemView.findViewById(R.id.iv_delete_goods);
            iv_subtract_goods = itemView.findViewById(R.id.iv_subtract_goods);
            iv_add_goods = itemView.findViewById(R.id.iv_add_goods);
        }
    }

    public interface GoodsCallback{
        void deleteGoods(int position);
        void subtractGoods(int position);
        void addGoods(int position);
    }
}
