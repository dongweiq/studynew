package com.dongweiq.android_mvp_demo.activity;

import com.dongweiq.android_mvp_demo.bean.InfoBean;

/**
 */
public interface IInfoView {
    void setInfo(InfoBean info);
    InfoBean getInfo();
}
