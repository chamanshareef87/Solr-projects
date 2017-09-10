package com.zyme.solr;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zyme.dao.ScrapeDataDao;
import com.zyme.model.ScrapeData;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

@RestController
public class CrawllerController {

	 private static final String template = "Hello, %s!";
	    private final AtomicLong counter = new AtomicLong();

	    @RequestMapping("/greeting")
	    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
	        return new Greeting(counter.incrementAndGet(),
	                            String.format(template, name));
	    }
	    
	    @RequestMapping("/crawl")
	    public String crawling(@RequestParam(value="site", required=false, defaultValue="https://www.google.co.in/") String site) {
	    	
	    	System.out.println("hello crawl");
	    	try {
				System.out.println("at crawling method....");
				String crawlStorageFolder = "/home/cshareef/crawler/Crawler1/src/main/resources/data";
				int numberOfCrawlers = 1;
				CrawlConfig config = new CrawlConfig();
				config.setCrawlStorageFolder(crawlStorageFolder);
				config.setMaxDepthOfCrawling(0);
				config.setMaxPagesToFetch(10);
				config.setPolitenessDelay(500);
				
				PageFetcher pageFetcher = new PageFetcher(config);
				RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
				RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
				CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
				
				List<ScrapeData> datalist =readurls();
				for(ScrapeData sdata : datalist){
					controller.addSeed(sdata.getUrl());
				}
				
				controller.start(MyCrawler.class, numberOfCrawlers);
	    	} catch (Exception e) {
				System.out.println("Exception:::::");
				e.printStackTrace();
			}
	    	
	    	return "crawl";
	    }
	    
	    @RequestMapping("/readurls")
	    public List<ScrapeData> readurls() {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

			ScrapeDataDao scrapeDao = context.getBean(ScrapeDataDao.class);
			List<ScrapeData> list = scrapeDao.list();
			for(ScrapeData s : list){
				System.out.println("ScrapeData List::"+s.toString());
			}
			context.close();	
			return list;
	    }

	    @RequestMapping("/getByurl")
	    public ScrapeData getByurl(@RequestParam(value="url", required=true) String url) {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

			ScrapeDataDao scrapeDao = context.getBean(ScrapeDataDao.class);
			System.out.println("url::::::"+url);
			ScrapeData data = scrapeDao.getByurl(url);
//			System.out.println("ScrapeData ::"+data.toString());
			context.close();	
			return data;
	    }

}
