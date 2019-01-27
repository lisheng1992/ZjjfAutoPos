package com.zjjf.autopos.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.socks.library.KLog;
import com.zjjf.autopos.R;
import com.zjjf.autopos.adapter.AddGoodsHolder;
import com.zjjf.autopos.bean.GoodsBean;
import com.zjjf.autopos.dialog.BarCodeDialog;
import com.zjjf.autopos.network.BaseResponse;
import com.zjjf.autopos.network.Novate;
import com.zjjf.autopos.presenter.QueryGoods;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import easyrecyclerview.EasyRecyclerView;
import easyrecyclerview.adapter.BaseViewHolder;
import easyrecyclerview.adapter.RecyclerArrayAdapter;
import easyrecyclerview.decoration.DividerDecoration;

import static com.zjjf.autopos.utils.UIUtils.dip2px;

public class ScanActivity extends BaseActivity implements View.OnClickListener, BarCodeDialog.BarCodeCallback, AddGoodsHolder.GoodsCallback {

    private EasyRecyclerView erv_goods_list;
    private LinearLayout ll_empty_goods;
    private EditText et_bar_code;
    private List<GoodsBean> goodsList;
    private RecyclerArrayAdapter<GoodsBean> mGoodslistAdapter;

    private QueryGoods mQueryGoods;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scan;
    }

    @Override
    public void initViews() {
        mQueryGoods = new QueryGoods();
        goodsList = new ArrayList<>();
        et_bar_code = findViewById(R.id.et_bar_code);
        ll_empty_goods = findViewById(R.id.ll_empty_goods);
        erv_goods_list = findViewById(R.id.erv_goods_list);
        et_bar_code.setShowSoftInputOnFocus(false);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
                        | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        et_bar_code.requestFocus();
        findViewById(R.id.fl_input_barcode).setOnClickListener(this);

        erv_goods_list.setLayoutManager(new LinearLayoutManager(this));
        DividerDecoration itemDecoration = new DividerDecoration(getResources().getColor(R.color.transparent), dip2px(this, 15f), 0, 0);
        itemDecoration.setDrawLastItem(true);
        erv_goods_list.addItemDecoration(itemDecoration);
        for (int i = 0;i < 10;i++){
            goodsList.add(new GoodsBean());
        }
        mGoodslistAdapter = new RecyclerArrayAdapter<GoodsBean>(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                AddGoodsHolder addGoodsHolder = new AddGoodsHolder(parent,R.layout.item_cart_goods);
                addGoodsHolder.setGoodsCallback(ScanActivity.this);
                return addGoodsHolder;
            }
        };
        mGoodslistAdapter.addAll(goodsList);
        erv_goods_list.setAdapter(mGoodslistAdapter);

    }

    private void queryGoods(String barCode) {
        mQueryGoods.queryGoods(barCode, new Novate.ResponseCallBack<List<GoodsBean>>() {
            @Override
            public void onStart() {
                showProgressDialog();
            }

            @Override
            public void onCompleted() {
                dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                dismissProgressDialog();
            }

            @Override
            public void onSuccee(List<GoodsBean> response) {
                KLog.i(goodsList);
                if (et_bar_code != null) {
                    et_bar_code.setText("");
                }
                if (response.size() == 0) {
                    Toast toast = new Toast(ScanActivity.this);
                    View view = LayoutInflater.from(ScanActivity.this).inflate(R.layout.custom_toast,null);
                    toast.setView(view);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();
                } else if (response.size() == 1) {
                    ll_empty_goods.setVisibility(View.GONE);
                    int i = 0;
                    boolean isExist = false;
                    for (GoodsBean goodsBean : goodsList) {
                        if (goodsBean.getGoodsId().equals(response.get(0).getGoodsId())) {
                            goodsBean.setGoodsNum(goodsBean.getGoodsNum() + 1);
                            goodsBean.setSellingPrice(response.get(0).getSellingPrice());
                            goodsBean.setName(response.get(0).getName());
                            goodsBean.setPkg(response.get(0).getPkg());
                            goodsBean.setTotalPrice(goodsBean.getGoodsNum() * goodsBean.getSellingPrice());
                            goodsList.remove(i);
                            goodsList.add(0, goodsBean);
                            mGoodslistAdapter.notifyDataSetChanged();
                            isExist = true;
                            break;
                        }
                        i++;
                    }
                    if (!isExist) {
                        goodsList.add(0, goodsList.get(0));
                        mGoodslistAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                dismissProgressDialog();
            }
        });
    }

    @Override
    public void initEvent() {
        et_bar_code.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                hideInputMethod(et_bar_code);
                if (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && event.getAction() == KeyEvent.ACTION_DOWN){
                    String barCode = et_bar_code.getText().toString();
                    if (!TextUtils.isEmpty(barCode)) {
                        queryGoods(barCode);
                    }
                    return true;
                }
                return true;
            }
        });
//        et_bar_code.addTextChangedListener(new TextWatcher() {
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
//                    et_bar_code.setText("");
//                    if(validateBarcode(barCode)) {
//                        afterTextChanged.afterTextChanged(barCode);
//                    }
//                }
//            }
//        });
    }

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fl_input_barcode:
                BarCodeDialog barCodeDialog = new BarCodeDialog(this);
                barCodeDialog.setBarCodeCallback(this);
                barCodeDialog.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void barCodeCallback(String barCode) {
        queryGoods(barCode);
    }

    // 隐藏系统键盘
    public void hideSoftInputMethod(EditText ed) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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

    @Override
    public void deleteGoods(int position) {
        mGoodslistAdapter.notifyItemRemoved(position);
    }

    @Override
    public void subtractGoods(int position) {

    }

    @Override
    public void addGoods(int position) {

    }

    private void calculatePriceAndNumber() {
        if (mGoodslistAdapter.getAllData().size() > 0) {
            int number = 0;
            double price = 0;
            for (GoodsBean goodsBean: mGoodslistAdapter.getAllData()) {
                number += goodsBean.getGoodsNum();
                price += goodsBean.getTotalPrice();
            }

        }
    }
}
