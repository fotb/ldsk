package com.noway.ldsk.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.noway.ldsk.vo.CompSystemVO;
@SuppressWarnings("unchecked")
public class CompSystemDAOImpl extends HibernateDaoSupport implements
		ICompSystemDAO {

	public Map findAllToMap() {
		final List list = getHibernateTemplate().find("from CompSystemVO");
		Map map = new HashMap();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			CompSystemVO vo = (CompSystemVO) iterator.next();
			map.put(String.valueOf(vo.getComputerIdn()), vo);
		}
		return map;
	}

	public int findCountByModel(String model) {
		final List list = getHibernateTemplate().find(
				"from CompSystemVO where manufacturer like ?", "%" + model + "%");
		return list.size();
	}

}
