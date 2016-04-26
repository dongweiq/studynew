package com.dongweiq.android_mvp_demo.model;

import com.dongweiq.android_mvp_demo.bean.InfoBean;

/**
 */
public class InfoModelImpl implements IInfoModel {
    private InfoBean infoBean = new InfoBean();

    @Override
    public InfoBean getInfo() {
        return infoBean;
    }

    @Override
    public void setInfo(InfoBean info) {
        infoBean = info;
    }
}
