package com.zyme.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.zyme.model.ScrapeData;

public class ScrapeDataDaoImpl implements ScrapeDataDao {

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	public void save(ScrapeData sd) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
//		session.persist(sd);
		session.saveOrUpdate(sd);
		tx.commit();
		session.close();
		
	}

	public List<ScrapeData> list() {

		Session session = this.sessionFactory.openSession();
		List<ScrapeData> scrapeList = session.createQuery("from ScrapeData").list();
		session.close();
		for(ScrapeData s : scrapeList){
			s.setContent("");
		}
		return scrapeList;
	}

	public List<ScrapeData> getUncrawled() {

		Session session = this.sessionFactory.openSession();
		List<ScrapeData> scrapeList = session.createQuery("from ScrapeData where content is null or content="+"''").list();
		session.close();
		for(ScrapeData s : scrapeList){
			s.setContent("");
		}
		return scrapeList;
	}

	public ScrapeData getByurl(String url) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from ScrapeData where url = "+"'"+url+"'");
//		Query query = session.createQuery("from ScrapeData where url = "+"'https://www.amazon.in/s/ref=a9_sc_1?rh=i%3Aaps%2Ck%3Arouter&keywords=router&ie=UTF8&qid=1505044905'");

		List<ScrapeData> list = query.list();
		System.out.println("list::===========" +list.size());
		for(ScrapeData s : list){
			System.out.println("ScrapeData List::"+s.toString());
		}
		session.close();
	
		if(null!=list && !list.isEmpty()){
			ScrapeData scrapedata = list.get(0);
			return scrapedata;
		}
		
		return null;
	}

}
