package com.zyme.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zyme.pojo.SearchResult;
import com.zyme.service.SolrSearchService;

@RestController
public class SolrController {
	
	@RequestMapping("/getResults")
	public List<SearchResult> getSearchResult(
			@RequestParam(value = "terms") String terms
			) {	
		String domain = "";
		System.out.println("get results at controller"+domain+terms);
		SolrSearchService solrSearchService = new SolrSearchService();
		List<SearchResult> results = solrSearchService.getResults(domain, terms );
		
		return results;
	}
	
	@RequestMapping("/getResultsByDomain")
	public List<SearchResult> getResultsByDomain(
			@RequestParam(value = "domain") String domain,
			@RequestParam(value = "terms") String terms
			) {	
		System.out.println("get results at controller"+domain+terms);
		SolrSearchService solrSearchService = new SolrSearchService();
		List<SearchResult> results = solrSearchService.getResultsByDomain(domain, terms );
		
		return results;
	}
}
