package com.dongweiq.android_mvp_demo.presenter;

import com.dongweiq.android_mvp_demo.activity.IInfoView;
import com.dongweiq.android_mvp_demo.bean.InfoBean;
import com.dongweiq.android_mvp_demo.model.IInfoModel;
import com.dongweiq.android_mvp_demo.model.InfoModelImpl;

/**
 */
public class Presenter {
    private IInfoModel infoModel;
    private IInfoView infoView;

    public Presenter(IInfoView infoView) {
        this.infoView = infoView;

        infoModel = new InfoModelImpl();
    }

    public void saveInfo(InfoBean bean) {
        infoModel.setInfo(bean);
    }

    public void getInfo() {
        infoView.setInfo(infoModel.getInfo());
    }
}
