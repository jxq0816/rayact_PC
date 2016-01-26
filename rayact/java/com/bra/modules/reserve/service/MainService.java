package com.bra.modules.reserve.service;

import com.bra.common.service.BaseService;
import com.bra.modules.reserve.entity.ReserveRole;
import com.bra.modules.reserve.event.main.MainControlerEvent;
import com.bra.modules.reserve.event.main.bean.MainBean;
import com.bra.modules.sys.utils.UserUtils;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by xiaobin on 16/1/25.
 */
@Service
public class MainService extends BaseService {

    @Autowired
    private ReserveRoleService reserveRoleService;

    /**
     * 过滤数据
     *
     * @return
     */
    public Map<String, Object> controle() {
        Map<String, Object> map = Maps.newConcurrentMap();

        ReserveRole reserveRole = new ReserveRole();
        reserveRole.setUser(UserUtils.getUser());
        List<String> venueIds = reserveRoleService.findVenueIdsByRole(reserveRole);
        map.put("venueIds", venueIds);
        MainBean mainBean = new MainBean();
        mainBean.setMap(map);
        MainControlerEvent mainControlerEvent = new MainControlerEvent(mainBean);
        applicationContext.publishEvent(mainControlerEvent);
        return mainBean.getMap();
    }
}
