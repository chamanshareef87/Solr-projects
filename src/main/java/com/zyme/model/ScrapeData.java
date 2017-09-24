package com.zyme.model;

import java.sql.Date;

//@Entity
//@Table(name="scrapedata")
public class ScrapeData {

//	@Id
//	@Column(name="id")
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String company;
	private String url;
	private Date insert_time;
	private String content;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "ScrapeData [id=" + id + ", company=" + company + ", url=" + url + ", content=" + content + "]";
	}
	public Date getInsert_time() {
		return insert_time;
	}
	public void setInsert_time(Date insert_time) {
		this.insert_time = insert_time;
	}

}
