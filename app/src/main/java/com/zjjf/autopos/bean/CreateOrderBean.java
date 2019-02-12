package com.zjjf.autopos.bean;

import java.util.List;

/**
 * Created by Yekai on 2017/6/22.
 */

public class CreateOrderBean {

    /**
     * "device_info": "设备号",
     * "spbill_create_ip": "终端IP",
     * "auth_code": "授权码",
     * "total_fee": "订单金额",
     * "subject": "订单标题",
     * "body": "订单详情",
     * "payType": "支付类型(1：微信；2：支付宝)",
     * "payWay": "支付方式（1：条码；2：二维码）",
     * "cardDiscountAmount": "会员卡折扣金额",
     * "cardDiscount": "会员卡折扣",
     * "cardPreferentialAmount": "积分优惠金额",
     * "vipcardCode": "会员卡号"
     */
    private String device_info;
    private String spbill_create_ip;
    private String auth_code;
    private double total_fee;
    private String subject;
    private String body;
    private String orderNo;
    private int payType;
    private int payWay;
    private double orderAmount;//应付金额
    private double actualPayAmount;//实付金额
    private double goodsAmount;//订单金额
    private double rebateAmount;//优惠合计
    private double refundAmount;//找零金额
    private String discountDesc;//优惠活动说明
    private String promotionIds;//订单关联活动ID
    private String token;
    private String vipcardCode;
    private String cardDiscountAmount;
    private String cardDiscount;
    private String cardPreferentialAmount;

    public String getCardDiscountAmount() {
        return cardDiscountAmount;
    }

    public void setCardDiscountAmount(String cardDiscountAmount) {
        this.cardDiscountAmount = cardDiscountAmount;
    }

    public String getCardDiscount() {
        return cardDiscount;
    }

    public void setCardDiscount(String cardDiscount) {
        this.cardDiscount = cardDiscount;
    }

    public String getCardPreferentialAmount() {
        return cardPreferentialAmount;
    }

    public void setCardPreferentialAmount(String cardPreferentialAmount) {
        this.cardPreferentialAmount = cardPreferentialAmount;
    }

    public String getVipcardCode() {
        return vipcardCode;
    }

    public void setVipcardCode(String vipcardCode) {
        this.vipcardCode = vipcardCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getAuth_code() {
        return auth_code;
    }

    public void setAuth_code(String auth_code) {
        this.auth_code = auth_code;
    }

    public double getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(double total_fee) {
        this.total_fee = total_fee;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getPayWay() {
        return payWay;
    }

    public void setPayWay(int payWay) {
        this.payWay = payWay;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public double getActualPayAmount() {
        return actualPayAmount;
    }

    public double getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(double goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public double getRebateAmount() {
        return rebateAmount;
    }

    public void setRebateAmount(double rebateAmount) {
        this.rebateAmount = rebateAmount;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public void setActualPayAmount(double actualPayAmount) {
        this.actualPayAmount = actualPayAmount;
    }

    public String getDiscountDesc() {
        return discountDesc;
    }

    public void setDiscountDesc(String discountDesc) {
        this.discountDesc = discountDesc;
    }

    public String getPromotionIds() {
        return promotionIds;
    }

    public void setPromotionIds(String promotionIds) {
        this.promotionIds = promotionIds;
    }

    private List<DetailListBean> detailList;

    public List<DetailListBean> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<DetailListBean> detailList) {
        this.detailList = detailList;
    }

    public static class DetailListBean {
        /**
         * productId : C7F13FF92C5B488BBAFAEB4B6B84ADA0
         * quantity : 22
         * price : 58
         * type : 0
         */

        private String productId;
        private int quantity;
        private double price;
        private int type;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
