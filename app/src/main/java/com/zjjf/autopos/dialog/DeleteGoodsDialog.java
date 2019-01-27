package com.zjjf.autopos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.zjjf.autopos.R;

/**
 * Created by liwinner on 2019/1/27.
 */

public class DeleteGoodsDialog extends Dialog {

    private DeleteGoodsCallback mDeleteGoodsCallback;
    private int mPosition;

    public DeleteGoodsDialog setDeleteGoodsCallback(DeleteGoodsCallback deleteGoodsCallback) {
        mDeleteGoodsCallback = deleteGoodsCallback;
        return this;
    }

    public DeleteGoodsDialog(@NonNull Context context, int position) {
        super(context, R.style.full_screen_dialog);
        this.mPosition = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_delete_goods);
        findViewById(R.id.delete_goods_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        findViewById(R.id.delete_goods_sure).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
               if (mDeleteGoodsCallback != null) {
                   mDeleteGoodsCallback.deleteGoods(mPosition);
               }
                dismiss();
            }
        });
    }

    public interface DeleteGoodsCallback{
        void deleteGoods(int position);
    }
}
