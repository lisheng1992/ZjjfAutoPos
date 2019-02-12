package com.zjjf.autopos.bean;

import java.util.List;

/**
 * 作者：Hao on 2017/7/10 16:07
 * 邮箱：shengxuan@izjjf.cn
 */

public class PromotionBean {

    /**
     * quantity : 0
     * productId :
     * rebateAmount : 1
     * actId : 32790D8CF9E44A63B817D31E71FD6575
     * actName : 满减
     * productList : [{"id":"D404CFE11A2A4CBB9D28D45EC49F5656","createTime":"2017-08-09 11:57:28","updateTime":"2017-08-09 11:57:28","deleteStatus":0,"createTimeBegin":null,"createTimeEnd":null,"updateTimeBegin":null,"updateTimeEnd":null,"purchasePrice":1,"sellingPrice":1,"status":1,"inventory":1,"safetyInventoryDays":1,"quantity":1000}]
     * productName :
     * desc : 满减:满123元，减1.00元
     */

    private int quantity;
    private String productId;
    private double rebateAmount;
    private String actId;
    private String actName;
    private String productName;
    private String desc;
    private List<ProductListBean> productList;
    private boolean isChoose;
    private boolean noChoice;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getRebateAmount() {
        return rebateAmount;
    }

    public void setRebateAmount(double rebateAmount) {
        this.rebateAmount = rebateAmount;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public boolean isNoChoice() {
        return noChoice;
    }

    public void setNoChoice(boolean noChoice) {
        this.noChoice = noChoice;
    }

    public List<ProductListBean> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductListBean> productList) {
        this.productList = productList;
    }

    public static class ProductListBean {
        /**
         * id : D404CFE11A2A4CBB9D28D45EC49F5656
         * createTime : 2017-08-09 11:57:28
         * updateTime : 2017-08-09 11:57:28
         * deleteStatus : 0
         * createTimeBegin : null
         * createTimeEnd : null
         * updateTimeBegin : null
         * updateTimeEnd : null
         * purchasePrice : 1
         * sellingPrice : 1
         * status : 1
         * inventory : 1
         * safetyInventoryDays : 1
         * quantity : 1000
         */

        private String mId;
        private String createTime;
        private String updateTime;
        private int deleteStatus;
        private String createTimeBegin;
        private String createTimeEnd;
        private String updateTimeBegin;
        private String updateTimeEnd;
        private double purchasePrice;
        private double sellingPrice;
        private int status;
        private int inventory;
        private int safetyInventoryDays;
        private int quantity;

        public String getId() {
            return mId;
        }

        public void setId(String id) {
            mId = id;
        }

        public String getCreateTimeBegin() {
            return createTimeBegin;
        }

        public void setCreateTimeBegin(String createTimeBegin) {
            this.createTimeBegin = createTimeBegin;
        }

        public String getCreateTimeEnd() {
            return createTimeEnd;
        }

        public void setCreateTimeEnd(String createTimeEnd) {
            this.createTimeEnd = createTimeEnd;
        }

        public String getUpdateTimeBegin() {
            return updateTimeBegin;
        }

        public void setUpdateTimeBegin(String updateTimeBegin) {
            this.updateTimeBegin = updateTimeBegin;
        }

        public String getUpdateTimeEnd() {
            return updateTimeEnd;
        }

        public void setUpdateTimeEnd(String updateTimeEnd) {
            this.updateTimeEnd = updateTimeEnd;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getDeleteStatus() {
            return deleteStatus;
        }

        public void setDeleteStatus(int deleteStatus) {
            this.deleteStatus = deleteStatus;
        }

        public double getPurchasePrice() {
            return purchasePrice;
        }

        public void setPurchasePrice(double purchasePrice) {
            this.purchasePrice = purchasePrice;
        }

        public double getSellingPrice() {
            return sellingPrice;
        }

        public void setSellingPrice(double sellingPrice) {
            this.sellingPrice = sellingPrice;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getInventory() {
            return inventory;
        }

        public void setInventory(int inventory) {
            this.inventory = inventory;
        }

        public int getSafetyInventoryDays() {
            return safetyInventoryDays;
        }

        public void setSafetyInventoryDays(int safetyInventoryDays) {
            this.safetyInventoryDays = safetyInventoryDays;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
