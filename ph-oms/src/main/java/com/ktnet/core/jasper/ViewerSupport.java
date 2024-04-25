package com.ktnet.core.jasper;

import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

public class ViewerSupport {
	public static final String PARAM_KEY = ViewerSupport.class + ".PARAM_KEY";
	public static final String DATA_SOURCE_KEY = ViewerSupport.class + ".DATA_SOURCE_KEY";
	public static final String XML_DOC_KEY = ViewerSupport.class + ".XML_DOC_KEY";
	public static final String JSON_KEY = ViewerSupport.class + ".JSON_KEY";

	/**
	 * return param Map to JaperView
	 * 
	 * @param request
	 * @param param
	 */
	public static void setPapameterMap(HttpServletRequest request, Map<String, Object> param) {
		request.setAttribute(ViewerSupport.PARAM_KEY, param);
	}

	/**
	 * return data List to JasperPDFVIewer.
	 * 
	 * @param request
	 * @param data
	 */
	public static void setDataSourceList(HttpServletRequest request, List<Map<String, Object>> data) {
		request.setAttribute(ViewerSupport.DATA_SOURCE_KEY, data);
	}
}
