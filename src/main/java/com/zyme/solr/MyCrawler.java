package com.zyme.solr;

import java.util.Set;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.zyme.dao.ScrapeDataDao;
import com.zyme.dao.ScrapeDataDaoImpl;
import com.zyme.model.ScrapeData;
import com.zyme.service.SolrSearchService;
import com.zyme.utils.DocumentConverter;
import com.zyme.utils.SolrUtil;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

//@Component
public class MyCrawler extends WebCrawler{

//	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
//  + "|png|mp3|mp3|zip|gz))$");
//	@Autowired
	private ScrapeDataDao scrapeDataDao;

	public ScrapeDataDao getScrapeDataDao() {
		return scrapeDataDao;
	}

	public void setScrapeDataDao(ScrapeDataDao scrapeDataDao) {
		this.scrapeDataDao = scrapeDataDao;
	}
	
	private static int pageNo=1;
	@Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
//		System.out.println("url:::::"+url);
//		System.out.println("referringPage:::::"+referringPage);
//        String href = url.getURL().toLowerCase();
//        System.out.println("href:::"+href);
//        return !FILTERS.matcher(href).matches()
//               && href.startsWith("http://www.zyme.com/");
		return true;
    }
	
	@Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        System.out.println("URL===================" + url);
        System.out.println("================================");
        
        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();
            savedata(text, html,url);
            System.out.println("Text length: " + text.length());
            System.out.println("Html length: " + html.length());
            System.out.println("Number of outgoing links: " + links.size());
        }
   }

	private void savedata(String text, String html, String url) {
		try {
			
			System.out.println("url at save to db:==========="+url);
			scrapeDataDao = (ScrapeDataDaoImpl) ApplicationContextProvider.getApplicationContext().getBean("scrapeDataDao");
	        System.out.println("scrapeDataDao::"+scrapeDataDao);
			ScrapeData scrapeData = scrapeDataDao.getByurl(url);
			System.out.println("scrapeData::"+scrapeData.toString());
			if (null!=scrapeData) {
				scrapeData.setContent(text);
				scrapeDataDao.save(scrapeData);
			}
			if (null!=scrapeData) {
				savetosolr(scrapeData);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void savetosolr(ScrapeData scrapeData) {
		SolrSearchService solrService = new SolrSearchService();
		try {
			SolrDocument document = DocumentConverter.getSolrDocument(scrapeData);
			SolrInputDocument inputDocument = SolrUtil.toSolrInputDocument(document);

			solrService.saveDocument(inputDocument);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
