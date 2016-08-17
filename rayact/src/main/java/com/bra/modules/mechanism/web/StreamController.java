package com.bra.modules.mechanism.web;

import com.bra.common.upload.FileModel;
import com.bra.common.upload.FileRepository;
import com.bra.common.upload.Range;
import com.bra.common.upload.StoreType;
import com.bra.common.utils.ImageUtils;
import com.bra.common.utils.StringUtils;
import com.bra.modules.mechanism.entity.AttMain;
import com.bra.modules.mechanism.exception.StreamException;
import com.bra.modules.mechanism.service.AttMainService;
import jodd.io.FileNameUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaobin on 15/4/14.
 */
@Controller
@RequestMapping("/mechanism/html5/upload")
@Scope("prototype")
public class StreamController {

    private static final Logger logger = LoggerFactory.getLogger(StreamController.class);
    @Autowired
    private FileRepository fileRepository;

    static final int BUFFER_LENGTH = 10240;
    public static final String START_FIELD = "start";

    @Autowired
    private AttMainService attMainService;

    /**
     * 上传图片的第一步
     * html5 上传文件
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> execute(HttpServletRequest req,
                                       HttpServletResponse resp, ModelMap model) {
        doOptions(req, resp);
        final String token = req.getParameter(TokenController.TOKEN_FIELD);
        final String size = req.getParameter(TokenController.FILE_SIZE_FIELD);
        final String fileName = req.getParameter(TokenController.FILE_NAME_FIELD);

        Map<String, Object> json = new HashMap<>();
        AttMain attMain = attMainService.convertAttMainbyToken(token);
        if (attMain != null) {
            json.put("fileName", attMain.getFdFileName());
            json.put("attId", attMain.getId());
            json.put("fileType", FileNameUtil.getExtension(attMain.getFdFileName()));
            json.put(START_FIELD, attMain.getFdSize());
            json.put(TokenController.SUCCESS, true);
            json.put("status", 1);
            fileRepository.deleteToken(token);
            return json;
        }
        long start = 0;
        boolean success = true;
        String message = "";
        try {
            File f = fileRepository.getTokenedFile(token);
            start = f.length();
            if (token.endsWith("_0") && "0".equals(size) && 0 == start)
                f.renameTo(fileRepository.getFile(token, fileName));
        } catch (IOException e) {
            message = "Error: " + e.getMessage();
            success = false;
        } finally {
            if (success)
                json.put(START_FIELD, start);
            json.put(TokenController.SUCCESS, success);
            json.put(TokenController.MESSAGE, message);
        }
        return json;
    }

    /**
     * 上传文件到指定文件夹在该方法完成
     * @param req
     * @param resp
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> upload(HttpServletRequest req,
                                      HttpServletResponse resp, ModelMap model) throws IOException {
        doOptions(req, resp);
        final String token = req.getParameter(TokenController.TOKEN_FIELD);
        String fileName = req.getParameter(TokenController.FILE_NAME_FIELD);
        String resizeImg = req.getParameter("resizeImg");
        String resizeWidth = req.getParameter("resizeWidth");
        String resizeHeight = req.getParameter("resizeHeight");
        String imgCheck = req.getParameter("imgCheck");
        //fileName = new String(fileName.getBytes("iso-8859-1"),"UTF-8");
        Map<String, Object> json = new HashMap<>();


        Range range = fileRepository.parseRange(req);
        FileModel fileModel = new FileModel();
        OutputStream out = null;
        InputStream content = null;

        String contentType = req.getContentType();
        long start = 0;
        boolean success = true;
        String message = "";
        File f = fileRepository.getTokenedFile(token);//读取本地文件
        try {
            if (f.length() != range.getFrom()) {
                throw new StreamException(StreamException.ERROR_FILE_RANGE_START);
            }
            out = new FileOutputStream(f, true);
            content = req.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[BUFFER_LENGTH];
            while ((read = content.read(bytes)) != -1)
                out.write(bytes, 0, read);
            start = f.length();
        } catch (StreamException se) {
            success = StreamException.ERROR_FILE_RANGE_START == se.getCode();
            message = "Code: " + se.getCode();
            logger.error(message + ":" + se.getMessage());
        } catch (FileNotFoundException fne) {
            message = "Code: " + StreamException.ERROR_FILE_NOT_EXIST;
            success = false;
            logger.error(fne.getMessage());
        } catch (IOException io) {
            message = "IO Error: " + io.getMessage();
            success = false;
            logger.error(io.getMessage());
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(content);
            final String ext = FilenameUtils.getExtension(fileName);
            /** rename the file */
            if (range.getSize() == start) {
                /** fix the `renameTo` bug */
                File dst = fileRepository.getFile(token, fileName);
                dst.delete();
                f.renameTo(dst);//将文件写入到指定文件夹

                if (StringUtils.isNotBlank(imgCheck)) {
                    if (!ImageUtils.getFormatName(dst, imgCheck)) {
                        json.put("status", 0);
                        dst.deleteOnExit();
                        dst = null;
                        return json;
                    }
                }

                //判断是否压缩图片
                if ("true".equals(resizeImg)) {
                    String outPath = fileRepository.canConverImg(token, fileName, dst, resizeWidth, resizeHeight);
                    if (StringUtils.isNotBlank(outPath)) {
                        fileModel.setFilePath(dst.getAbsolutePath());
                        fileModel.setReSizePath(outPath);
                    } else {
                        fileModel.setFilePath(dst.getAbsolutePath());
                    }
                } else {
                    fileModel.setFilePath(dst.getAbsolutePath());
                }
                fileModel.setFileSize(start);
                fileModel.setStoreType(StoreType.SYSTEM);
                fileModel.setContentType(contentType);
                fileModel.setExt(ext);
                fileModel.setToken(token);
                fileModel.setFileName(fileName);// 文件名称(带文件扩展名)
                AttMain attMain = new AttMain(fileModel);
                attMain = attMainService.saveAttMain(attMain);
                fileModel.setAttId(attMain.getId());
            }
        }
        if (success) {
            json.put(START_FIELD, start);
            json.put("fileName", fileModel.getFileName());
            json.put("attId", fileModel.getAttId());
            json.put("fileType", FileNameUtil.getExtension(fileModel.getFileName()));
        }
        json.put("status", 1);
        json.put(TokenController.SUCCESS, success);
        json.put("fileName", fileName);
        json.put(TokenController.MESSAGE, message);
        return json;
    }


    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Headers", "Content-Range,Content-Type");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
    }
}
