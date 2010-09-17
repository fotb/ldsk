package com.noway.ldsk.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.noway.ldsk.vo.NetworkSoftwareVO;

public class NetworkSoftwareDAOImpl extends HibernateDaoSupport implements
		INetworkSoftwareDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.report.dao.INetworkSoftwareDAO#findAll()
	 */
	@SuppressWarnings("unchecked")
	public Map findAll() {
		final List list = getHibernateTemplate().find("from NetworkSoftwareVO");
		Map map = new HashMap();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			NetworkSoftwareVO vo = (NetworkSoftwareVO) iterator.next();
			map.put(String.valueOf(vo.getComputerIdn()), vo.getNicAddress());
		}
		return map;
	}

}
