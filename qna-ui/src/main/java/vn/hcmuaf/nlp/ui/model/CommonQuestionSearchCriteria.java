package vn.hcmuaf.nlp.ui.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CommonQuestionSearchCriteria{
	private Long id;
	private Date startSearchDate;
	private Long fetchSize;
	private String keyWords;
	private String typeName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getStartSearchDate() {
		return startSearchDate;
	}
	public void setStartSearchDate(Date startSearchDate) {
		this.startSearchDate = startSearchDate;
	}
	public Long getFetchSize() {
		return fetchSize;
	}
	public void setFetchSize(Long fetchSize) {
		this.fetchSize = fetchSize;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}
