package com.bra.modules.reserve.web;

import com.bra.common.utils.Collections3;
import com.bra.common.web.annotation.Token;
import com.bra.modules.reserve.entity.ReserveField;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.service.ReserveFieldService;
import com.bra.modules.reserve.service.ReserveVenueService;
import com.bra.modules.reserve.utils.TimeUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.service.ReserveFieldPriceSetService;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

/**
 * 场地价格Controller
 *
 * @author 肖斌
 * @version 2016-01-06
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/price")
public class ReserveFieldPriceSetController extends BaseController {

    //场馆
    @Autowired
    private ReserveVenueService reserveVenueService;
    //场地
    @Autowired
    private ReserveFieldService reserveFieldService;


    //场地价格
    @Autowired
    private ReserveFieldPriceSetService reserveFieldPriceSetService;

    /**
     * 场地价格设定
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "set")
    @Token(remove = true)
    public String set(Model model, HttpServletRequest request) throws ParseException {
        loadDefaultData(model);
        model.addAttribute("weekIndex", NumberUtils.toInt(request.getParameter("weekIndex"), 0));
        return "modules/reserve/reserveFieldPriceSetList";
    }

    //初始化默认数据
    private void loadDefaultData(Model model) throws ParseException {
        //加载所有场馆
        List<ReserveVenue> venueList = reserveVenueService.findList(new ReserveVenue());
        model.addAttribute("venueList", venueList);
        if (!Collections3.isEmpty(venueList)) {
            ReserveField reserveField = new ReserveField();
            reserveField.setReserveVenue(venueList.get(0));
            model.addAttribute("venueId", venueList.get(0).getId());
            //场地
            List<ReserveField> fieldList = reserveFieldService.findList(reserveField);
            model.addAttribute("fieldList", fieldList);
        }
        model.addAttribute("weekDays", TimeUtils.WEEK_DAYS);
        //获取营业时间
        List<String> times = TimeUtils.getTimeSpacList("09:00:00", "23:00:00", 60);

        model.addAttribute("times", times);
    }
}