package com.bra.modules.cms.entity;

import com.bra.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 回帖Entity
 * @author ddt
 * @version 2016-05-25
 */
public class Post extends DataEntity<Post> {
	
	private static final long serialVersionUID = 1L;
	private String content;		// content
	private String postId;		// post_id
    private String ptpId;
	private PostMain postMain;
	public Post() {
		super();
	}

	public Post(String id){
		super(id);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=19, message="post_id长度必须介于 0 和 19 之间")
	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}


	public PostMain getPostMain() {
		return postMain;
	}

	public void setPostMain(PostMain postMain) {
		this.postMain = postMain;
	}


	public String getPtpId() {
		return ptpId;
	}

	public void setPtpId(String ptpId) {
		this.ptpId = ptpId;
	}



}