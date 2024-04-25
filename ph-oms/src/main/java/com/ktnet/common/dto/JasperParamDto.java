package com.ktnet.common.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ktnet.common.util.CommonUtil;

public class JasperParamDto {
	private List<Map<String, Object>> list;
    private List<Map<String, Object>> subDS;
    private Map<String, Object> param;
    
    public JasperParamDto( String jasperFileName ) {
        param = new HashMap<String, Object>();
        this.jasperFileName = jasperFileName;
    }
    public JasperParamDto(String jasperFileName, String printFileName){
    	param = new HashMap<String, Object>();
    	this.jasperFileName = jasperFileName;
    	this.printFileName = printFileName;
    }
    
    private String jasperFileName;
    private String printFileName;
    /**
	 * @return the printFileName
	 */
	public String getPrintFileName() {
		return printFileName;
	}
	/**
	 * @param printFileName the printFileName to set
	 */
	public void setPrintFileName(String printFileName) {
		this.printFileName = printFileName;
	}
	/**
     * @return the list
     */
    public List<Map<String, Object>> getList() {
        return list;
        
        //When creating jasper pdf, the reference is broken.
        //return CommonUtil.cloneListMap(list);   
    }
    /**
     * @param list the list to set
     */
    public void setList(List<Map<String, Object>> list) {
        this.list = CommonUtil.cloneListMap(list);
    }
    
    /**
     * @return the list
     */
	public List<Map<String, Object>> getSubDS() {
        return CommonUtil.cloneListMap(subDS);
    }
    /**
     * @return the list
     */
    public boolean hasSubDS() {
        return (subDS == null) ? false : true;
    }
    /**
     * @param list the list to set
     */
    public void setSubDS(List<Map<String, Object>> list) {
        this.subDS = CommonUtil.cloneListMap(list);
    }
    /**
     * @return the jasperFileName
     */
    public String getJasperFileName() {
        return jasperFileName;
    }
    /**
     * @param jasperFileName the jasperFileName to set
     */
    public void setJasperFileName(String jasperFileName) {
        this.jasperFileName = jasperFileName;
    }
    
    public void addParam(String key, Object value) {
        param.put(key, value);
    }
    
    public void setParam(Map<String, Object> param) {
        this.param.putAll(param);
    }
    
    public Map<String, Object> getParamMap() {
    	Map<String, Object> mapClone = new HashMap<String, Object>(); 
    	mapClone.putAll(this.param);
    	
        return mapClone;
    }
    
}
