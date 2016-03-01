package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.common.web.annotation.Token;
import com.bra.modules.mechanism.web.bean.AttMainForm;
import com.bra.modules.reserve.entity.ReserveProject;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.entity.form.ReserveVenueIncomeIntervalReport;
import com.bra.modules.reserve.entity.form.ReserveVenueProjectIntervalReport;
import com.bra.modules.reserve.entity.form.ReserveVenueTotalIntervalReport;
import com.bra.modules.reserve.service.MainService;
import com.bra.modules.reserve.service.ReserveProjectService;
import com.bra.modules.reserve.service.ReserveVenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 监控Controller
 *
 * @author jiangxingqi
 * @version 2015-12-29
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveListener")
public class ReserveListenerController extends BaseController {

    @Autowired
    private MainService mainService;

    @RequestMapping(value = "report")
    public String report(Model model) {
        Map<String, Object> data = mainService.controle();
        model.addAllAttributes(data);
        return "reserve/report/dataListenerReport";
    }
}