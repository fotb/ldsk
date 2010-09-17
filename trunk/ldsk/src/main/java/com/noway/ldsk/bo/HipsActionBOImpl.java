package com.noway.ldsk.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.noway.ldsk.dao.IHipsActionDAO;
import com.noway.ldsk.vo.HipsActionVO;
@SuppressWarnings("unchecked")
public class HipsActionBOImpl implements IHipsActionBO {
	private IHipsActionDAO hipsActionDAO;

	public IHipsActionDAO getHipsActionDAO() {
		return hipsActionDAO;
	}

	public void setHipsActionDAO(IHipsActionDAO hipsActionDAO) {
		this.hipsActionDAO = hipsActionDAO;
	}

	public List findAll() {
		return hipsActionDAO.findAll();
	}

	public Map getHipsActionGroupByComputerIdn() {
		Map map = new HashMap();
		final List list = hipsActionDAO.findAll();
		for (final Iterator iter = list.iterator(); iter.hasNext();) {
			final HipsActionVO vo = (HipsActionVO) iter.next();
			if (map.containsKey(vo.getComputerIdn())) {
				final List temp = (List) map.get(vo.getComputerIdn());
				temp.add(vo);
			} else {
				List temp = new ArrayList();
				temp.add(vo);
				map.put(vo.getComputerIdn(), temp);
			}
		}
		return map;
	}

	public Map getHipsActionWithActionCode() {
		Map map = new HashMap();
		final List list = hipsActionDAO.findAll();
		for (final Iterator iter = list.iterator(); iter.hasNext();) {
			final HipsActionVO vo = (HipsActionVO) iter.next();
			if (map.containsKey(vo.getActionCode())) {
				final List temp = (List) map.get(String.valueOf(vo.getActionCode().intValue()));
				temp.add(vo);
			} else {
				List temp = new ArrayList();
				temp.add(vo);
				map.put(vo.getActionCode(), temp);
			}
		}
		return map;
	}
}
