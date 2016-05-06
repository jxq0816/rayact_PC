package com.bra.modules.mechanism.entity;

import com.bra.common.persistence.DataEntity;
import com.bra.common.upload.FileModel;
import com.bra.common.utils.FileUtils;
import com.bra.common.utils.ImageUtils;
import com.bra.modules.sys.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.validator.constraints.Length;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 文档管理Entity
 * @author 肖斌
 * @version 2015-12-30
 */
public class AttMain extends DataEntity<AttMain> {
	
	private static final long serialVersionUID = 1L;
	private String fdContentType;		// fd_content_type
	private String fdData;		// fd_data
	private String fdFileName;		// fd_file_name
	private String fdFilePath;		// fd_file_path
	private String fdFileType;		// fd_file_type
	private String fdKey;		// fd_key
	private String fdModelId;		// fd_model_id
	private String fdModelName;		// fd_model_name
	private String fdOrder;		// fd_order
	private Double fdSize;		// fd_size
	private String fdStoreType;		// fd_store_type
	private String flag;		// flag
	private String token;		// token
	private User createById;		// create_by_id
	private User updateById;		// update_by_id
	private Long crc32;		// crc32
	private String reSizePath;		// re_size_path
	private String videoView;		// video_view
	
	public AttMain() {
		super();
	}

	public AttMain(String id){
		super(id);
	}

	@Length(min=0, max=255, message="fd_content_type长度必须介于 0 和 255 之间")
	public String getFdContentType() {
		return fdContentType;
	}

	public void setFdContentType(String fdContentType) {
		this.fdContentType = fdContentType;
	}
	
	public String getFdData() {
		return fdData;
	}

	public void setFdData(String fdData) {
		this.fdData = fdData;
	}
	
	@Length(min=0, max=255, message="fd_file_name长度必须介于 0 和 255 之间")
	public String getFdFileName() {
		return fdFileName;
	}

	public void setFdFileName(String fdFileName) {
		this.fdFileName = fdFileName;
	}
	
	@Length(min=0, max=255, message="fd_file_path长度必须介于 0 和 255 之间")
	public String getFdFilePath() {
		return fdFilePath;
	}

	public void setFdFilePath(String fdFilePath) {
		this.fdFilePath = fdFilePath;
	}
	
	@Length(min=0, max=255, message="fd_file_type长度必须介于 0 和 255 之间")
	public String getFdFileType() {
		return fdFileType;
	}

	public void setFdFileType(String fdFileType) {
		this.fdFileType = fdFileType;
	}
	
	@Length(min=0, max=255, message="fd_key长度必须介于 0 和 255 之间")
	public String getFdKey() {
		return fdKey;
	}

	public void setFdKey(String fdKey) {
		this.fdKey = fdKey;
	}
	
	@Length(min=0, max=255, message="fd_model_id长度必须介于 0 和 255 之间")
	public String getFdModelId() {
		return fdModelId;
	}

	public void setFdModelId(String fdModelId) {
		this.fdModelId = fdModelId;
	}
	
	@Length(min=0, max=255, message="fd_model_name长度必须介于 0 和 255 之间")
	public String getFdModelName() {
		return fdModelName;
	}

	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}
	
	@Length(min=0, max=255, message="fd_order长度必须介于 0 和 255 之间")
	public String getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}
	
	public Double getFdSize() {
		return fdSize;
	}

	public void setFdSize(Double fdSize) {
		this.fdSize = fdSize;
	}
	
	@Length(min=0, max=255, message="fd_store_type长度必须介于 0 和 255 之间")
	public String getFdStoreType() {
		return fdStoreType;
	}

	public void setFdStoreType(String fdStoreType) {
		this.fdStoreType = fdStoreType;
	}
	
	@Length(min=0, max=11, message="flag长度必须介于 0 和 11 之间")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@Length(min=0, max=255, message="token长度必须介于 0 和 255 之间")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public User getCreateById() {
		return createById;
	}

	public void setCreateById(User createById) {
		this.createById = createById;
	}
	
	public User getUpdateById() {
		return updateById;
	}

	public void setUpdateById(User updateById) {
		this.updateById = updateById;
	}
	
	public Long getCrc32() {
		return crc32;
	}

	public void setCrc32(Long crc32) {
		this.crc32 = crc32;
	}
	
	@Length(min=0, max=255, message="re_size_path长度必须介于 0 和 255 之间")
	public String getReSizePath() {
		return reSizePath;
	}

	public void setReSizePath(String reSizePath) {
		this.reSizePath = reSizePath;
	}
	
	@Length(min=0, max=255, message="video_view长度必须介于 0 和 255 之间")
	public String getVideoView() {
		return videoView;
	}

	public void setVideoView(String videoView) {
		this.videoView = videoView;
	}

    public String getFormatFileSize() {
        return FileUtils.formetFileSize(fdSize);
    }


    public Long getCrc() {
        if (getCrc32() != null)
            return crc32;
        long crc ;
        try {
            File file = new File(this.getFdFilePath());
            if (file.exists())
                crc = FileUtils.checksumCRC32(file);
            else
                crc = 0L;
        } catch (IOException e) {
            crc = 0L;
        }
        return crc;
    }

    public String getJpgFile() {
        if (this.fdFileType != null && ImageUtils.hasImgExt(fdFileType.toLowerCase())) {
            if (!ImageUtils.JPG.equals(fdFileType.toLowerCase())) {
                File file = new File(fdFilePath);
                String filePath = file.getParent() + File.separator + FileUtils.getFilePrefix(file) + ".jpg";
                try {
                    ImageUtils.convert(fdFilePath, "jpg", filePath);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return filePath;
            }
        }
        return fdFilePath;
    }


    @JsonIgnore
    public AttMain(FileModel model) {
        setId(model.getAttId());
        setFdFileName(model.getFileName());
        setFdContentType(model.getContentType());
        setCreateDate(new Date());
        setFdFilePath(model.getFilePath());

        try {
            File file = new File(model.getFilePath());
            if (file.exists())
                this.crc32 = FileUtils.checksumCRC32(new File(model.getFilePath()));
            else
                this.crc32 = 0L;
        } catch (IOException e) {
            this.crc32 = 0L;
        }
        setFdFileType(model.getContentType());
        setFdSize(NumberUtils.toDouble(model.getFileSize() + "", 0.00));
        setFdStoreType(model.getStoreType().getName());
        setToken(model.getToken());
    }
	
}