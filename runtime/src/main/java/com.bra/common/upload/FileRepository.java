package com.bra.common.upload;

import com.bra.common.config.Global;
import com.bra.common.image.AverageImageScale;
import com.bra.common.image.ImageUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiaobin268 on 2014-9-19.
 */
@org.springframework.stereotype.Repository
public class FileRepository implements ServletContextAware {

    private static final String UPLOAD_PATH = "resources/www";

    public static final int BUFFER_LENGTH = 1024 * 1024 * 10;

    private Logger log = LoggerFactory.getLogger(FileRepository.class);

    public List<FileModel> storeByExts(MultipartFile file, String key) {
        List<FileModel> lists = new ArrayList<FileModel>();
        String origName = file.getOriginalFilename();
        String ext = FilenameUtils.getExtension(origName).toLowerCase(
                Locale.ENGLISH);
        FileModel fileModel = new FileModel();
        if (!checkExt(ext, fileModel)) {
            lists.add(fileModel);
            return lists;
        }
        FileModel model = storeByExt(file, StoreType.SYSTEM.getName(), key, false, null, null);
        lists.add(model);
        return lists;
    }

    public FileModel storeByExt(MultipartFile file, String key) {
        return storeByExt(file, StoreType.SYSTEM.getName(), key, false, null, null);
    }

    public FileModel storeByExt(MultipartFile file, String key, boolean cutImg, String resizeWidth, String resizeHeight) {
        return storeByExt(file, StoreType.SYSTEM.getName(), key, cutImg, resizeWidth, resizeHeight);
    }

