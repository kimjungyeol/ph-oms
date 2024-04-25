package com.ktnet.core.jasper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktnet.common.util.StringUtil;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class ViewDataSource implements JRDataSource {

	private Logger logger = LoggerFactory.getLogger(ViewDataSource.class);
	private List<Map<String, Object>> data = null;
	private int index = 0;
	private Map<String, Object> record = null;

	/** 
	 * Data Source에 Data를 지정한다.
	 * 
	 * @param dataList
	 */
	public ViewDataSource(List<Map<String, Object>> dataList) {
		this.data = dataList;
		
		//When creating jasper pdf, the reference is broken.
		//this.data = CommonUtil.cloneListMap(dataList);
	}

	/**
	 * 현재 record의 key(String arg0)의 값을 가져 온다.
	 * 
	 * @param arg0
	 * @return
	 * @throws JRException
	 */
	public Object getFieldValue(String arg0) throws JRException {
		if (record != null) {
			if (record.containsKey(arg0)) {
				return StringUtil.nvl(record.get(arg0), "");
			} else {
				return StringUtil.nvl(record.get(arg0.toLowerCase()), "");
			}
		} else {
			return "";
		}
	}

	/**
	 * 현재 record의 key(JRField arg0)의 값을 가져 온다.
	 * 
	 * @param arg0
	 * @return
	 * @throws JRException
	 */
	public Object getFieldValue(JRField arg0) throws JRException {

		if (record != null) {
			return record.get(arg0.getName());
		} else {
			return "";
		}
	}

	/**
	 * 현재 record를 돌려 준다.
	 * 
	 * @return
	 */
	public final Map<String, Object> getRecord() {
		Map<String, Object> mapClone = new HashMap<String, Object>(); 
    	mapClone.putAll(record);
    	
		return mapClone;
	}

	/**
	 * 다음 레코드로 이동하고 true 리턴 더 이동 할 곳이 없으면 false
	 */
	public boolean next() throws JRException {
		if (data == null || data.size() < index) {
			return false;
		}
		try {
			record = data.get(index++);
			return true;
		} catch (IndexOutOfBoundsException _ignore) {
			return false;
		} catch (Exception e) {
			logger.error("{}  is only support MAP !!!", this.getClass());
			return false;
		}
	}

	public int size() {
		if (data != null) {
			return data.size();
		} else {
			logger.debug("Data is Null !!!");
			return 0;
		}
	}
}