package com.bra.modules.mechanism.web;

import com.bra.common.download.DownloadHelper;
import com.bra.common.image.AverageImageScale;
import com.bra.common.upload.FileModel;
import com.bra.common.upload.FileRepository;
import com.bra.modules.mechanism.entity.AttMain;
import com.bra.modules.mechanism.service.AttMainService;
import jodd.io.FileNameUtil;
import jodd.io.StreamUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaobin on 15/4/14.
 */
@Controller
@RequestMapping("/mechanism/file")
@Scope("prototype")
public class FileController {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private AttMainService attMainService;

    /**
     * 上传文件
     */
    @RequestMapping("/flash_upload")
    @ResponseBody
    public List<FileModel> execute(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("Filedata");
        String token = request.getParameter(TokenController.TOKEN_FIELD);
        String modelId = request.getParameter("modelId");
        String modelName = request.getParameter("modelName");
        String fdKey = request.getParameter("fdKey");
        if (StringUtils.isBlank(token)) {
            token = generateToken(file.getOriginalFilename(), ((Long) file.getSize()).toString());
        }
        AttMain attMain = attMainService.convertAttMainbyToken(token);
        if (attMain != null) {
            fileRepository.deleteToken(token);
            return null;
        }
        List<FileModel> fileModels = new ArrayList<FileModel>();
        boolean success = true;
        try {
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                    request.getSession().getServletContext());
            commonsMultipartResolver.setDefaultEncoding("utf-8");

            Map<String, MultipartFile> multipartFileMap = multipartRequest.getFileMap();
            for (String key : multipartFileMap.keySet()) {
                MultipartFile multipartFile = multipartFileMap.get(key);
                FileModel fileModel = fileRepository.storeByExt(multipartFile, token);
                fileModel.setToken(token);
                attMain = new AttMain(fileModel);
                attMain.setFdModelId(modelId);
                attMain.setFdModelName(modelName);
                attMain.setFdKey(fdKey);
                attMain = attMainService.saveAttMain(attMain);
                fileModel.setAttId(attMain.getId());
                fileModels.add(fileModel);
            }

        } catch (Exception ex) {
            success = false;
        }
        return fileModels;
    }

    /**
     * 上传文件
     *
     * @throws IOException
     */
    @RequestMapping("/o_upload")
    @ResponseBody
    public Map<String, Object> upload(HttpServletRequest request) throws IOException {
        Map<String, Object> json = new HashMap<String, Object>();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("Filedata");
        String resizeImg = request.getParameter("resizeImg");
        String resizeWidth = request.getParameter("resizeWidth");
        String resizeHeight = request.getParameter("resizeHeight");
        String imgCheck = request.getParameter("imgCheck");
        String token = request.getParameter(TokenController.TOKEN_FIELD);
        if (StringUtils.isBlank(token)) {
            token = generateToken(file.getOriginalFilename(), ((Long) file.getSize()).toString());
        }
        AttMain attMain = attMainService.convertAttMainbyToken(token);
        if (attMain != null) {
            json.put(StreamController.START_FIELD, attMain.getFdSize());
            json.put("fileName", attMain.getFdFileName());
            json.put("attId", attMain.getId());
            json.put("fileType", FileNameUtil.getExtension(attMain.getFdFileName()));
            json.put(TokenController.SUCCESS, true);
            fileRepository.deleteToken(token);
            return json;
        }
        boolean success = true;
        try {
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                    request.getSession().getServletContext());
            commonsMultipartResolver.setDefaultEncoding("utf-8");

            Map<String, MultipartFile> multipartFileMap = multipartRequest.getFileMap();
            for (String key : multipartFileMap.keySet()) {
                MultipartFile multipartFile = multipartFileMap.get(key);

                if (StringUtils.isNotBlank(imgCheck)) {
                    if (!com.bra.common.utils.ImageUtils.getFormatName(multipartFile.getInputStream(), imgCheck)) {
                        json.put("status", 0);
                        return json;
                    }
                }
                FileModel fileModel = fileRepository.storeByExt(multipartFile, token, "true".equals(resizeImg), resizeWidth, resizeHeight);
                fileModel.setToken(token);
                attMain = new AttMain(fileModel);
                attMain = attMainService.saveAttMain(attMain);
                fileModel.setAttId(attMain.getId());
                json.put(StreamController.START_FIELD, fileModel.getFileSize());
                json.put("fileName", fileModel.getFileName());
                json.put("attId", fileModel.getAttId());
                json.put("fileType", FileNameUtil.getExtension(attMain.getFdFileName()));
            }

        } catch (Exception ex) {
            success = false;
            json.put("status", 0);
            // ex.printStackTrace();
            //message = "Error: " + ex.getLocalizedMessage();
        }
        json.put("status", 1);
        json.put(TokenController.SUCCESS, success);
        return json;
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    public Map<String, Object> delete(@PathVariable("id") String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        attMainService.deleteAttMainById(id);
        map.put("status", 1);
        return map;
    }

    /**
     * 文件下载
     *
     * @param id (对应AttMain的主键)
     * @return
     */
    @RequestMapping("/download/{id}")
    @ResponseBody
    public DownloadHelper download(@PathVariable("id") String id,
                                   HttpServletRequest request, HttpServletResponse response) {
        DownloadHelper dh = new DownloadHelper();
        dh.setRequest(request);
        AttMain attMain = attMainService.get(id);
        String filePath = attMain.getFdFilePath();
        File file = new File(filePath);
        if (!file.exists()) {
            // @todo :抛出异常
        }
        dh.setFile(new File(filePath));
        dh.setFileName(attMain.getFdFileName());
        return dh;
    }


    /**
     * 查看图片
     *
     * @param id       对应业务表主键，也就是AttMain的modelId
     * @param request
     * @param response
     */
    @RequestMapping("/image/{id}")
    public void image(@PathVariable("id") String id,
                      HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "no-store");

        AttMain attMain = attMainService.get(id);
        if(attMain==null){
            return;//数据库中不存在该记录
        }
        response.setContentType("image/jpeg");
        File file = new File(attMain.getFdFilePath());
        if (!file.exists()) {
            return;//服务器不存在该文件
        }
        OutputStream out;
        try {
            out = response.getOutputStream();
            InputStream is = new FileInputStream(attMain.getFdFilePath());
            byte buffer[] = new byte[1024];
            while (is.read(buffer, 0, 1024) != -1) {
                out.write(buffer);
            }
            out.flush();
            StreamUtil.close(is);
            StreamUtil.close(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查看图片
     *
     * @param request
     * @param response
     */
    @RequestMapping("/imageMobile/{modelId}/{modelName}/{key}")
    public void imageMobile(@PathVariable("modelId") String modelId,
                            @PathVariable("modelName") String modelName,
                            @PathVariable("key") String key,
                            HttpServletRequest request, HttpServletResponse response) {
        Integer resizeWidth = NumberUtils.toInt(request.getParameter("width"), 0);
        Integer resizeHeight = NumberUtils.toInt(request.getParameter("height"), 0);
        // response.setHeader("Cache-Control", "max-age=2592000");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "no-store");
        AttMain attMain = null;
        String fdFilePath;
        if (!"default".equals(modelId)) {
            attMain = attMainService.getUniqueAttMain(modelId, modelName, key);
        }
        File file;
        if (attMain == null) {
            return;
        }
        file = new File(attMain.getFdFilePath());
        fdFilePath = attMain.getFdFilePath();
        response.setContentType("image/jpeg");
        if (!file.exists()) {
            return;
        } else {
            String filePath;
            if (resizeWidth > 0 && resizeHeight > 0) {
                filePath = fdFilePath;
                String fileName = FilenameUtils.getBaseName(filePath);
                String extName = FilenameUtils.getExtension(filePath);
                String fullPath = FilenameUtils.getFullPath(filePath);
                filePath = fullPath + fileName + "-" + resizeWidth + "-" + resizeHeight + "." + extName;
                File resizeFile = new File(filePath);
                if (!resizeFile.exists()) {
                    try {
                        AverageImageScale.resizeFix(file, resizeFile, resizeWidth, resizeHeight);
                        file = resizeFile;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    file = resizeFile;
                }
            }
        }
        OutputStream out;
        try {
            out = response.getOutputStream();
            InputStream is = new FileInputStream(file.getPath());
            byte buffer[] = new byte[1024];
            while (is.read(buffer, 0, 1024) != -1) {
                out.write(buffer);
            }
            out.flush();
            StreamUtil.close(is);
            StreamUtil.close(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * 查看图片
     *
     * @param id       对应业务表主键，也就是AttMain的modelId
     * @param response
     */
    @RequestMapping("/video/{id}")
    public void video(@PathVariable("id") String id, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader( "Cache-Control", "no-store");
        AttMain attMain = attMainService.get(id);
        response.setContentType("video/mp4");
        File file = new File(attMain.getFdFilePath());
        if (!file.exists()) {
            return;
        }
        OutputStream out;
        try {
            out = response.getOutputStream();
            InputStream is = new FileInputStream(attMain.getFdFilePath());
            byte buffer[] = new byte[1024];
            while (is.read(buffer, 0, 1024) != -1) {
                out.write(buffer);
            }
            out.flush();
            StreamUtil.close(is);
            StreamUtil.close(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String generateToken(String name, String size) throws IOException {
        if (name == null || size == null)
            return "";
        int code = name.hashCode();
        try {
            String token = (code > 0 ? "A" : "B") + Math.abs(code) + "_"
                    + size.trim();
            /** TODO: store your token, here just create a file */
            fileRepository.storeToken(token);

            return token;
        } catch (Exception e) {
            throw new IOException(e);
        }
    }


}
