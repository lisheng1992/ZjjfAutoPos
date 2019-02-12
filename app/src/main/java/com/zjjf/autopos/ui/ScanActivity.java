package com.zjjf.autopos.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.socks.library.KLog;
import com.zjjf.autopos.R;
import com.zjjf.autopos.adapter.AddGoodsHolder;
import com.zjjf.autopos.bean.CreateOrderBean;
import com.zjjf.autopos.bean.GoodsBean;
import com.zjjf.autopos.bean.PromotionBean;
import com.zjjf.autopos.bean.PromotionListBean;
import com.zjjf.autopos.bean.QRcodeBean;
import com.zjjf.autopos.common.Constant;
import com.zjjf.autopos.dialog.BarCodeDialog;
import com.zjjf.autopos.dialog.GoodsDetailsDialog;
import com.zjjf.autopos.network.BaseResponse;
import com.zjjf.autopos.network.Novate;
import com.zjjf.autopos.presenter.QueryGoods;
import com.zjjf.autopos.utils.DateUtils;
import com.zjjf.autopos.utils.OperateSharedUtils;
import com.zjjf.autopos.utils.SceneUtil;
import com.zjjf.autopos.utils.SystemUtils;
import com.zjjf.autopos.utils.ToastUtils;

import org.w3c.dom.Text;

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
    private TextView tv_sell_price,tv_all_number,tv_discard_price,tv_discounts_price,tv_count_down;
    private RelativeLayout rl_order;
    private Button bt_balance;
    private RecyclerArrayAdapter<GoodsBean> mGoodslistAdapter;

    private QueryGoods mQueryGoods;
    private ExitDownTimer mExitDownTimer;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scan;
    }

    @Override
    public void initViews() {
        mQueryGoods = new QueryGoods();
        tv_sell_price = findViewById(R.id.tv_sell_price);
        tv_all_number = findViewById(R.id.tv_all_number);
        tv_discard_price = findViewById(R.id.tv_discard_price);
        tv_discounts_price = findViewById(R.id.tv_discounts_price);
        tv_count_down = findViewById(R.id.tv_count_down);
        et_bar_code = findViewById(R.id.et_bar_code);
        ll_empty_goods = findViewById(R.id.ll_empty_goods);
        erv_goods_list = findViewById(R.id.erv_goods_list);
        rl_order = findViewById(R.id.rl_order);
        bt_balance = findViewById(R.id.bt_balance);
        et_bar_code.setShowSoftInputOnFocus(false);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
                        | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        et_bar_code.requestFocus();
        findViewById(R.id.fl_input_barcode).setOnClickListener(this);
        bt_balance.setOnClickListener(this);
        erv_goods_list.setLayoutManager(new LinearLayoutManager(this));
        DividerDecoration itemDecoration = new DividerDecoration(getResources().getColor(R.color.transparent), dip2px(this, 15f), 0, 0);
        itemDecoration.setDrawLastItem(true);
        erv_goods_list.addItemDecoration(itemDecoration);
        mGoodslistAdapter = new RecyclerArrayAdapter<GoodsBean>(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                AddGoodsHolder addGoodsHolder = new AddGoodsHolder(parent,R.layout.item_cart_goods);
                addGoodsHolder.setGoodsCallback(ScanActivity.this);
                return addGoodsHolder;
            }
        };
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
                    for (GoodsBean goodsBean : mGoodslistAdapter.getAllData()) {
                        if (goodsBean.getGoodsId().equals(response.get(0).getGoodsId())) {
                            goodsBean.setGoodsNum(goodsBean.getGoodsNum() + 1);
                            goodsBean.setSellingPrice(response.get(0).getSellingPrice());
                            goodsBean.setName(response.get(0).getName());
                            goodsBean.setPkg(response.get(0).getPkg());
                            goodsBean.setTotalPrice(goodsBean.getGoodsNum() * goodsBean.getSellingPrice());
                            mGoodslistAdapter.remove(i);
                            mGoodslistAdapter.add(0,goodsBean);
                            isExist = true;
                            break;
                        }
                        i++;
                    }
                    if (!isExist) {
                        mGoodslistAdapter.add(0,response.get(0));
                        final GoodsDetailsDialog goodsDetailsDialog = new GoodsDetailsDialog(ScanActivity.this,response.get(0).getImgUrl(),
                                response.get(0).getName(),response.get(0).getSellingPrice());
                        goodsDetailsDialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                goodsDetailsDialog.dismiss();
                            }
                        },1000);
                    }
                    calculateDiscountsPrice();
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
        mExitDownTimer = new ExitDownTimer(1000 * 60,1000);
        mExitDownTimer.start();
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
            case R.id.bt_balance:
                creatWXOrder();
                creatZFBOrder();
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
        mGoodslistAdapter.remove(position);
        calculateDiscountsPrice();
    }

    @Override
    public void subtractGoods(int position) {
        GoodsBean goodsBean = mGoodslistAdapter.getItem(position);
        if (goodsBean.getGoodsNum() > 1) {
            goodsBean.setGoodsNum(goodsBean.getGoodsNum() - 1);
            goodsBean.setTotalPrice(goodsBean.getGoodsNum()*goodsBean.getSellingPrice());
            mGoodslistAdapter.notifyItemChanged(position,goodsBean);
        }
        calculateDiscountsPrice();
    }

    @Override
    public void addGoods(int position) {
        GoodsBean goodsBean = mGoodslistAdapter.getItem(position);
        goodsBean.setGoodsNum(goodsBean.getGoodsNum() + 1);
        goodsBean.setTotalPrice(goodsBean.getGoodsNum()*goodsBean.getSellingPrice());
        mGoodslistAdapter.notifyItemChanged(position,goodsBean);
        calculateDiscountsPrice();
    }

    class ExitDownTimer extends CountDownTimer{

        public ExitDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tv_count_down.setText("取消交易（"+millisUntilFinished/1000+"）");
            if (millisUntilFinished/1000 == 10) {
                CancelOrder cancelOrder = new CancelOrder(ScanActivity.this);
                cancelOrder.show();
            }
            if (tv_dialog_title != null) {
                tv_dialog_title.setText("确认要取消交易吗（"+millisUntilFinished/1000+"秒）？");
            }
        }

        @Override
        public void onFinish() {
            ScanActivity.this.finish();
        }
    }

    private double rebateAmount;
    private String discountDesc = "";
    private String actIds = "";
    private void calculateDiscountsPrice() {
        if (mGoodslistAdapter.getAllData().size() == 0) {
            ll_empty_goods.setVisibility(View.VISIBLE);
            rl_order.setVisibility(View.GONE);
            return;
        }
        rl_order.setVisibility(View.VISIBLE);
        ll_empty_goods.setVisibility(View.GONE);
        rebateAmount = 0;
        discountDesc = "";
        actIds = "";
        Novate.getDefault().executeJson(Constant.URL_PROMOTION, getPromotionParam(), new Novate.ResponseCallBack<BaseResponse<PromotionListBean>>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onSuccee(BaseResponse<PromotionListBean> response) {
                if (response.getMsg() == null) {
                    return;
                }
                PromotionListBean promotionListBean = response.getMsg();
                if (promotionListBean.getSingleActList() != null && promotionListBean.getSingleActList().size() > 0) {
                    for (PromotionBean promotionBean : promotionListBean.getSingleActList()) {
                        actIds += promotionBean.getActId() + ";";
                        discountDesc += promotionBean.getDesc() + ";";
                        rebateAmount += promotionBean.getRebateAmount();
                    }
                    KLog.d(String.valueOf(rebateAmount));
                    int number = 0;
                    double price = 0;
                    for (GoodsBean goodsBean: mGoodslistAdapter.getAllData()) {
                        number += goodsBean.getGoodsNum();
                        price += goodsBean.getTotalPrice();
                    }
                    tv_discard_price.setText(Constant.RMB+DateUtils.getOneDecimals(price));
                    tv_sell_price.setText(Constant.RMB+ DateUtils.getOneDecimals(price-rebateAmount));
                    tv_all_number.setText("（共"+number+"件）");
                    tv_discounts_price.setText("已优惠"+Constant.RMB+DateUtils.getOneDecimals(rebateAmount));
                } else {
                    int number = 0;
                    double price = 0;
                    for (GoodsBean goodsBean: mGoodslistAdapter.getAllData()) {
                        number += goodsBean.getGoodsNum();
                        price += goodsBean.getTotalPrice();
                    }
                    tv_sell_price.setText(Constant.RMB+ DateUtils.getOneDecimals(price));
                    tv_all_number.setText("（共"+number+"件）");
                }
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                int number = 0;
                double price = 0;
                for (GoodsBean goodsBean: mGoodslistAdapter.getAllData()) {
                    number += goodsBean.getGoodsNum();
                    price += goodsBean.getTotalPrice();
                }
                tv_sell_price.setText(Constant.RMB+ DateUtils.getOneDecimals(price));
                tv_all_number.setText("（共"+number+"件）");
            }
        });
    }

    private CreateOrderBean getPromotionParam() {
        CreateOrderBean createOrderBean = new CreateOrderBean();
        createOrderBean.setDetailList(getCommonList());
        return createOrderBean;
    }

    private List<CreateOrderBean.DetailListBean> getCommonList() {
        List<CreateOrderBean.DetailListBean> detailListBeans = new ArrayList<>();
        if (mGoodslistAdapter.getAllData().size() > 0) {
            for (GoodsBean bean : mGoodslistAdapter.getAllData()) {
                CreateOrderBean.DetailListBean detailListBean = new CreateOrderBean.DetailListBean();
                detailListBean.setProductId(bean.getGoodsId());
                detailListBean.setPrice(bean.getSellingPrice());
                detailListBean.setQuantity(bean.getGoodsNum());
                detailListBeans.add(detailListBean);
            }
        }
        return detailListBeans;
    }

    TextView tv_dialog_title;
    class CancelOrder extends Dialog {

        Button delete_goods_cancel,delete_goods_sure;

        public CancelOrder(@NonNull Context context) {
            super(context, R.style.full_screen_dialog);
        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_delete_goods);
            delete_goods_cancel = findViewById(R.id.delete_goods_cancel);
            delete_goods_sure = findViewById(R.id.delete_goods_sure);
            tv_dialog_title = findViewById(R.id.tv_dialog_title);
            delete_goods_cancel.setText("不取消");
            delete_goods_sure.setText("确认取消");
            delete_goods_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    mExitDownTimer.start();
                }
            });
            delete_goods_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    ScanActivity.this.finish();
                }
            });
        }
    }

    private String WXUrl;
    private String ZFBUrl;
    private boolean creatWXSucceed;
    private boolean creatZFBSucceed;
    private void creatWXOrder() {
        Novate.getDefault().executeJson(Constant.URL_CREATE_ORDER, getCreateOrderParam(1), new Novate.ResponseCallBack<BaseResponse<QRcodeBean>>() {
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
            public void onSuccee(BaseResponse<QRcodeBean> response) {
                WXUrl = response.getMsg().getUrl();
                String orderNo = response.getMsg().getOrderNo();
                creatWXSucceed = true;
                if (creatZFBSucceed) {
                    double price = 0;
                    int number = 0;
                    for (GoodsBean goodsBean: mGoodslistAdapter.getAllData()) {
                        price += goodsBean.getTotalPrice();
                        number += goodsBean.getGoodsNum();
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("WXURL",WXUrl);
                    bundle.putString("ZFBURL",ZFBUrl);
                    bundle.putInt("NUMBER",number);
                    bundle.putDouble("needPay",price-rebateAmount);
                    bundle.putDouble("totalPrice",price);
                    bundle.putDouble("discountsPrice",rebateAmount);
                    bundle.putString("OrderNo",orderNo);
                    SceneUtil.toScene(ScanActivity.this,PayActivity.class,bundle);
                }
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                if (baseResponse.getMsg() != null) {
                    ToastUtils.showShortToast(ScanActivity.this,baseResponse.getMsg().toString());
                }
            }
        });
    }

    private void creatZFBOrder() {
        Novate.getDefault().executeJson(Constant.URL_CREATE_ORDER, getCreateOrderParam(2), new Novate.ResponseCallBack<BaseResponse<QRcodeBean>>() {
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
            public void onSuccee(BaseResponse<QRcodeBean> response) {
                ZFBUrl = response.getMsg().getUrl();
                String orderNo = response.getMsg().getOrderNo();
                creatZFBSucceed = true;
                if (creatWXSucceed) {
                    double price = 0;
                    int number = 0;
                    for (GoodsBean goodsBean: mGoodslistAdapter.getAllData()) {
                        price += goodsBean.getTotalPrice();
                        number += goodsBean.getGoodsNum();
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("WXURL",WXUrl);
                    bundle.putString("ZFBURL",ZFBUrl);
                    bundle.putInt("NUMBER",number);
                    bundle.putDouble("needPay",price-rebateAmount);
                    bundle.putDouble("totalPrice",price);
                    bundle.putDouble("discountsPrice",rebateAmount);
                    bundle.putString("OrderNo",orderNo);
                    SceneUtil.toScene(ScanActivity.this,PayActivity.class,bundle);
                }
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                if (baseResponse.getMsg() != null) {
                    ToastUtils.showShortToast(ScanActivity.this,baseResponse.getMsg().toString());
                }
            }
        });
    }

    //"payType": "支付类型(1：微信；2：支付宝)",
    public CreateOrderBean getCreateOrderParam(int payType) {
        CreateOrderBean createOrderBean = new CreateOrderBean();
        createOrderBean.setPayWay(2);
        createOrderBean.setPayType(payType);
        String extra = "";//额外优惠说明
        if (!TextUtils.isEmpty(discountDesc) || !TextUtils.isEmpty(extra)) {
            createOrderBean.setDiscountDesc(discountDesc + extra);
        }
        if (!TextUtils.isEmpty(actIds)) {
            createOrderBean.setPromotionIds(actIds);
        }
        if (!TextUtils.isEmpty(SystemUtils.getDeviceImei(ScanActivity.this))) {
            createOrderBean.setDevice_info(SystemUtils.getDeviceImei(ScanActivity.this));
        } else {
            createOrderBean.setDevice_info("864601022604920");
        }
        createOrderBean.setSubject(OperateSharedUtils.getStoreName(this));
        createOrderBean.setBody("转角街坊便利-" + OperateSharedUtils.getStoreName(this));
        double price = 0;
        for (GoodsBean goodsBean: mGoodslistAdapter.getAllData()) {
            price += goodsBean.getTotalPrice();
        }
        createOrderBean.setOrderAmount(price-rebateAmount);//应付金额
        createOrderBean.setRebateAmount(rebateAmount);
        createOrderBean.setGoodsAmount(price);//订单总额
        createOrderBean.setSpbill_create_ip(SystemUtils.getDeviceWifiIp(getApplicationContext()));

        createOrderBean.setDetailList(getCommonList());
        return createOrderBean;
    }
 }
