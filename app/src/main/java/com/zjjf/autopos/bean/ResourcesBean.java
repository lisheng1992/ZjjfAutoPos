package com.zjjf.autopos.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by Yekai on 2017/6/12.
 */

public class ResourcesBean extends DataSupport{


    /**
     * id : C7F13FF92C5B488BBAFAEB4B6B84ADA1
     * createTime : 2017-01-10 13:56:51
     * updateTime : 2017-01-10 13:59:01
     * deleteStatus : 0
     * name : 商品上架
     * permission : product:onshelf
     * type : 1
     * url : /api/product/update
     * level : 2
     * sort : 1
     * icon : null
     * status : 1
     * description : 商品上架
     */

    private String mId;
    private String createTime;
    private String updateTime;
    private int deleteStatus;
    private String name;
    private String permission;
    private int type;
    private String url;
    private int level;
    private int sort;
    private String icon;
    private int status;
    private String description;
    private int needCheck;
    private String username;

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

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNeedCheck() {
        return needCheck;
    }

    public void setNeedCheck(int needCheck) {
        this.needCheck = needCheck;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
