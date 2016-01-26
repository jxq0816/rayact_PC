package com.bra.modules.mechanism.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.bra.common.service.ServiceException;
import com.bra.common.utils.Collections3;
import com.bra.common.utils.IdGen;
import com.bra.common.utils.MyBeanUtils;
import com.bra.common.utils.StringUtils;
import com.bra.modules.mechanism.web.bean.AttMainForm;
import jodd.io.FileNameUtil;
import jodd.io.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.mechanism.entity.AttMain;
import com.bra.modules.mechanism.dao.AttMainDao;
import org.springframework.util.Assert;

/**
 * 文档管理Service
 *
 * @author 肖斌
 * @version 2015-12-30
 */
@Service
@Transactional(readOnly = true)
public class AttMainService extends CrudService<AttMainDao, AttMain> {

    /**
     * 更新文档信息
     *
     * @param attMainForm
     * @param modelId
     * @param modelName
     * @param map
     */
    @Transactional(readOnly = false)
    public void updateAttMainForm(AttMainForm attMainForm, String modelId, String modelName, Map<String, String> map) {
        for (String key : map.keySet()) {
            List<AttMain> attMains = MyBeanUtils.getGetFieldValue(attMainForm, "attMains" + map.get(key));
            for (AttMain attMain : attMains) {
                if (StringUtils.isNotBlank(attMain.getId())) {
                    updateAttMain(attMain.getId(), modelId, modelName, attMain.getFdKey());
                }
            }
        }
    }

    /**
     * 更新文档信息
     *
     * @param attMainForm
     * @param modelId
     * @param modelName
     */
    @Transactional(readOnly = false)
    public void updateAttMainForm(AttMainForm attMainForm, String modelId, String modelName) {
        if (attMainForm == null) return;
        Map<String, Object> map = MyBeanUtils.describe(attMainForm);

        for (String key : map.keySet()) {
            if ("class".equals(key)) continue;
            List<AttMain> attMains = (List<AttMain>) map.get(key);
            if (Collections3.isEmpty(attMains)) continue;
            for (AttMain attMain : attMains) {
                if (StringUtils.isNotBlank(attMain.getId())) {
                    updateAttMain(attMain.getId(), modelId, modelName, attMain.getFdKey());
                }
            }
        }
    }

    @Transactional(readOnly = false)
    public AttMain saveAttMain(AttMain attMain) {
        String filePath = attMain.getFdFilePath();
        if (StringUtils.isNotBlank(filePath)) {
            String prefix = filePath.substring(filePath.lastIndexOf(".") + 1);
            //文件后缀名
            attMain.setFdFileType(prefix);
        }
        //修改token值
       String id = attMain.getId();
        if (StringUtils.isNotBlank(id)) {
            attMain.setToken(attMain.getId());
        } else {
            attMain.setToken(IdGen.uuid());
        }
        save(attMain);
        return attMain;
    }

    public List<AttMain> getAttMain(String modelId, String modelName) {
        AttMain attMain = new AttMain();
        attMain.setFdModelId(modelId);
        attMain.setFdModelName(modelName);
        return findList(attMain);
    }

    public List<AttMain> getAttMain(String modelId, String modelName, String fdKey) {
        AttMain attMain = new AttMain();
        attMain.setFdModelId(modelId);
        attMain.setFdModelName(modelName);
        attMain.setFdKey(fdKey);
        return findList(attMain);
    }

    public AttMain getUniqueAttMain(String modelId, String modelName, String fdKey) {
        List<AttMain> attMains = getAttMain(modelId, modelName, fdKey);
        if (Collections3.isEmpty(attMains))
            return null;
        return attMains.get(0);
    }

    /**
     * 根据modelId、modelName、key删除附件信息
     *
     * @param modelId
     * @param modelName
     */
    @Transactional(readOnly = false)
    public void deleteAttMain(String modelId, String modelName, String fdKey) {
        List<AttMain> attMains = getAttMain(modelId, modelName, fdKey);
        if (attMains != null) {
            for (AttMain attMain : attMains) {
                //判断删除资源文件
                String filePath = attMain.getFdFilePath();
                AttMain attMainCopy = new AttMain();
                MyBeanUtils.copyProperties(attMain, attMainCopy);
                deleteFile(filePath, attMain.getId());
                //删除附件记录
                delete(attMain);
            }
        }
    }

    public AttMain getAttMainUniqueByToken(String token) {
        AttMain attMain = new AttMain();
        attMain.setToken(token);
        List<AttMain> attMains = findList(attMain);
        if (attMains == null || attMains.size() < 1)
            return null;
        return attMains.get(0);
    }

    /**
     * 根据token获取文档，如果存在新建一个文档，并把id值为新的，modelId和modelName值为null
     *
     * @param token
     * @return
     */
    @Transactional(readOnly = false)
    public AttMain convertAttMainbyToken(String token) {
        AttMain attMain = getAttMainUniqueByToken(token);
        if (attMain == null)
            return null;
        String path = attMain.getFdFilePath();
        if (path == null || !new File(path).exists()) {
            return null;
        }
        AttMain convertAttMain = new AttMain();
        MyBeanUtils.copyProperties(attMain, convertAttMain);
        convertAttMain.setId(IdGen.uuid());
        convertAttMain.setFdModelId(null);
        convertAttMain.setFdModelName(null);
        convertAttMain.setIsNewRecord(true);
        saveAttMain(convertAttMain);
        return convertAttMain;
    }


    @Transactional(readOnly = false)
    public boolean deleteAttMainById(String id) {
        Assert.notNull(id);
        AttMain attMain = get(id);
        AttMain attMainCopy = new AttMain();
        MyBeanUtils.copyProperties(attMain, attMainCopy);
        String filePath = attMain.getFdFilePath();
        delete(attMain);
        deleteFile(filePath, attMain.getId());
        return false;
    }


    private void deleteFile(String filePath, String attId) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
            String swfPath = FileNameUtil.getFullPath(filePath);
            swfPath = swfPath + attId;
            File swfFile = new File(swfPath);
            if (swfFile.exists() && swfFile.isDirectory()) {
                try {
                    FileUtil.deleteDir(swfFile);
                } catch (IOException e) {
                    throw new ServiceException(e);
                }
            }
        }
    }


    /**
     * 查询附件张数
     *
     * @param modelId
     * @param modelName
     * @return
     */
    public int getAttMainCount(String modelId, String modelName, String fdKey) {
        int count = 0;
        List<AttMain> attMains = getAttMain(modelId, modelName, fdKey);
        if (!Collections3.isEmpty(attMains))
            count = attMains.size();
        return count;
    }


    @Transactional(readOnly = false)
    public AttMain updateAttMain(String id, String modelId, String modelName, String fdKey) {
        AttMain bean = new AttMain();
        bean.setId(id);
        bean.setFdModelId(modelId);
        bean.setFdModelName(modelName);
        bean.setFdKey(fdKey);
        dao.updateAttMain(bean);
        return bean;
    }

    public AttMain get(String id) {
        return super.get(id);
    }

    public List<AttMain> findList(AttMain attMain) {
        return super.findList(attMain);
    }

    public Page<AttMain> findPage(Page<AttMain> page, AttMain attMain) {
        return super.findPage(page, attMain);
    }

    @Transactional(readOnly = false)
    public void save(AttMain attMain) {
        super.save(attMain);
    }

    @Transactional(readOnly = false)
    public void delete(AttMain attMain) {
        super.delete(attMain);
    }


}