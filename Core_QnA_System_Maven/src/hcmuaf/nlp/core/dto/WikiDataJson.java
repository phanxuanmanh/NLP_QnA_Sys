package hcmuaf.nlp.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WikiDataJson {
private int pageid;
private int ns;
private String title;
public int getPageid() {
	return pageid;
}
public void setPageid(int pageid) {
	this.pageid = pageid;
}
public int getNs() {
	return ns;
}
public void setNs(int ns) {
	this.ns = ns;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}

}
