package com.bra.common.upload;


/**
 * 文件上传存储类
 *
 * @author xiaobin
 */
public class FileModel {

    /**
     * 视频类型
     */
    public static final String VIDEO = ".wmv.wm.asf.asx.rm.rmvb.ra.ram.mpg.mpeg.mpe.vob.dat.mov.3gp.mp4.mp4v.m4v.mkv.avi.flv.f4v.mts";
    /**
     * 音频
     */
    public static final String AUDIO = ".mp3.mv";
    /**
     * 图片
     */
    public static final String PICTURE = ".jpg.jpeg.gif.png";
    /**
     * 文档
     */
    public static final String DOC = ".doc.xls.docx.xlsx.pdf";
    /**
     * 幻灯片
     */
    public static final String PPT = ".ppt.pptx.pps.ppsx";

    private String docId;

    /**
     * 附件ID，对应AttMain的主键
     */
    private String attId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 压缩以后图片路径
     */
    private String reSizePath;

    /**
     * 文件扩展名
     */
    private String ext;

    /**
     * 文件大小
     */
    private long fileSize;

    /**
     * 存储类型 返回绝对路径
     */
    private StoreType storeType;

    /**
     * 文档链接类型
     */
    private String contentType;

    /**
     * 附件的MD5码
     */
    private String token;

    /**
     * 是否显示图片
     */
    private String imgShow;

    /**
     * 文件的crc32值
     */
    private Long crc32;

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    /**
     * 附件ID，对应AttMain的主键
     *
     * @return
     */
    public String getAttId() {
        return attId;
    }

    /**
     * 附件ID，对应AttMain的主键
     *
     * @param attId
     */
    public void setAttId(String attId) {
        this.attId = attId;
    }

    /**
     * 文件名称
     * <p>
     * 带文件扩展名
     *
     * @return
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 文件名称 带文件扩展名
     *
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 文件路径
     *
     * @return
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 文件路径
     *
     * @param filePath
     */
    public void setFilePath(String filePath) {

        this.filePath = filePath;
    }

    /**
     * 文件扩展名
     *
     * @return
     */
    public String getExt() {
        return ext;
    }

    /**
     * 文件扩展名
     *
     * @param ext
     */
    public void setExt(String ext) {
        this.ext = ext;
    }

    /**
     * 文件大小
     *
     * @return
     */
    public long getFileSize() {
        return fileSize;
    }

    /**
     * 文件大小
     *
     * @param fileSize
     */
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * 文件返回的存储路径类型
     * <p>
     * <pre>
     * StoreType.DISK:绝对路径
     *
     * SotreType.PROJECT:项目陆军
     * </pre>
     *
     * @return
     */
    public StoreType getStoreType() {
        return storeType;
    }

    /**
     * 文件返回的存储路径类型
     * <p>
     * <pre>
     * StoreType.DISK:绝对路径
     *
     * SotreType.PROJECT:项目陆军
     * </pre>
     *
     * @param storeType
     */
    public void setStoreType(StoreType storeType) {
        this.storeType = storeType;
    }

    /**
     * 文档链接类型
     *
     * @return
     */
    public String getContentType() {
        return contentType;
    }

    public String getReSizePath() {
        return reSizePath;
    }

    public void setReSizePath(String reSizePath) {
        this.reSizePath = reSizePath;
    }

    /**
     * 文档链接类型
     *
     * @param contentType
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getCrc32() {
        return crc32;
    }

    public void setCrc32(Long crc32) {
        this.crc32 = crc32;
    }

    public String getImgShow() {
        if (PICTURE.contains(ext)) {
            return "show";
        }
        return imgShow;
    }

    public void setImgShow(String imgShow) {
        this.imgShow = imgShow;
    }
}
