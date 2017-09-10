package com.zyme.dao;

import java.util.List;

import com.zyme.model.ScrapeData;

public interface ScrapeDataDao {

	public void save(ScrapeData s);
	public List<com.zyme.model.ScrapeData> list();
	public ScrapeData getByurl(String url);
	
}
