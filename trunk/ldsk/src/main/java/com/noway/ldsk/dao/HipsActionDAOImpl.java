package com.noway.ldsk.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@SuppressWarnings("unchecked")
public class HipsActionDAOImpl extends HibernateDaoSupport implements
		IHipsActionDAO {

	public List findAll() {
		return getHibernateTemplate().find(
				"from HipsActionVO order by computerIdn");
	}

	@Override
	public int getCount() {
//		Integer count = (Integer) this.getHibernateTemplate().execute(
//				new HibernateCallback() {
//					public Object doInHibernate(Session session)
//							throws HibernateException {
//						Query query = session.createQuery("select count(computerIdn) from HipsActionVO");
//						return query.list().size();
//					}
//				});
//		return count;
		   List result = getHibernateTemplate().find("select count(computerIdn) from HipsActionVO");
		   return ((Long)result.get(0)).intValue();
	}
}
