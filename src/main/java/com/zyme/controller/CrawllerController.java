package com.zyme.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zyme.dao.ScrapeDataDao;
import com.zyme.model.ScrapeData;
import com.zyme.solr.DomainCrawler;
import com.zyme.solr.Greeting;
import com.zyme.solr.MyCrawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

@RestController
public class CrawllerController {

//	@Autowired
	private ScrapeDataDao scrapeDataDao;

	public ScrapeDataDao getScrapeDataDao() {
		return scrapeDataDao;
	}

	public void setScrapeDataDao(ScrapeDataDao scrapeDataDao) {
		this.scrapeDataDao = scrapeDataDao;
	}

	 private static final String template = "Hello, %s!";
	    private final AtomicLong counter = new AtomicLong();
	    
	    public static String domain="";
	    @RequestMapping("/greeting")
	    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
	        return new Greeting(counter.incrementAndGet(),
	                            String.format(template, name));
	    }
	    
	    @RequestMapping("/crawlnew")
	    public String crawlnew(@RequestParam(value="site", required=false, defaultValue="https://www.google.co.in/") String site) {
	    	
	    	System.out.println("hello crawl");
	    	try {
				List<ScrapeData> datalist =getUncrawledUrls();
				crawlData(datalist);
	    	} catch (Exception e) {
				System.out.println("Exception:::::");
				e.printStackTrace();
			}
	    	return "crawl successful.";
	    }
	    
	    @RequestMapping("/crawl")
	    public String crawling(@RequestParam(value="site", required=false, defaultValue="https://www.google.co.in/") String site) {
	    	
	    	System.out.println("hello crawl");
	    	try {
//		    	ScrapeDataDao scrapeDao = ApplicationContextLoader.getContext().getBean(ScrapeDataDao.class);
				List<ScrapeData> datalist = scrapeDataDao.list();
				crawlData(datalist);
	    	} catch (Exception e) {
				System.out.println("Exception:::::");
				e.printStackTrace();
			}
	    	return "crawl successful.";
	    }

	    @RequestMapping("/crawldomain")
	    public String crawlDomain(@RequestParam(value="domain", required=true) String domain) {
	    	System.out.println("crawling domain");
	    	try {
	    		this.domain=domain;
				crawlByDomain(domain);
	    	} catch (Exception e) {
				System.out.println("Exception:::::");
				e.printStackTrace();
			}
	    	
	    	return "crawl successful.";
	    }

	    
	   private void crawlByDomain(String domain) {
		   try {
				System.out.println("crawling domian::"+domain);
				String crawlStorageFolder = "../../src/main/resources/data";
				
				int numberOfCrawlers = 1;
				CrawlConfig config = new CrawlConfig();
				config.setCrawlStorageFolder(crawlStorageFolder);
				config.setMaxDepthOfCrawling(1);
//				config.setMaxPagesToFetch(10);
				config.setPolitenessDelay(500);
				
				PageFetcher pageFetcher = new PageFetcher(config);
				RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
				RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
				CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
				controller.addSeed(domain);
				controller.start(DomainCrawler.class, numberOfCrawlers);
				controller.setCustomData(domain);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		}

	public void  crawlData(List<ScrapeData> datalist ){
			try {
				System.out.println("at crawling method....");
				String crawlStorageFolder = "../../../src/main/resources/data";
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
				for(ScrapeData sdata : datalist){
					controller.addSeed(sdata.getUrl());
				}
				controller.start(MyCrawler.class, numberOfCrawlers);
			} catch (Exception e) {
				e.printStackTrace();
			}
    
	    }
	   
	   
	    private List<ScrapeData> getUncrawledUrls() throws Exception{
	    	
//	    	ScrapeDataDao scrapeDao = ApplicationContextLoader.getContext().getBean(ScrapeDataDao.class);
	    	List<ScrapeData> list = scrapeDataDao.getUncrawled();
	    	return list;
		}

		@RequestMapping("/readurls")
	    public List<ScrapeData> readurls() {
//	    	ScrapeDataDao scrapeDao = ApplicationContextLoader.getContext().getBean(ScrapeDataDao.class);
			List<ScrapeData> list = scrapeDataDao.list();
			return list;
	    }

	    @RequestMapping("/getByurl")
	    public ScrapeData getByurl(@RequestParam(value="url", required=true) String url) {
			System.out.println("url::::::"+url);
			ScrapeData data = scrapeDataDao.getByurl(url);
			return data;
	    }
	    
	    
}
