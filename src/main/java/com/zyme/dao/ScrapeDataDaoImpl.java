package com.zyme.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.zyme.model.ScrapeData;

@Component
public class ScrapeDataDaoImpl implements ScrapeDataDao {

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	public List<ScrapeData> saveList(List<ScrapeData> sdList) {
		for(ScrapeData sd : sdList){
			save(sd);
		}
		return sdList;
	}

	public ScrapeData save(ScrapeData sd) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(sd);
		tx.commit();
		session.close();
		return sd;
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

	public ScrapeData readById(int id) {

		Session session = this.sessionFactory.openSession();
		ScrapeData scrapedata = (ScrapeData) session.createQuery("from ScrapeData where id="+"'"+id+"'").uniqueResult();
		session.close();
		return scrapedata;
	}

	public List<ScrapeData> readByDomain(String domain) {

		Session session = this.sessionFactory.openSession();
		List<ScrapeData> scrapeList = (List<ScrapeData>)session.createQuery("from ScrapeData where company="+"'"+domain+"'").list();
		session.close();
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
