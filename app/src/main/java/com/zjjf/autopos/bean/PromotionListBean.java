package com.zjjf.autopos.bean;

import java.util.List;

/**
 * 作者：Hao on 2017/7/10 16:14
 * 邮箱：shengxuan@izjjf.cn
 */

public class PromotionListBean {
    private List<PromotionBean> singleActList;
    private List<PromotionBean> manyActList;

    public List<PromotionBean> getSingleActList() {
        return singleActList;
    }

    public void setSingleActList(List<PromotionBean> singleActList) {
        this.singleActList = singleActList;
    }

    public List<PromotionBean> getManyActList() {
        return manyActList;
    }

    public void setManyActList(List<PromotionBean> manyActList) {
        this.manyActList = manyActList;
    }
}
