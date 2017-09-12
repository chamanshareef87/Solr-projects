package com.zyme.solr;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zyme.pojo.SearchResult;
import com.zyme.service.SolrSearchService;

@RestController
public class SolrClientController {
	
	@RequestMapping("/getResults")
	public List<SearchResult> getSearchResult(
//			@RequestParam(value = "domain") String domain,
			@RequestParam(value = "terms") String terms
			) {	
		String domain = "";
		System.out.println("get results at controller"+domain+terms);
		SolrSearchService solrSearchService = new SolrSearchService();
		List<SearchResult> results = solrSearchService.getResults(domain, terms );
		
		return results;
	}
}
