package com.zjjf.autopos.common;

/**
 * Created by ls on 2017/5/9.
 */

public class Constant {

    /**
     * 与服务器端连接的协议名
     */
//    public static final String PROTOCAL = "https://";
    public static final String PROTOCAL = "http://";
    /**
     * 服务器IP
     */
    public static final String HOST = "www.jfun365.cn";
    //public static final String HOST = "192.168.1.10";
//     public static final String HOST = "192.168.1.188";
    //public static final String HOST = "112.74.196.61";
    /**
     * 外网映射地址:192.168.1.10:8182
     */
//    public static final String HOST = "pos.ngrok.xiaomiqiu.cn";
    /**
     * 服务器端口号
     */
    //public static final String PORT = ":8182/";
    //public static final String PORT = ":8185/";
    public static final String PORT = "/";

    /**
     * 工程名
     * 收银系统 pos
     * 微便利 wbl
     */
   // public static final String PROJECT_NAME = "pos/";
//    public static final String PROJECT_NAME = "wbl/";
    public static final String PROJECT_NAME = "test/";

    /**
     * 应用上下文完整路径
     */
    public static final String CONTEXTPATH = PROTOCAL + HOST + PORT + PROJECT_NAME;

    /**
     * 登录完整路径
     */
     public static final String URL_LOGIN = CONTEXTPATH + "login";
    /**
     * 退出登录
     */
    public static final String URL_LOGINOUT = CONTEXTPATH + "logout";
    /**
     * 获取用户列表
     */
    public static final String URL_USER_LIST = CONTEXTPATH + "account/user/list";
    /**
     * 分类列表
     */
    public static final String URL_CLASSIFY = CONTEXTPATH + "api/category/list/";
    /**
     * 商品列表
     */
    public static final String URL_GOODS_LIST = CONTEXTPATH + "api/product/list";
    /**
     * 删除用户
     */
    public static final String URL_DELETE_CASHIER = CONTEXTPATH + "api/account/user/delete/";
    /**
     * 新增商品
     */
    public static final String URL_ADD_GOODS = CONTEXTPATH + "api/product/create";
    /**
     * 编辑更新商品
     */
    public static final String URL_UPDATE_GOODS = CONTEXTPATH + "api/product/update";

    /**
     * 获取便利店信息
     */
    public static final String URL_GET_STORE_DETAIL = CONTEXTPATH + "api/store/detail";
    /**
     * 批量上传文件
     */
    public static final String URL_POST_UPLOAD_BATCH = CONTEXTPATH + "api/upload";
    /**
     * 获取系统所有资源权限信息
     */
    public static final String URL_GET_ALL_RESOURCE = CONTEXTPATH + "api/resource/list";
    /**
     * 用户列表数据
     */
    public static final String URL_GET_ALL_CASHIER = CONTEXTPATH + "api/account/user/list";
    /**
     * 便利店添加角色
     */
    public static final String URL_STORE_ADD_CASHIER = CONTEXTPATH + "api/account/user/create";
    /**便利店编辑用户*/
    public static final String URL_STORE_EDIT_CASHIER = CONTEXTPATH + "api/account/user/update";
    /**根据用户ID获取用户详情*/
    public static final String URL_GET_USER_DETAIL_BY_ID = CONTEXTPATH + "api/account/user/detail/";
    /**更新便利店广告设置*/
    public static final String URL_UPDATE_STORE_ADVERTISEMENT = CONTEXTPATH + "api/store/update/advertisement";
    /**更新便利店打印设置*/
    public static final String URL_UPDATE_STORE_PRINT_SETUP = CONTEXTPATH + "api/store/update/print/setup";
    /**入库列表**/
    public static final String URL_GET_WARE_HOUSE_LIST = CONTEXTPATH + "api/inventory/stockin/list";
    /**新增入库**/
    public static final String URL_ADD_STOCK = CONTEXTPATH + "api/inventory/stockin/create";
    /**添加订单*/
    public static final String URL_ADD_ORDER = CONTEXTPATH + "api/order/create";
    /**生成支付订单*/
    public static final String URL_CREATE_ORDER = CONTEXTPATH + "api/order/getpayurl";
    /**根据订单号查询订单信息*/
    public static final String URL_QUERY_ORDERINFO_BY_ORDERID = CONTEXTPATH + "api/order/getorderinfobyorderno";
    /**退款订单*/
    public static final String URL_ORDER_REFUND = CONTEXTPATH + "api/order/refund";
    /**查询支付结果*/
    public static final String URL_QUERY_PAY = CONTEXTPATH + "order/trade/query";
    /**优惠活动*/
    public static final String URL_PROMOTION = CONTEXTPATH + "api/order/activity";
    /**获取订单信息*/
    public static final String URL_DATA_REPORT = CONTEXTPATH + "api/order/list";
    /**条码查询国家标准商品库*/
    public static final String URL_BARCODE_LIBRARY = CONTEXTPATH+"api/product/gs1/";
    /**获取最新版本的App信息*/
    public static final String URL_GET_LATEST_VERSION = CONTEXTPATH+"openapi/app/latest";

