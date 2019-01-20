package com.zjjf.autopos.bean;


import org.litepal.crud.DataSupport;

import java.util.List;

/**
 *  登录成功返回账户信息
 * Created by Yekai on 2017/6/1.
 */

public class UserInfo extends DataSupport {


    /**
     * id : C7F13FF92C5B488BBAFAEB4B6B84ADA0
     * createTime : 2017-01-09 17:26:41
     * updateTime : 2017-01-09 17:27:11
     * deleteStatus : 0
     * username : admin
     * nickname : Admin
     * realname : null
     * password : null
     * sex : 0
     * birthday : 2017-01-09 17:26:39
     * telephone : 18475170255
     * email : admin@izjjf.com
     * address : 深圳
     * locked : 0
     * description : 超级管理员
     * isAdmin : 1
     * syncId : null
     * sessionId : 3d251b94-7004-4c39-abed-17d56fc27d4e
     * remoteAddress : 192.168.1.175
     * storeId : C7F13FF92C5B488BBAFAEB4B6B84ADA0
     */

    private String mId;
    private String createTime;
    private String updateTime;
    private int deleteStatus;
    private String username;
    private String nickname;
    private String realname;
    private String password;
    private int sex;
    private String birthday;
    private String telephone;
    private String email;
    private String address;
    private int locked;
    private String description;
    private int isAdmin;
    private String syncId;
    private String sessionId;
    private String remoteAddress;
    private String storeId;
    private String storeName;
    private List<ResourcesBean> resources;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getSyncId() {
        return syncId;
    }

    public void setSyncId(String syncId) {
        this.syncId = syncId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public List<ResourcesBean> getResources() {
        return resources;
    }

    public void setResources(List<ResourcesBean> resources) {
        this.resources = resources;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "mId='" + mId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", deleteStatus=" + deleteStatus +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", realname='" + realname + '\'' +
                ", password='" + password + '\'' +
                ", sex=" + sex +
                ", birthday='" + birthday + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", locked=" + locked +
                ", description='" + description + '\'' +
                ", isAdmin=" + isAdmin +
                ", syncId='" + syncId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", remoteAddress='" + remoteAddress + '\'' +
                ", storeId='" + storeId + '\'' +
                ", storeName='" + storeName + '\'' +
                '}';
    }
}
