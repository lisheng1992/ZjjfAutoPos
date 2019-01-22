package com.zjjf.autopos.bean;

import java.io.Serializable;

/**
 * Created by yekai on 17-5-10.
 */

public class GoodsBean implements Serializable{

    /**
     * 商品id
     */
    private String goodsId;

    /**
     * 购买数量
     */
    private int goodsNum;
    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品编号
     */
    private String no;

    /**
     * 商品规格
     */
    private String spec;

    /**
     * 商品条码
     */
    private String barcode;

    /**
     * 包装名称/单位
     */
    private String pkg;


    /**
     * 商品品牌
     */
    private String brand;

    /**
     *   商品类型
     */
    private int type;

    /**
     * 一级分类id
     */
    private String firstClassifyId;
    /**
     * 二级分类id
     */
    private String secondClassifyId;
    /**
     * 采购价
     */
    private double purchasePrice;
    /**
     * 零售价
     */
    private double sellingPrice;
    /**
     * 库存
     */
    private int inventory;

    /**
     * 生产日期
     */
    private String productDate;

    /**
     * 保质期
     */
    private String expirationDate;

    /**
     * 总价
     */
    private double totalPrice;

    public GoodsBean(String goodsId, int goodsNum, String name, String no, String spec,
                     String barcode, String pkg, String brand, int type,
                     String firstClassifyId, String secondClassifyId, double purchasePrice,
                     double sellingPrice, int inventory, double totalPrice) {
        this.goodsId = goodsId;
        this.goodsNum = goodsNum;
        this.name = name;
        this.no = no;
        this.spec = spec;
        this.barcode = barcode;
        this.pkg = pkg;
        this.brand = brand;
        this.type = type;
        this.firstClassifyId = firstClassifyId;
        this.secondClassifyId = secondClassifyId;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.inventory = inventory;
        this.totalPrice = totalPrice;
    }

    public GoodsBean(){}

    public GoodsBean(String name) {
        this.name = name;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getFirstClassifyId() {
        return firstClassifyId;
    }

    public void setFirstClassifyId(String firstClassifyId) {
        this.firstClassifyId = firstClassifyId;
    }

    public String getSecondClassifyId() {
        return secondClassifyId;
    }

    public void setSecondClassifyId(String secondClassifyId) {
        this.secondClassifyId = secondClassifyId;
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

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "GoodsBean{" +
                "goodsId='" + goodsId + '\'' +
                ", goodsNum=" + goodsNum +
                ", name='" + name + '\'' +
                ", no='" + no + '\'' +
                ", spec='" + spec + '\'' +
                ", barcode='" + barcode + '\'' +
                ", pkg='" + pkg + '\'' +
                ", brand='" + brand + '\'' +
                ", type=" + type +
                ", firstClassifyId='" + firstClassifyId + '\'' +
                ", secondClassifyId='" + secondClassifyId + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", sellingPrice=" + sellingPrice +
                ", inventory=" + inventory +
                ", productDate='" + productDate + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
