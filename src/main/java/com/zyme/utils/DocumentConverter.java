package com.zyme.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrDocument;

import com.zyme.model.ScrapeData;
import com.zyme.pojo.SearchResult;
import com.zyme.pojo.TermCount;

public class DocumentConverter {

	public static SearchResult getResultFromDocument(SolrDocument doc, String terms){
		doc.setField("content", "");
		System.out.println("Doc:::"+doc);
		SearchResult result = new SearchResult();
//		result.setId((String)doc.getFieldValue("id"));
//		result.setDomain((String)doc.getFieldValue("company"));
		result.setUrl((String)doc.getFieldValue("url"));
//		result.setPageContent((String)doc.getFieldValue("content"));

		List<TermCount> termCountList = new ArrayList<TermCount>();
		String[] termList = terms.split("\\s+");

		int count = 0;
		for(String term :  termList){
			TermCount termCount = new TermCount();
			termCount.setTerm(term);
			termCount.setCount((Integer)doc.getFieldValue(term+"_Count"));
			termCountList.add(termCount);
			count += termCount.getCount();
		}
		result.setCount(count);
		result.setTermcountlist(termCountList);
		return result;
	}

	public static SolrDocument getSolrDocument(ScrapeData scrapeData) {
		SolrDocument doc = new SolrDocument();
		doc.setField("id", scrapeData.getId());
		doc.setField("company", scrapeData.getCompany());
		doc.setField("url", scrapeData.getUrl());
		doc.setField("content", scrapeData.getContent());
		return doc;
	}
	
	
}
