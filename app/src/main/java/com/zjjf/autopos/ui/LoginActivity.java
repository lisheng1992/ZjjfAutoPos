package com.zjjf.autopos.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.zjjf.autopos.R;
import com.zjjf.autopos.bean.ResourcesBean;
import com.zjjf.autopos.bean.StoreDetailBean;
import com.zjjf.autopos.bean.UserInfo;
import com.zjjf.autopos.common.Constant;
import com.zjjf.autopos.network.BaseResponse;
import com.zjjf.autopos.network.Novate;
import com.zjjf.autopos.network.utils.NetworkUtil;
import com.zjjf.autopos.utils.OperateSharedUtils;
import com.zjjf.autopos.utils.SceneUtil;
import com.zjjf.autopos.utils.SecurityUtil;
import com.zjjf.autopos.utils.ToastUtils;

import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "LoginActivity";

    private EditText et_user_name,et_user_password;
    private FrameLayout bt_login;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews() {
        et_user_name = findViewById(R.id.et_user_name);
        et_user_password = findViewById(R.id.et_user_password);
        bt_login = findViewById(R.id.bt_login);
    }

    @Override
    public void initEvent() {
        bt_login.setOnClickListener(this);
        et_user_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(et_user_name.getText())) {
                    et_user_name.setBackgroundResource(R.drawable.eee_white_sp);
                } else {
                    et_user_name.setBackgroundResource(R.drawable.login_et_sp);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_user_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(et_user_password.getText())) {
                    et_user_password.setBackgroundResource(R.drawable.eee_white_sp);
                } else {
                    et_user_password.setBackgroundResource(R.drawable.login_et_sp);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login:
                final String userName = et_user_name.getText().toString().trim();
                final String password = et_user_password.getText().toString().trim();
                if (NetworkUtil.isNetworkAvailable()) {
                    if (checkLogin(userName,password)) {
                        showProgressDialog();
                        Map<String,Object> params = new HashMap<>();
                        params.put("username","aaa");
                        params.put("password",SecurityUtil.md5_32Bit("111111"));
                        Novate.getDefault().executePost(Constant.URL_LOGIN, params, new Novate.ResponseCallBack<BaseResponse<UserInfo>>() {
                            @Override
                            public void onStart() {
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
                            public void onSuccee(BaseResponse<UserInfo> response) {
                                /**保存账户数据*/
                                OperateSharedUtils.putStoreName(LoginActivity.this, response.getMsg().getStoreName());
                                OperateSharedUtils.putUserName(LoginActivity.this, response.getMsg().getUsername());
                                OperateSharedUtils.putStoreId(LoginActivity.this, response.getMsg().getStoreId());
                                OperateSharedUtils.putLoginStatus(LoginActivity.this, true);
                                List<ResourcesBean> resourcesBeanList = response.getMsg().getResources();
                                for (ResourcesBean bean : resourcesBeanList) {
                                    bean.setUsername(userName);
                                }
                                if (!DataSupport.isExist(ResourcesBean.class, "username=?", userName)) {
                                    DataSupport.saveAll(resourcesBeanList);
                                } else {
                                    DataSupport.deleteAll(ResourcesBean.class, "username=?", userName);
                                    DataSupport.saveAll(resourcesBeanList);
                                }
                                if (!DataSupport.isExist(UserInfo.class, "username=?", userName)) {
                                    UserInfo userInfo = response.getMsg();
                                    userInfo.save();
                                }
                                SceneUtil.toScene(LoginActivity.this, InitializeActivity.class, null);
                                finish();
                            }

                            @Override
                            public void onFail(BaseResponse baseResponse) {
                                if (baseResponse.getMsg() != null) {
                                    ToastUtils.showShortToast(LoginActivity.this, baseResponse.getMsg().toString());
                                }
                            }
                        });
                    }
                } else {
                    List<UserInfo> userInfos = DataSupport.where("username=?", userName).find(UserInfo.class);
                    if (userInfos.size() > 0 && userInfos.get(0) != null) {
                        if (userInfos.get(0).getPassword().equals(SecurityUtil.md5_32Bit(password))) {
                            OperateSharedUtils.putUserName(this, userName);
                            OperateSharedUtils.putStoreName(this, userInfos.get(0).getStoreName());
                            OperateSharedUtils.putLoginStatus(this, true);
                            SceneUtil.toScene(LoginActivity.this, HomeActivity.class, null);
                            finish();
                        } else {
                            ToastUtils.showShortToast(this, "账号或密码错误！");
                        }
                    } else {
                        ToastUtils.showShortToast(this, "请打开网络！");
                    }
                }
                break;
            default:
                break;
        }
    }

    private void getStoreDetail() {
        Novate.getDefault().executeGet(Constant.URL_GET_STORE_DETAIL, null, new Novate.ResponseCallBack<BaseResponse<StoreDetailBean>>() {
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
            public void onSuccee(BaseResponse<StoreDetailBean> response) {

            }

            @Override
            public void onFail(BaseResponse baseResponse) {

            }
        });
    }

    private boolean checkLogin(String userName,String password) {
        if (TextUtils.isEmpty(userName)) {
            ToastUtils.showShortToast(LoginActivity.this, "请输入账号");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showShortToast(LoginActivity.this, "请输入密码");
            return false;
        }
        return true;
    }
}
