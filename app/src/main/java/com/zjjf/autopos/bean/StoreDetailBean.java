package com.zjjf.autopos.bean;

import java.util.List;

/**
 * Created by Yekai on 2017/6/6.
 */

public class StoreDetailBean {


    /**
     * id : C7F13FF92C5B488BBAFAEB4B6B84ADA0
     * createTime : 2017-05-24 07:01:12
     * updateTime : 2017-05-24 15:01:16
     * deleteStatus : 0
     * name : 转角便利
     * no : A0001
     * province : {"id":6,"name":"广东省","code":"粤","level":2,"parentId":1}
     * city : {"id":77,"name":"深圳","code":"B","level":3,"parentId":6}
     * area : {"id":707,"name":"南山区","code":"01","level":4,"parentId":77}
     * address : 科兴25号
     * telephone : 18475170266
     * description : 科兴店
     * status : 1
     * iconPic : /pic/icon.jpg
     * openTime : 6:00
     * closeTime : 22:00
     * bankName : 农业银行
     * bankCard : 622589254578
     * wechatAccount : jfun@zjjf.cn
     * alipayAccount : jfun@zjjf.cn
     * receiptTagline : 欢迎光临
     * receiptQrcode : /pic/qr.jpg
     * printNum : 1
     * licenseNo : 8654455
     * pricetagWidth : 33
     * pricetagHeight : 22
     * walletBalance : 900.25
     * advertisements : []
     * pricetagFields : []
     * syncId : null
     */

    private String mId;
    private String createTime;
    private String updateTime;
    private int deleteStatus;
    private String name;
    private String no;
    private ProvinceBean province;
    private CityBean city;
    private AreaBean area;
    private String address;
    private String telephone;
    private String description;
    private int status;
    private String iconPic;
    private String openTime;
    private String closeTime;
    private String bankName;
    private String bankCard;
    private String wechatAccount;
    private String alipayAccount;
    private String receiptTagline;
    private String receiptQrcode;
    private int printNum;
    private String licenseNo;
    private int pricetagWidth;
    private int pricetagHeight;
    private String walletBalance;
    private String syncId;
    private String adv1;
    private String adv2;
    private String adv3;
//    private List<AdPictureBean> advertisements;
    private List<FieldBean> pricetagFields;

    public String getMId() {
        return mId;
    }

    public void setMId(String mId) {
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

    public ProvinceBean getProvince() {
        return province;
    }

    public void setProvince(ProvinceBean province) {
        this.province = province;
    }

    public CityBean getCity() {
        return city;
    }

    public void setCity(CityBean city) {
        this.city = city;
    }

    public AreaBean getArea() {
        return area;
    }

    public void setArea(AreaBean area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIconPic() {
        return iconPic;
    }

    public void setIconPic(String iconPic) {
        this.iconPic = iconPic;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getWechatAccount() {
        return wechatAccount;
    }

    public void setWechatAccount(String wechatAccount) {
        this.wechatAccount = wechatAccount;
    }

    public String getAlipayAccount() {
        return alipayAccount;
    }

    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }

    public String getReceiptTagline() {
        return receiptTagline;
    }

    public void setReceiptTagline(String receiptTagline) {
        this.receiptTagline = receiptTagline;
    }

    public String getReceiptQrcode() {
        return receiptQrcode;
    }

    public void setReceiptQrcode(String receiptQrcode) {
        this.receiptQrcode = receiptQrcode;
    }

    public int getPrintNum() {
        return printNum;
    }

    public void setPrintNum(int printNum) {
        this.printNum = printNum;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public int getPricetagWidth() {
        return pricetagWidth;
    }

    public void setPricetagWidth(int pricetagWidth) {
        this.pricetagWidth = pricetagWidth;
    }

    public int getPricetagHeight() {
        return pricetagHeight;
    }

    public void setPricetagHeight(int pricetagHeight) {
        this.pricetagHeight = pricetagHeight;
    }

    public String getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(String walletBalance) {
        this.walletBalance = walletBalance;
    }

    public String getSyncId() {
        return syncId;
    }

    public void setSyncId(String syncId) {
        this.syncId = syncId;
    }

//    public List<AdPictureBean> getAdvertisements() {
//        return advertisements;
//    }
//
//    public void setAdvertisements(List<AdPictureBean> advertisements) {
//        this.advertisements = advertisements;
//    }


    public String getAdv1() {
        return adv1;
    }

    public void setAdv1(String adv1) {
        this.adv1 = adv1;
    }

    public String getAdv2() {
        return adv2;
    }

    public void setAdv2(String adv2) {
        this.adv2 = adv2;
    }

    public String getAdv3() {
        return adv3;
    }

    public void setAdv3(String adv3) {
        this.adv3 = adv3;
    }

    public List<FieldBean> getPricetagFields() {
        return pricetagFields;
    }

    public void setPricetagFields(List<FieldBean> pricetagFields) {
        this.pricetagFields = pricetagFields;
    }

    public static class ProvinceBean {
        /**
         * id : 6
         * name : 广东省
         * code : 粤
         * level : 2
         * parentId : 1
         */

        private int id;
        private String name;
        private String code;
        private int level;
        private int parentId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }
    }

    public static class CityBean {
        /**
         * id : 77
         * name : 深圳
         * code : B
         * level : 3
         * parentId : 6
         */

        private int id;
        private String name;
        private String code;
        private int level;
        private int parentId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }
    }

    public static class AreaBean {
        /**
         * id : 707
         * name : 南山区
         * code : 01
         * level : 4
         * parentId : 77
         */

        private int id;
        private String name;
        private String code;
        private int level;
        private int parentId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }
    }

    public static class FieldBean {
        /**
         * checked: 0,
         * createTime: "2017-06-08T05:40:39.989Z",
         * deleteStatus: 0,
         * description: "string",
         * fieldName: "string",
         * id: "string",
         * updateTime: "2017-06-08T05:40:39.989Z"
         */

        private int checked;
        private String createTime;
        private int deleteStatus;
        private String description;
        private String fieldName;
        private String mId;
        private String updateTime;
        private boolean isSelect;

        public int getChecked() {
            return checked;
        }

        public void setChecked(int checked) {
            this.checked = checked;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getDeleteStatus() {
            return deleteStatus;
        }

        public void setDeleteStatus(int deleteStatus) {
            this.deleteStatus = deleteStatus;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getMId() {
            return mId;
        }

        public void setMId(String mId) {
            this.mId = mId;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }
    }
}
