package com.zyme.pojo;

import java.util.List;

public class SearchResult {
	
	String id;
	String domain;
	String url;
	String pageContent;
	int count;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	List<TermCount> termcountlist;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPageContent() {
		return pageContent;
	}
	public void setPageContent(String pageContent) {
		this.pageContent = pageContent;
	}
	public List<TermCount> getTermcountlist() {
		return termcountlist;
	}
	public void setTermcountlist(List<TermCount> termcountlist) {
		this.termcountlist = termcountlist;
	}
	
}