    public static final String COOKIE_PREFS = "Novate_Cookies_Prefs";
    /**查询报损列表*/
    public static final String URL_QUERY_SCRAP_LIST = CONTEXTPATH+"api/inventory/scrap/list";
    /**删除报损*/
    public static final String URL_DELETE_SCRAP_ITEM = CONTEXTPATH+"api/inventory/scrap/delete/";
    /**新建报损*/
    public static final String URL_CREATE_SCRAP_ITEM = CONTEXTPATH+"api/inventory/scrap/create";
    /**更新商品入库单*/
    public static final String URL_STOCKIN_UPDATE = CONTEXTPATH+"api/inventory/stockin/update";
    /**确认入库*/
    public static final String URL_STOCKIN_CONFIRM = CONTEXTPATH+"api/inventory/stockin/confirm/";
    /**删除入库单*/
    public static final String URL_STOCKIN_DELETE = CONTEXTPATH+"api/inventory/stockin/delete/";
    /**生成交接班数据**/
    public static final String URL_CREATE_SHIFT = CONTEXTPATH+"api/shift/update";
    /**修改交接班数据**/
    public static final String URL_UPDATE_SHIFT = CONTEXTPATH+"api/shift/upstate";
    /**上传错误日志**/
    public static final String URL_UPLOAD_ERROR_LOG = CONTEXTPATH+"openapi/app/error/log";

    /**预警商品列表*/
    public static final String URL_WARN_PROD_LIST = CONTEXTPATH+"api/warn/prod/list";
    /**下单完成列表*/
    public static final String URL_WARN_ORDER_LIST = CONTEXTPATH+"api/warn/order/list";
    /**一键下单*/
    public static final String URL_WARN_ORDER = CONTEXTPATH+"api/warn/order";


    public static final String DEFAULT = "DEFAULTCODE";
    public static final String RMB = "¥";
    public static final String ERROR_LOG="zjjfpos_errorlog.txt";

    /**获取外卖订单列表*/
    public static final String GET_TAKE_OUT_ORDER_LIST = "api/takeout/order/list";
    /**获取外卖订单详情*/
    public static final String GET_TAKE_OUT_ORDER_DETAIL = "api/takeout/order/detail?takeoutType=";
    /**外卖订单处理*/
    public static final String DISPOSE_TAKE_OUT_ORDER = "api/takeout/order/dispose";
    /**获取便利店外卖设置*/
    public static final String GET_TAKEOUT_STORE_SET_LIST = "api/system/takeout/list";
    /**获取外卖平台绑定URL*/
    public static final String GET_TAKEOUT_STORE_SET_BIND = "api/system/takeout/bind/";
    /**获取外卖平台解绑定URL*/
    public static final String GET_TAKEOUT_STORE_SET_UNBIND = "api/system/takeout/unbind";
    /**检测外卖绑定或解绑结果*/
    public static final String GET_TAKEOUT_STORE_SET_STATUS = "api/system/takeout/status";
    /**设置自动接单*/
    public static final String GET_TAKEOUT_STORE_SET_AUTO_ACCEPT = "api/system/takeout/auto-accept";
    /**同步商品到外卖平台*/
    public static final String SYNC_PRODUCT_TO_TAKEOUT = "api/takeout/product/sync";
    /**修改入库单结算状态*/
    public static final String UPDATE_PAY_STATUS = "api/inventory/stockin/updatepaystatus";



}
