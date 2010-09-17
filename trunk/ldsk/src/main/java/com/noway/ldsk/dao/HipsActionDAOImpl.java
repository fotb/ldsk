package com.noway.ldsk.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
@SuppressWarnings("unchecked")
public class HipsActionDAOImpl extends HibernateDaoSupport implements
		IHipsActionDAO {

	public List findAll() {
		return getHibernateTemplate().find("from HipsActionVO order by computerIdn");
	}
}
