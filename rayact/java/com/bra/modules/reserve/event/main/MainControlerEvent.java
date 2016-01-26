package com.bra.modules.reserve.event.main;

import com.bra.modules.reserve.event.main.bean.MainBean;

/**
 * Created by xiaobin on 16/1/25.
 */
public class MainControlerEvent {

    private MainBean mainBean;

    public MainControlerEvent(MainBean mainBean){
        this.mainBean = mainBean;
    }

    public MainBean getMainBean() {
        return mainBean;
    }

    public void setMainBean(MainBean mainBean) {
        this.mainBean = mainBean;
    }
}
