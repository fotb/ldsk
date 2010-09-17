package com.noway.ldsk.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.noway.ldsk.vo.TcpVO;

public class TcpDAOImpl extends HibernateDaoSupport implements ITcpDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.report.dao.ITcpDAO#findAll()
	 */
	@SuppressWarnings("unchecked")
	public Map findAll() {
		final List list = getHibernateTemplate().find("from TcpVO");
		Map map = new HashMap();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TcpVO vo = (TcpVO) iterator.next();
			map.put(String.valueOf(vo.getComputerIdn()), vo);
		}
		return map;
	}

}
