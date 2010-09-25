package com.noway.ldsk.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.noway.ldsk.vo.UnmodeledDataVO;

public class UnmodeledDataDAOImpl extends HibernateDaoSupport implements
		IUnmodeledDataDAO {

	@SuppressWarnings("unchecked")
	public Map<String, String> findAllByMetaObjAttrRelationsIdn(String metaObjAttrRelationsIdn) {
		final List list = getHibernateTemplate().find(
				"from UnmodeledDataVO vo where vo.metaObjAttrRelationsIdn = ?",
				Integer.valueOf(metaObjAttrRelationsIdn));
		Map map = new HashMap();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			UnmodeledDataVO vo = (UnmodeledDataVO) iterator.next();
			map.put(String.valueOf(vo.getComputerIdn()), vo.getDataString());
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> findAllBranch(String metaObjAttrRelationsIdn) {
		final List<UnmodeledDataVO> list = getHibernateTemplate().find(
				"from UnmodeledDataVO vo where vo.metaObjAttrRelationsIdn = ?",
				Integer.valueOf(metaObjAttrRelationsIdn));
		Map map = new HashMap();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			UnmodeledDataVO vo = (UnmodeledDataVO) iterator.next();
			map.put(vo.getDataString(), vo.getDataString());
		}
		return map;
	}
}