    /**
     * 文件存储
     *
     * @param file
     * @param folderType
     * @return
     */
    public FileModel storeByExt(MultipartFile file, String folderType, String key, boolean cutImg, String resizeWidth, String resizeHeight) {
        FileModel fileModel = new FileModel();

        String origName = file.getOriginalFilename();
        String contentType = file.getContentType();
        String ext = FilenameUtils.getExtension(origName).toLowerCase(
                Locale.ENGLISH);
        if (!checkExt(ext, fileModel)) {
            return fileModel;
        }
        try {
            String fileUrl;
            if (StoreType.FOLDER.getName().equals(folderType)) { // 返回项目的相对路径
                fileUrl = storeByExtFolder(ext, file);
                fileModel.setStoreType(StoreType.FOLDER);
            } else { // 返回项目的绝对路径(本次项目默认为此选项)
                fileUrl = storeByExt(ext, file, key, cutImg, resizeWidth, resizeHeight);
                fileModel.setStoreType(StoreType.SYSTEM);
            }
            fileModel.setContentType(contentType);
            fileModel.setExt(ext);
            //fileModel.setToken(Streams.asString(file.getInputStream()));
            fileModel.setFileName(origName);// 文件名称(带文件扩展名)
            fileModel.setFilePath(fileUrl);
            fileModel.setFileSize(file.getSize());
            return fileModel;

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private boolean checkExt(String ext, FileModel fileModel) {
        if ("jsp".equals(ext) || "php".equals(ext) || "html".equals(ext)
                || "htm".equals(ext) || "jspx".equals(ext)) {
            fileModel.setContentType("");
            fileModel.setExt("毛");
            fileModel.setFileName("y@@@@@@@@@@@@@@@");// 文件名称(带文件扩展名)
            fileModel.setFilePath("NNNNNNNNNNNNNNNNNNNNNNNNN");
            fileModel.setFileSize(0);
            return false;
        }
        return true;
    }

    public String storeByExt(String ext, MultipartFile file, String key, boolean cutImg, String resizeWidth, String resizeHeight) throws IOException {
        String destPath = Global.getBaseDir();
        String destfile = destPath + UPLOAD_PATH;
        File dest = new File(destfile + File.separator + UploadUtils.MONTH_FORMAT.format(new Date()) + File.separator + key + "." + ext);
        store(file, dest);
        File tmp = new File(destfile + File.separator + UploadUtils.MONTH_FORMAT.format(new Date()) + File.separator + key);
        if (tmp.exists()) {
            tmp.delete();
        }
        if (ImageUtils.isValidImageExt(ext) && cutImg) {
            String outPath = destfile + File.separator + UploadUtils.MONTH_FORMAT.format(new Date()) + File.separator + key + "-reduction" + "." + ext;
            int width = NumberUtils.toInt(resizeWidth, NumberUtils.toInt(Global.getConfig("resizeFix.image.width"), 150));
            int height = NumberUtils.toInt(resizeHeight, NumberUtils.toInt(Global.getConfig("resizeFix.image.height"), 228));
            AverageImageScale.resizeFix(dest, new File(outPath), width, height);
            return outPath;
        }

        return dest.getAbsolutePath();
    }

    public String storeByExtFolder(String ext, MultipartFile file)
            throws IOException {

        String filename = UploadUtils.generateFilename(UPLOAD_PATH, ext);
        File dest = new File(ctx.getRealPath(filename));

        dest = UploadUtils.getUniqueFile(dest);
        store(file, dest);
        return filename;
    }

    public String storeByFilename(String filename, MultipartFile file)
            throws IOException {
        File dest = new File(ctx.getRealPath(filename));
        store(file, dest);
        return filename;
    }

    public String storeByExt(String ext, File file, String key) throws IOException {
        String destPath = Global.getBaseDir();
        String destfile = destPath + UPLOAD_PATH;
        String filename = UploadUtils.generateFilename(UPLOAD_PATH, ext);
        File dest = new File(destfile + File.separator + UploadUtils.MONTH_FORMAT.format(new Date()) + File.separator + key + "." + ext);


        store(file, dest);
        return filename;
    }

    public String storeByFilename(String filename, File file)
            throws IOException {
        File dest = new File(ctx.getRealPath(filename));
        store(file, dest);
        return filename;
    }

    public boolean delete(String filePath) {

        if (StringUtils.isBlank(filePath))
            return false;
        File file = new File(filePath);
        if (file.exists())
            file.delete();
        return true;
    }

    private void store(MultipartFile file, File dest) throws IOException {
        try {
            //dest.deleteOnExit();
            UploadUtils.checkDirAndCreate(dest.getParentFile());
            file.transferTo(dest);
        } catch (IOException e) {
            log.error("Transfer file error when upload file", e);
            throw e;
        }
    }

    private void store(File file, File dest) throws IOException {
        try {
            UploadUtils.checkDirAndCreate(dest.getParentFile());
            FileUtils.copyFile(file, dest);
        } catch (IOException e) {
            log.error("Transfer file error when upload file", e);
            throw e;
        }
    }

    public void storeToken(String key) throws IOException {
        if (key == null || key.isEmpty())
            return;
        String destPath = Global.getBaseDir();
        String file = destPath + UPLOAD_PATH;

        File f = new File(file + File.separator + UploadUtils.MONTH_FORMAT.format(new Date()) + File.separator + key);
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        if (!f.exists())
            f.createNewFile();
    }

    public void deleteToken(String key) {
        if (key == null || key.isEmpty())
            return;
        String file = Global.getBaseDir();
        File f = new File(file + File.separator + UploadUtils.MONTH_FORMAT.format(new Date()) + File.separator + key);
        if (f.exists())
            f.delete();
    }

    /**
     * Acquired the file.
     *
     * @param key
     * @return
     * @throws FileNotFoundException If key not found, will throws this.
     */
    public File getTokenedFile(String key) throws FileNotFoundException {
        if (key == null || key.isEmpty())
            return null;

        String destPath = Global.getBaseDir();
        String file = destPath + UPLOAD_PATH;

        File f = new File(file + File.separator + UploadUtils.MONTH_FORMAT.format(new Date()) + File.separator + key);
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        if (!f.exists())
            throw new FileNotFoundException("File `" + f + "` not exist.");

        return f;
    }

    public String canConverImg(String key, String fileName, File dest, String resizeWidth, String resizeHeight) throws IOException {
        String ext = FilenameUtils.getExtension(fileName);
        if (ImageUtils.isValidImageExt(ext)) {
            String destPath = Global.getBaseDir();
            String file = destPath + UPLOAD_PATH;

            int width = NumberUtils.toInt(resizeWidth, NumberUtils.toInt(Global.getConfig("resizeFix.image.width"), 150));
            int height = NumberUtils.toInt(resizeHeight, NumberUtils.toInt(Global.getConfig("resizeFix.image.height"), 228));
            String outPath = file + File.separator + UploadUtils.MONTH_FORMAT.format(new Date()) + File.separator + key + "-" + width + "-" + height + "." + ext;
            AverageImageScale.resizeFix(dest, new File(outPath), width, height);
            return outPath;
        }
        return null;
    }


    /**
     * According the key, generate a file (if not exist, then create a new
     * file).
     * <p/>
     * the file relative path(something like `a../bxx/wenjian.txt`)
     *
     * @return
     * @throws IOException
     */
    public File getFile(String key, String fileName) throws IOException {
        if (key == null || key.isEmpty())
            return null;
        String ext = FilenameUtils.getExtension(fileName);
        String file = Global.getBaseDir();
        File f = new File(file + File.separator + UploadUtils.MONTH_FORMAT.format(new Date()) + File.separator + key + "." + ext);
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        if (!f.exists())
            f.createNewFile();

        return f;
    }


    /**
     * From the InputStream, write its data to the given file.
     */
    public long streaming(InputStream in, String key, String fileName) throws IOException {
        OutputStream out = null;
        File f = getTokenedFile(key);
        try {
            out = new FileOutputStream(f);

            int read = 0;
            final byte[] bytes = new byte[BUFFER_LENGTH];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
        } finally {
            IOUtils.closeQuietly(out);
        }

        String ext = FilenameUtils.getExtension(fileName);
        String destPath = Global.getBaseDir();
        String file = destPath + UPLOAD_PATH;

        File dst = new File(file + File.separator + UploadUtils.MONTH_FORMAT.format(new Date()) + File.separator + key + "." + ext);
        dst.delete();
        f.renameTo(dst);

        long length = getFile(key, fileName).length();
        return length;
    }

    static final Pattern RANGE_PATTERN = Pattern
            .compile("bytes \\d+-\\d+/\\d+");

    /**
     * 获取Range参数
     *
     * @param req
     * @return
     * @throws IOException
     */
    public Range parseRange(HttpServletRequest req) throws IOException {
        String range = req.getHeader("content-range");
        Matcher m = RANGE_PATTERN.matcher(range);
        if (m.find()) {
            range = m.group().replace("bytes ", "");
            String[] rangeSize = range.split("/");
            String[] fromTo = rangeSize[0].split("-");

            long from = Long.parseLong(fromTo[0]);
            long to = Long.parseLong(fromTo[1]);
            long size = Long.parseLong(rangeSize[1]);

            return new Range(from, to, size);
        }
        throw new IOException("Illegal Access!");
    }

    public File retrieve(String name) {
        return new File(ctx.getRealPath(name));
    }

    private ServletContext ctx;

    public void setServletContext(ServletContext servletContext) {
        this.ctx = servletContext;
    }
}
