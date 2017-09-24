package com.zyme.dao;

import java.util.List;

import com.zyme.model.ScrapeData;

public interface ScrapeDataDao {

	public ScrapeData save(ScrapeData s);
	public List<ScrapeData> saveList(List<ScrapeData> dataList);
	public List<com.zyme.model.ScrapeData> list();
	public List<ScrapeData> list1();
	public List<ScrapeData> getUncrawled();
	public ScrapeData getByurl(String url);
	public ScrapeData readById(int id);
	public List<ScrapeData> readByDomain(String domain);
	
}
