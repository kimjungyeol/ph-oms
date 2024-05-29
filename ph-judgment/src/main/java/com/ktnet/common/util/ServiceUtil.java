package com.ktnet.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Toast Grid DATASET.
 * 	createdRows, updatedRows, deletedRows
 */
@SuppressWarnings("unchecked")
public class ServiceUtil {
	
	public static ArrayList<Map<String, Object>> getGridInsertData(Map<String, Object> map) {
		HashMap<String, Object> data = (HashMap<String, Object>) map.get("data");
        return (ArrayList<Map<String, Object>>) data.get("createdRows");
	}
	public static ArrayList<Map<String, Object>> getGridUpdateData(Map<String, Object> map) {
		HashMap<String, Object> data = (HashMap<String, Object>) map.get("data");
		return (ArrayList<Map<String, Object>>) data.get("updatedRows");
	}
	public static ArrayList<Map<String, Object>> getGridDeleteData(Map<String, Object> map) {
		HashMap<String, Object> data = (HashMap<String, Object>) map.get("data");
		return (ArrayList<Map<String, Object>>) data.get("deletedRows");
	}
}
