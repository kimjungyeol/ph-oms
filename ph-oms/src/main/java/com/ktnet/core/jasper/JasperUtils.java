package com.ktnet.core.jasper;

import java.util.Map;
import java.util.Set;

import com.ktnet.common.dto.JasperParamDto;

public class JasperUtils {

	public static JasperParamDto convertKey(String fileName, String printFileName) {
		JasperParamDto jParam = new JasperParamDto(fileName, printFileName);
		return jParam;
	}
	
	public static JasperParamDto convertKey(String fileName, String printFileName, Map<String, Object> param) {
		JasperParamDto jParam = new JasperParamDto(fileName, printFileName);
		Set<String> keySet = param.keySet();
		
		for (String key : keySet) {
			jParam.addParam("p_" + key, param.get(key));
		}
		return jParam;
	}
}
