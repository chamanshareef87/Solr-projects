package com.zyme.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zyme.dao.ScrapeDataDao;
import com.zyme.model.ScrapeData;

@RestController
public class ScrapedataController {

	private ScrapeDataDao scrapeDataDao;
	
    public ScrapeDataDao getScrapeDataDao() {
		return scrapeDataDao;
	}

	public void setScrapeDataDao(ScrapeDataDao scrapeDataDao) {
		this.scrapeDataDao = scrapeDataDao;
	}

	@RequestMapping("/readAll")
    public List<ScrapeData> readAll() {
//		ScrapeDataDao scrapeDao = ApplicationContextLoader.getContext().getBean(ScrapeDataDao.class);
		List<ScrapeData> list = scrapeDataDao.list();
		return list;
    }
    
    @RequestMapping("/readById")
    public ScrapeData readById(@RequestParam(value="id", required=true) int id) {
//		ScrapeDataDao scrapeDao = ApplicationContextLoader.getContext().getBean(ScrapeDataDao.class);
		ScrapeData obj = scrapeDataDao.readById(id);
		return obj;
    }

    @RequestMapping("/readByDomain")
    public List<ScrapeData> readByDomain(@RequestParam(value="domain", required=true) String domain) {
//		ScrapeDataDao scrapeDao = ApplicationContextLoader.getContext().getBean(ScrapeDataDao.class);
		List<ScrapeData> list = scrapeDataDao.readByDomain(domain);
		return list;
    }

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public ScrapeData addData(@RequestBody ScrapeData data) {
//		ScrapeDataDao scrapeDao = ApplicationContextLoader.getContext().getBean(ScrapeDataDao.class);
		if(null != data){
			data = scrapeDataDao.save(data);
		}
		CrawllerController crawllerController = new CrawllerController();
		List<ScrapeData> list = new ArrayList<ScrapeData>();
		list.add(data);
		crawllerController.crawlData(list);
		return data;
    }

    @RequestMapping(value="/addList", method = RequestMethod.POST)
    public List<ScrapeData> addDataList(@RequestBody List<ScrapeData> dataList) {
    	System.out.println("at add List::");
    	for(ScrapeData data : dataList){
    		System.out.println(data.toString());
    	}
//    	ScrapeDataDao scrapeDao = ApplicationContextLoader.getContext().getBean(ScrapeDataDao.class);
		if(null != dataList && !dataList.isEmpty()){
			dataList = scrapeDataDao.saveList(dataList);
		}
		CrawllerController crawllerController = new CrawllerController();
		crawllerController.crawlData(dataList);
		return dataList;
    }

    @RequestMapping(value="/update", method=RequestMethod.POST)
    public ScrapeData updateData(@RequestBody ScrapeData data) {
//		ScrapeDataDao scrapeDao = ApplicationContextLoader.getContext().getBean(ScrapeDataDao.class);
		if(null != data){
			data = scrapeDataDao.save(data);
		}
		return data;
    }
}
