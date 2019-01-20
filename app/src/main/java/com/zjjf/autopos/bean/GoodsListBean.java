package com.zjjf.autopos.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：Hao on 2017/6/2 17:25
 * 邮箱：shengxuan@izjjf.cn
 */

public class GoodsListBean {

    /**
     * totalPages : 1
     * last : true
     * totalElements : 1
     * numberOfElements : 1
     * size : 15
     * number : 0
     * first : true
     */

    private int totalPages;
    private boolean last;
    private int totalElements;
    private int numberOfElements;
    private int size;
    private int number;
    private boolean first;
    private List<ContentBean> content;
    private List<SortBean> sort;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public List<SortBean> getSort() {
        return sort;
    }

    public void setSort(List<SortBean> sort) {
        this.sort = sort;
    }

    public static class ContentBean implements Serializable{
        /**
         * id : C7F13FF92C5B488BBAFAEB4B6B84ADA0
         * createTime : 2017-05-24 15:06:45
         * updateTime : 2017-05-24 15:06:49
         * deleteStatus : 0
         * name : 大碗面
         * no : 999999
         * spec : 1x23
         * barcode : 88888
         * pkg : 桶
         * pkgId : 6958
         * brand : 统一
         * picUrl : /pic/dfsfs
         * type : 0//无码
         * secondCategory : {"id":"464","createTime":"2017-05-27 15:20:22","updateTime":"2017-05-27 15:20:22","deleteStatus":0,"name":"方便面","status":1,"parentId":"421","subCategories":null}
         * products : [{"id":"C7F13FF92C5B488BBAFAEB4B6B84ADA0","createTime":"2017-05-24 15:09:04","updateTime":"2017-05-24 15:09:07","deleteStatus":0,"purchasePrice":7,"sellingPrice":8,"status":1,"inventory":20,"safetyInventoryDays":8,"expirationDate":"2017-05-30 07:09:31"}]
         * syncId :
         * syncToEleme:true
         * syncToMeituan:true
         */

        private String mId;
        private String createTime;
        private String updateTime;
        private int deleteStatus;
        private String name;
        private String no;
        private String spec;
        private String barcode;
        private String pkg;
        private String pkgId;
        private String brand;
        private String picUrl;
        private String productionDate;
        private int type;
        private int expirationDays;
        private SecondCategoryBean secondCategory;
        private String syncId;
        private List<ProductsBean> products;

        private boolean isPurchaseChooes;
        private boolean isRetailChooes;
        private boolean isStockChooes;
        private boolean isDateChoose;
        private boolean isChoose;

        private int scrapNum;
        private String reason;
        private boolean isScrapChoose;
        private boolean isSelect;
        private boolean syncToEleme;
        private boolean syncToMeituan;

        public boolean isSyncToEleme() {
            return syncToEleme;
        }

        public void setSyncToEleme(boolean syncToEleme) {
            this.syncToEleme = syncToEleme;
        }

        public boolean isSyncToMeituan() {
            return syncToMeituan;
        }

