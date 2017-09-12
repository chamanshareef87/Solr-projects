package com.zyme.solr;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zyme.dao.ScrapeDataDao;
import com.zyme.model.ScrapeData;

@RestController
public class ScrapedataController {

    @RequestMapping("/readAll")
    public List<ScrapeData> readAll() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		ScrapeDataDao scrapeDao = context.getBean(ScrapeDataDao.class);
		List<ScrapeData> list = scrapeDao.list();
		context.close();	
		return list;
    }
    
    @RequestMapping(value="/add", method = RequestMethod.POST)
    public List<ScrapeData> addData(@RequestBody ScrapeData data) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		ScrapeDataDao scrapeDao = context.getBean(ScrapeDataDao.class);
		if(null != data){
			scrapeDao.save(data);
		}
		
		List<ScrapeData> list = scrapeDao.list();
		context.close();	
		return list;
    }
    
    @RequestMapping("/update")
    public List<ScrapeData> updateData() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		ScrapeDataDao scrapeDao = context.getBean(ScrapeDataDao.class);

		
		List<ScrapeData> list = scrapeDao.list();
		context.close();	
		return list;
    }
}
