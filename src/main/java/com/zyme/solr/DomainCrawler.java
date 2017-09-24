package com.zyme.solr;

import java.util.Set;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Component;

import com.zyme.controller.CrawllerController;
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

@Component
public class DomainCrawler extends WebCrawler{
	
//	@Autowired
	private ScrapeDataDao scrapeDataDao;

	public ScrapeDataDao getScrapeDataDao() {
		return scrapeDataDao;
	}

	public void setScrapeDataDao(ScrapeDataDao scrapeDataDao) {
		this.scrapeDataDao = scrapeDataDao;
	}

	@Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
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
			ScrapeData scrapeData = new ScrapeData();
			System.out.println("domain::"+(String)this.getMyController().getCustomData());
			System.out.println("domain::"+CrawllerController.domain);
			
			scrapeData.setCompany(CrawllerController.domain);
			scrapeData.setUrl(url);
			scrapeData.setContent(text);
			scrapeDataDao = (ScrapeDataDaoImpl) ApplicationContextProvider.getApplicationContext().getBean("scrapeDataDao");
			scrapeDataDao.save(scrapeData);
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
