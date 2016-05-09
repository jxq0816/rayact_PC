package com.bra.modules.reserve.web;

import com.alibaba.fastjson.JSON;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.form.FieldPrice;
import com.bra.modules.reserve.entity.form.TimePrice;
import com.bra.modules.reserve.service.ReserveFieldPriceService;
import com.bra.modules.reserve.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 场地预定管理
 * Created by xiaobin on 16/1/5.
 */
@Controller
@RequestMapping(value = "${adminPath}/app/reserve/field")
public class ReserveAppController extends BaseController {

    @Autowired
    private ReserveFieldPriceService reserveFieldPriceService;

    //场地状态界面
    @RequestMapping(value = "main")
    @ResponseBody
    public String main(Date consDate, String venueId) throws ParseException {
        String rtn = null;
        if (consDate == null) {
            consDate = new Date();
        }
        if (StringUtils.isNoneEmpty(venueId)) {
            //场地价格
            List<String> times = new ArrayList<>();
            times.addAll(TimeUtils.getTimeSpacListValue("06:00:00", "00:30:00", 30));
            List<FieldPrice> venueFieldPriceList = reserveFieldPriceService.findByDate(venueId, "1", consDate, times);
            for (FieldPrice i : venueFieldPriceList) {
                i.setHaveFullCourt(null);
                i.setHaveHalfCourt(null);
                for (TimePrice j : i.getTimePriceList()) {
                    j.setConsItem(null);
                    j.setConsType(null);
                    j.setUserName(null);
                }
                FieldPrice full = i.getFieldPriceFull();
                if (full != null) {
                    full.setHaveFullCourt(null);
                    full.setHaveHalfCourt(null);
                    for (TimePrice k : full.getTimePriceList()) {
                        k.setConsItem(null);
                        k.setPrice(null);
                        k.setConsType(null);
                        k.setUserName(null);
                    }
                }

                FieldPrice left = i.getFieldPriceLeft();
                if (left != null) {
                    left.setHaveFullCourt(null);
                    left.setHaveHalfCourt(null);
                    for (TimePrice k : left.getTimePriceList()) {
                        k.setConsItem(null);
                        k.setPrice(null);
                        k.setConsType(null);
                        k.setUserName(null);
                    }
                }
                FieldPrice right = i.getFieldPriceRight();
                if (right != null) {
                    right.setHaveFullCourt(null);
                    right.setHaveHalfCourt(null);
                    for (TimePrice k : right.getTimePriceList()) {
                        k.setConsItem(null);
                        k.setPrice(null);
                        k.setConsType(null);
                        k.setUserName(null);
                    }
                }
            }
            rtn = JSON.toJSONString(venueFieldPriceList);
        }
        return rtn;
    }
}
