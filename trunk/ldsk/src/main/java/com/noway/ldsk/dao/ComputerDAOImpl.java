package com.noway.ldsk.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.noway.ldsk.vo.ComputerVO;
@SuppressWarnings("unchecked")
public class ComputerDAOImpl extends HibernateDaoSupport implements IComputerDAO {

	public int findAllCount() {
		Long count = (Long)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate( Session session) throws HibernateException,SQLException {
				Query query = session.createQuery("select count(*) from ComputerVO");
				return ((Long)query.iterate().next());
			}
		});
		return count.intValue();
	}

	public int findCountByModel(String model) {
		final List list = getHibernateTemplate().find("from ComputerVO where model like ?", "%" + model + "%");
		return list.size();
	}
	
	public List findAll() {
		return getHibernateTemplate().find("from ComputerVO");
	}

	public Map findAllToMap() {
		final List list = getHibernateTemplate().find("from ComputerVO");
		Map map = new HashMap();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			ComputerVO vo = (ComputerVO) iter.next();
			map.put(String.valueOf(vo.getComputerIdn().intValue()), vo);
		}
		return map;
	}
	
}
