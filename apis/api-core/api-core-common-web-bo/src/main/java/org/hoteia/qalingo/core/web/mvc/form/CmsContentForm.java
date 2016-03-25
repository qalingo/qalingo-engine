package org.hoteia.qalingo.core.web.mvc.form;

import org.springframework.web.multipart.MultipartFile;

public class CmsContentForm {
	
	private String id;
    
    private String code;
    private String type;
    private String title;
    private String linkTitle;
    private String seoSegment;
    private String seoKey;
    private String summary;
    private boolean master;
    private boolean active;
    
    private CmsContentBlockForm defaultBlock;
    
    private MultipartFile file;

    private String dateCreate;
	private String dateUpdate;
	
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