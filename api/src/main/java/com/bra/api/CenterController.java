package com.bra.api;

import com.bra.common.utils.CacheUtils;
import com.bra.common.utils.JsonUtils;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.Servlets;
import com.bra.plugin.migration.entity.MobileHead;
import com.bra.plugin.migration.service.TransmitsCommanderService;
import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by xiaobin on 16/2/16.
 */
@RestController
public class CenterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CenterController.class);

    @Autowired
    private TransmitsCommanderService transmitsCommanderService;


    @RequestMapping(value = "/center")
    @ResponseBody
    public String center(HttpServletRequest request) {
        String contentType = request.getContentType();
        String method = request.getMethod();
        boolean hasMultiPart = contentType != null && contentType.toLowerCase().startsWith("multipart/");
        Map<String, Object> params;
        if (hasMultiPart) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            params = Servlets.getParams(multipartRequest);
            params.putAll(fileMap);
        } else {
            params = Servlets.getParams(request);
        }
        String remoteAddr = request.getRemoteAddr();
        LOGGER.info("inIp=" + remoteAddr + ",method=" + method + ",inLog" + getInDataLog(params));

        String userId = MapUtils.getString(params, "userId");
        String equipment = MapUtils.getString(params, "equipment");
        String requestType = MapUtils.getString(params, "requestType");
        String token = MapUtils.getString(params, "token");

        MobileHead head = new MobileHead(userId, equipment, requestType, token);
        Map<String, Object> json = Maps.newConcurrentMap();
        String outLog = "";
        if (StringUtils.isBlank(head.getRequestType())) {
            json.put("status_code", "501");
            json.put("message", "请求类型不能为空");
            outLog = JsonUtils.writeObjectToJson(json);
        }
        if (StringUtils.isBlank(head.getEquipment())) {
            json.put("status_code", "502");
            json.put("message", "设备标识不能为空");
            outLog = JsonUtils.writeObjectToJson(json);
        }
        if (StringUtils.isNotBlank(head.getRequestType()) && StringUtils.isNotBlank(head.getEquipment())) {
            outLog = transmitsCommanderService.executeTodo(head, params);
        }
        Map<String, Object> outMap = JsonUtils.readObjectByJson(outLog, Map.class);
        if (StringUtils.isNotBlank(token)) {
            if (CacheUtils.get("userToken", token) != null) {
                outMap.put("token", CacheUtils.get("userToken", token));
            }
        }
        LOGGER.info("outLog=" + outLog);
        return JsonUtils.writeObjectToJson(outMap);
    }

    private String getInDataLog(Map<String, Object> map) {
        StringBuffer log = new StringBuffer("{");
        for (String key : map.keySet()) {
            log.append(key).append(":").append(map.get(key)).append(",");
        }
        log.append("}");
        return log.toString();
    }
}
