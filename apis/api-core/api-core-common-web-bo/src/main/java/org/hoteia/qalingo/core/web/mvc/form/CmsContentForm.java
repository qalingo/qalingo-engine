package org.hoteia.qalingo.core.web.mvc.form;

import org.springframework.web.multipart.MultipartFile;

public class CmsContentForm {
	
	protected String id;
    
    protected String code;
    protected String type;
    protected String title;
    protected String linkTitle;
    protected String seoSegment;
    protected String seoKey;
    protected String summary;
    protected boolean master;
    protected boolean active;
    
    protected CmsContentBlockForm defaultBlock;
    
    protected MultipartFile file;

    protected String dateCreate;
	protected String dateUpdate;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLinkTitle() {
		return linkTitle;
	}

	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}

	public String getSeoSegment() {
		return seoSegment;
	}
	
	public void setSeoSegment(String seoSegment) {
		this.seoSegment = seoSegment;
	}
	
	public String getSeoKey() {
		return seoKey;
	}
	
	public void setSeoKey(String seoKey) {
		this.seoKey = seoKey;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public boolean isMaster() {
		return master;
	}

	public void setMaster(boolean master) {
		this.master = master;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public CmsContentBlockForm getDefaultBlock() {
		return defaultBlock;
	}
	
	public void setDefaultBlock(CmsContentBlockForm defaultBlock) {
		this.defaultBlock = defaultBlock;
	}
	
	public MultipartFile getFile() {
		return file;
	}
	
	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(String dateCreate) {
		this.dateCreate = dateCreate;
	}

	public String getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(String dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

}