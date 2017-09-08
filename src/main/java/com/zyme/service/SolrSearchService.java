package com.zyme.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Service;

import com.zyme.pojo.SearchResult;
import com.zyme.utils.DocumentConverter;
import com.zyme.utils.SolrUtil;

@Service
public class SolrSearchService {

	private SolrUtil solrutil = new SolrUtil();
	public List<SearchResult> getResults(String domain, String terms) {
		List<SearchResult> results = new ArrayList<SearchResult>();
		System.out.println("get results at service");
		
		String[] termList = terms.split("\\s+");
		HttpSolrClient server = SolrUtil.getSolrServer();
		SolrQuery query = solrutil.getSolrQuery(domain, termList);

		SolrDocumentList docsList = solrutil.getSolrDocs(server, query);
		results = getResultFromSolr(docsList, termList);
		
		return results;
		
	}
	
	private List<SearchResult> getResultFromSolr(SolrDocumentList docsList, String[] termList) {
		List<SearchResult> results = new ArrayList<SearchResult>();
		for(SolrDocument doc : docsList){
			SearchResult result = DocumentConverter.getResultFromDocument(doc, termList);
			results.add(result);
		}
		return results;
	}

}
