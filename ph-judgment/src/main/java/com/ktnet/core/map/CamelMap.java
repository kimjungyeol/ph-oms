package com.ktnet.core.map;

import java.util.HashMap;

import org.springframework.jdbc.support.JdbcUtils;

public class CamelMap extends HashMap<Object, Object> {
	
	private static final long serialVersionUID = 8188697549455013825L;

	@Override
	public Object put( Object key, Object value ) {
		return super.put( JdbcUtils.convertUnderscoreNameToPropertyName( (String) key), value );
	}

}