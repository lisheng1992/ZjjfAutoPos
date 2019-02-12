package com.zjjf.autopos.bean;

/**
 * Created by liwinner on 2019/2/12.
 */

public class PayCallbackBean {
//    "id": null,
//            "createTime": null,
//            "updateTime": null,
//            "deleteStatus": 0,
//            "createTimeBegin": null,
//            "createTimeEnd": null,
//            "updateTimeBegin": null,
//            "updateTimeEnd": null,
//            "storeId": null,
//            "storeName": null,
//            "orderNo": "123456",
//            "tradeNo": null,
//            "orderAmount": null,
//            "goodsAmount": null,
//            "rebateAmount": null,
//            "actualPayAmount": null,
//            "refundAmount": null,
//            "cost": null,
//            "profit": null,
//            "payTypeCode": null,
//            "payTypeName": null,
//            "tradeType": null,
//            "remark": null,
//            "discountDesc": null,
//            "quantity": 0,
//            "createBy": null,
//            "updateBy": null,
//            "isTransfer": 0,
//            "updateByName": null,
//            "wecharopenId": null,
//            "orderFrom": null,
//            "status": 0,
//            "payStatus": 0,
//            "payReturnMsg": null,
//            "vipcardCode": null,
//            "cardChangeStatus": "0",
//            "cardRule": null,
//            "integral": null,
//            "isCardPreferential": "0",
//            "cardPreferentialAmount": null,
//            "cardConvertQuantity": null,
//            "cardDiscount": null,
//            "cardDiscountAmount": null,
//            "isCardChange": "0",
//            "posThridPlatform": null,
//            "detailList": null,
//            "device_info": null,
//            "spbill_create_ip": null,
//            "auth_code": null,
//            "subject": null,
//            "body": null,
//            "payType": null,
//            "payWay": null,
//            "notify_url": null,
//            "singleActList": null,
//            "manyActList": null,
//            "promotionIds": null,
//            "fromString": null,
//            "token": null,
//            "orderMealList": null,
//            "address": null,
//            "posThridPlatformString": null,
//            "refund": 1,
//            "porderNo": null

    //0待支付，1支付成功，2支付异常，3支付退款，9支付中
    private int payStatus;

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }
}