        public void setSyncToMeituan(boolean syncToMeituan) {
            this.syncToMeituan = syncToMeituan;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public boolean isScrapChoose() {
            return isScrapChoose;
        }

        public void setScrapChoose(boolean scrapChoose) {
            isScrapChoose = scrapChoose;
        }

        public String getmId() {
            return mId;
        }

        public void setmId(String mId) {
            this.mId = mId;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public String getPkg() {
            return pkg;
        }

        public void setPkg(String pkg) {
            this.pkg = pkg;
        }

        public String getPkgId() {
            return pkgId;
        }

        public void setPkgId(String pkgId) {
            this.pkgId = pkgId;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getExpirationDays() {
            return expirationDays;
        }

        public void setExpirationDays(int expirationDays) {
            this.expirationDays = expirationDays;
        }

        public SecondCategoryBean getSecondCategory() {
            return secondCategory;
        }

        public void setSecondCategory(SecondCategoryBean secondCategory) {
            this.secondCategory = secondCategory;
        }

        public String getSyncId() {
            return syncId;
        }

        public void setSyncId(String syncId) {
            this.syncId = syncId;
        }

        public List<ProductsBean> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsBean> products) {
            this.products = products;
        }

        public boolean isPurchaseChooes() {
            return isPurchaseChooes;
        }

        public void setPurchaseChooes(boolean purchaseChooes) {
            isPurchaseChooes = purchaseChooes;
        }

        public boolean isRetailChooes() {
            return isRetailChooes;
        }

        public void setRetailChooes(boolean retailChooes) {
            isRetailChooes = retailChooes;
        }

        public boolean isStockChooes() {
            return isStockChooes;
        }

        public void setStockChooes(boolean stockChooes) {
            isStockChooes = stockChooes;
        }

        public boolean isDateChoose() {
            return isDateChoose;
        }

        public void setDateChoose(boolean dateChoose) {
            isDateChoose = dateChoose;
        }

        public boolean isChoose() {return isChoose;}

        public void setChoose(boolean choose) {isChoose = choose;}

        public String getProductionDate() {return productionDate;}

        public void setProductionDate(String productionDate) {this.productionDate = productionDate;}

        public int getScrapNum() {
            return scrapNum;
        }

        public void setScrapNum(int scrapNum) {
            this.scrapNum = scrapNum;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public static class SecondCategoryBean implements Serializable{
            /**
             * id : 464
             * createTime : 2017-05-27 15:20:22
             * updateTime : 2017-05-27 15:20:22
             * deleteStatus : 0
             * name : 方便面
             * status : 1
             * parentId : 421
             * subCategories : null
             */

            private String mId;
            private String createTime;
            private String updateTime;
            private int deleteStatus;
            private String name;
            private int status;
            private String parentId;

            public String getmId() {
                return mId;
            }

            public void setmId(String mId) {
                this.mId = mId;
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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }
        }

        public static class ProductsBean implements Serializable{
            /**
             * id : C7F13FF92C5B488BBAFAEB4B6B84ADA0
             * createTime : 2017-05-24 15:09:04
             * updateTime : 2017-05-24 15:09:07
             * deleteStatus : 0
             * purchasePrice : 7
             * sellingPrice : 8
             * status : 1
             * inventory : 20
             * safetyInventoryDays : 8
             * expirationDate : 2017-05-30 07:09:31
             */

            private String mId;
            private String createTime;
            private String updateTime;
            private int deleteStatus;
            private double purchasePrice;
            private double sellingPrice;
            private int status;
            private int inventory;
            private int safetyInventoryDays;
            private String expirationDate;
            private String productionDate;//生产日期
            private String purchasePriceAsString;//设置采购价时使用
            private String sellingPriceAsString;//设置销售价时使用
            private String statusDate;

            public String getStatusDate() {
                return statusDate;
            }

            public void setStatusDate(String statusDate) {
                this.statusDate = statusDate;
            }

            public String getmId() {
                return mId;
            }

            public void setmId(String mId) {
                this.mId = mId;
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

            public String getExpirationDate() {
                return expirationDate;
            }

            public void setExpirationDate(String expirationDate) {
                this.expirationDate = expirationDate;
            }

            public String getProductionDate() {
                return productionDate;
            }

            public void setProductionDate(String productionDate) {
                this.productionDate = productionDate;
            }

            public String getPurchasePriceAsString() {
                return purchasePriceAsString;
            }

            public void setPurchasePriceAsString(String purchasePriceAsString) {
                this.purchasePriceAsString = purchasePriceAsString;
            }

            public String getSellingPriceAsString() {
                return sellingPriceAsString;
            }

            public void setSellingPriceAsString(String sellingPriceAsString) {
                this.sellingPriceAsString = sellingPriceAsString;
            }
        }
    }

    public static class SortBean {
        /**
         * direction : DESC
         * property : createTime
         * ignoreCase : false
         * nullHandling : NATIVE
         * descending : true
         * ascending : false
         */

        private String direction;
        private String property;
        private boolean ignoreCase;
        private String nullHandling;
        private boolean descending;
        private boolean ascending;

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public boolean isIgnoreCase() {
            return ignoreCase;
        }

        public void setIgnoreCase(boolean ignoreCase) {
            this.ignoreCase = ignoreCase;
        }

        public String getNullHandling() {
            return nullHandling;
        }

        public void setNullHandling(String nullHandling) {
            this.nullHandling = nullHandling;
        }

        public boolean isDescending() {
            return descending;
        }

        public void setDescending(boolean descending) {
            this.descending = descending;
        }

        public boolean isAscending() {
            return ascending;
        }

        public void setAscending(boolean ascending) {
            this.ascending = ascending;
        }
    }
}
