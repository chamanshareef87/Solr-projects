package com.zyme.solr;

import java.util.Set;

import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zyme.dao.ScrapeDataDao;
import com.zyme.model.ScrapeData;
import com.zyme.service.SolrSearchService;
import com.zyme.utils.DocumentConverter;
import com.zyme.utils.SolrUtil;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler{
//	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
//            + "|png|mp3|mp3|zip|gz))$");
	
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
			
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

			ScrapeDataDao scrapeDao = context.getBean(ScrapeDataDao.class);
			System.out.println("url at save to db:==========="+url);
			ScrapeData scrapeData = scrapeDao.getByurl(url);
			if (null!=scrapeData) {
//				System.out.println("============================");
//				System.out.println("scrapeData::"+scrapeData.toString());
//				System.out.println("============================");
				scrapeData.setContent(text);
				scrapeDao.save(scrapeData);
			}
			context.close();	
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
