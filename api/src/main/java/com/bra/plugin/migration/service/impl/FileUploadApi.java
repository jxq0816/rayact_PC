package com.bra.plugin.migration.service.impl;

import com.bra.common.config.Global;
import com.bra.common.upload.FileModel;
import com.bra.common.upload.FileRepository;
import com.bra.common.utils.IdGen;
import com.bra.common.utils.JsonUtils;
import com.bra.common.utils.SpringContextHolder;
import com.bra.modules.mechanism.entity.AttMain;
import com.bra.modules.mechanism.service.AttMainService;
import com.bra.modules.reserve.entity.ReserveMember;
import com.bra.modules.reserve.service.MemberService;
import com.bra.plugin.migration.entity.MobileHead;
import com.bra.plugin.migration.entity.StatusField;
import com.bra.plugin.migration.service.TransmitsService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaobin on 16/2/18.
 */
public class FileUploadApi implements TransmitsService {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadApi.class);

    private AttMainService getAttMainService(){
        return SpringContextHolder.getBean("attMainService");
    }
    private FileRepository getFileRepository(){
        return SpringContextHolder.getBean("fileRepository");
    }
    private MemberService getMemberService(){return SpringContextHolder.getBean("memberService");}

    @Override
    public String executeTodo(MobileHead mobileHead, Map<String, Object> request) {
        Map<String, String> map = new HashMap<String, String>();
        logger.debug("...........................FileUploadApi begin.........................................");
        Object object = request.get("file");
        String modelId = MapUtils.getString(request, "uuid");
        String userToken = MapUtils.getString(request, "token");
        String modelName = "com.world.think.modules.sys.entity.User";//实体类路径
        String fdKey = MapUtils.getString(request, "tradeType");//业务类型（头像上传_userPhoto、身份证正面_IDFace、身份证反面_IDBack、营业执照_BL businessLicenceUrl、股东证明文件 _SHL stockholderLicenceUrl 、公司证明文件_CL companyLicenceUrl、个人名片/执业证书/执业许可证 _IDL identificationLicenceUrl）

        if(modelId ==null || userToken ==null || fdKey == null  ){
            return JsonUtils.writeObjectToJson(map.put(StatusField.STATUS_DEC,"888:参数为空，无法继续"));
        }
        //用户基本信息(数据库查询)
        ReserveMember user = getMemberService().get(modelId);
        if (user != null) {
            if (user.getMemberExtend().getToken().equals(userToken)) {
                if (object != null) {
                    MultipartFile multipartFile = (MultipartFile) object;
                    String token = IdGen.uuid();
                    FileModel fileModel = getFileRepository().storeByExt(multipartFile, IdGen.uuid());
                    fileModel.setToken(token);
                    AttMain attMain = new AttMain(fileModel);
                    attMain.setFdModelId(modelId);
                    attMain.setFdModelName(modelName);
                    attMain.setFdKey(fdKey);

                    //删除
                    getAttMainService().deleteAttMain(modelId,modelName,fdKey);
                    getAttMainService().saveAttMain(attMain);//入库
                    String filePath = Global.getConfig("system.url") + "mechanism/file/imageMobile/" + modelId + "/" + modelName +"/"+fdKey+ "?random="+ RandomUtils.nextInt(1, 100);
                    map.put("file_url", filePath);//filePath
                    map.put(StatusField.STATUS_CODE, StatusField.DO_SUCCESS);
                } else {
                    map.put(StatusField.STATUS_CODE, StatusField.DO_FAILED);
                }
            } else {
                map.put(StatusField.STATUS_CODE, StatusField.DO_FAILED);
                map.put(StatusField.DO_FAILED_DEC, "您已处于离线,无法上传文件");
            }
        } else {
            map.put(StatusField.STATUS_CODE, StatusField.DO_FAILED);
            map.put(StatusField.DO_FAILED_DEC, "用户不存在或号码有误");

        }
        logger.debug("..........................."+JsonUtils.writeObjectToJson(map)+".........................................");
        logger.debug("...........................FileUploadApi end.........................................");
        String jsonStr = JsonUtils.writeObjectToJson(map);
        logger.info("上传的json="+jsonStr);
        return jsonStr;
    }

    @Override
    public String getName() {
        return "FileUpload_Api";
    }
}
