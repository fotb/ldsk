package com.noway.ldsk.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
@SuppressWarnings("unchecked")
public class DeviceControlActionDAOImpl extends HibernateDaoSupport implements
		IDeviceControlActionDAO {

	public List findAll() {
		return getHibernateTemplate().find("from DeviceControlActionVO order by computerIdn");
	}
	@Override
	public int getCount() {
//		Integer count = (Integer) this.getHibernateTemplate().execute(
//				new HibernateCallback() {
//					public Object doInHibernate(Session session)
//							throws HibernateException {
//						Query query = session.createQuery("select count(computerIdn) from DeviceControlActionVO");
//						return query
//					}
//				});
//		return count;
		
		   List result = getHibernateTemplate().find("select count(computerIdn) from DeviceControlActionVO");
		   return ((Long)result.get(0)).intValue();

	}
}
