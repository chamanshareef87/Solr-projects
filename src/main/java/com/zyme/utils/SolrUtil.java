package com.zyme.utils;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public class SolrUtil {

	static HttpSolrClient server = null;
    
    public static HttpSolrClient getSolrServer()
    {
    	if(server==null)
//        server = new HttpSolrClient.Builder("http://127.0.0.1:8983/solr/scraperdata").build();
        server = new HttpSolrClient.Builder("http://localhost:8983/solr/scraperdata").build();

    	return server;
    }

	public SolrQuery getSolrQuery(String domain, String terms) {
		SolrQuery query = null;
		try {

			query = new SolrQuery();
//			query.setQuery("company:"+domain);
//			query.set("q", "company:"+domain);
			query.setQuery("content:"+terms);
			query.set("q", "content:"+terms);

			String[] termList = terms.split("\\s+");

//			query.setFilterQueries("company:"+domain);
//			query.setFields("id","company","url","content", "Terms-aaa:termfreq(\"content\",\"aaa\")","Terms-bbb:termfreq(\"content\",\"bbb\")");
//			query.addField("id");
//			query.addField("company");
			query.addField("url");
//			query.addField("content");
//			query.addField(terms+":termfreq(\"content\",\""+terms+"\")");

			for(String term : termList){
				query.addField(term+"_Count"+":termfreq(\"content\",\""+term+"\")");
			}
			
//			query.addSort("termfreq(\"content\",\"aaa\")", ORDER.desc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return query;

	}

	public SolrDocumentList getSolrDocs(HttpSolrClient server, SolrQuery query) {
		QueryResponse response;
		SolrDocumentList list = null;
		try {
			response = server.query(query);
			list = response.getResults();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
    
	  public static SolrInputDocument toSolrInputDocument( SolrDocument d )
	  {
	    SolrInputDocument doc = new SolrInputDocument();
	    for( String name : d.getFieldNames() ) {
	      doc.addField( name, d.getFieldValue(name), 1.0f );
	    }
	    return doc;
	  }

}
