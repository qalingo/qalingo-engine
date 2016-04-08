package org.hoteia.qalingo.core.web.mvc.form;

import org.springframework.web.multipart.MultipartFile;

public class CmsMenuBlockForm {
	
	protected String cmsMenuId;
    
    protected String title;
    protected String text;
	
    protected MultipartFile file;

	public String getCmsMenuId() {
		return cmsMenuId;
	}

	public void setCmsMenuId(String cmsMenuId) {
		this.cmsMenuId = cmsMenuId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}