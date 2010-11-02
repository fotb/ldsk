package com.noway.ldsk.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.noway.ldsk.dao.IDeviceControlActionDAO;
import com.noway.ldsk.vo.DeviceControlActionVO;
@SuppressWarnings("unchecked")
public class DeviceControlActionBOImpl implements IDeviceControlActionBO {
	private IDeviceControlActionDAO deviceControlActionDAO;

	public IDeviceControlActionDAO getDeviceControlActionDAO() {
		return deviceControlActionDAO;
	}

	public void setDeviceControlActionDAO(
			IDeviceControlActionDAO deviceControlActionDAO) {
		this.deviceControlActionDAO = deviceControlActionDAO;
	}

	public List findAll() {
		return deviceControlActionDAO.findAll();
	}

	public Map getDeviceControlActionWithActionCode() {
		Map map = new HashMap();
		final List list = deviceControlActionDAO.findAll();
		for (final Iterator iter = list.iterator(); iter.hasNext();) {
			final DeviceControlActionVO vo = (DeviceControlActionVO) iter.next();
			if (map.containsKey(vo.getActionCode())) {
				final List temp = (List) map.get(String.valueOf(vo.getActionCode().intValue()));
				temp.add(vo);
			} else {
				final List temp = new ArrayList();
				temp.add(vo);
				map.put(vo.getActionCode(), temp);
			}
		}
		return map;
	}

	public Map getHipsActionGroupByComputerIdn() {
		Map map = new HashMap();
		final List list = deviceControlActionDAO.findAll();
		for (final Iterator iter = list.iterator(); iter.hasNext();) {
			final DeviceControlActionVO vo = (DeviceControlActionVO) iter.next();
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

	@Override
	public int getCount() {
		return deviceControlActionDAO.getCount();
	}
	
}
