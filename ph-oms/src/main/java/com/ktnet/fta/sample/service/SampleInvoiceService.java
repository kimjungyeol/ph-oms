package com.ktnet.fta.sample.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ktnet.core.map.ParamMap;

@Service("sampleInvoiceService")
public class SampleInvoiceService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<Map<String, Object>> searchInvoiceList(ParamMap pMap) throws Exception {

        List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();

        HashMap<String, Object> paramMap = (HashMap<String, Object>) pMap.getMap();
        String perPage = paramMap.get("perPage") == null ? "10" : paramMap.get("perPage") + "";
        String page = paramMap.get("page") == null ? "1" : paramMap.get("page") + "";

        int end = (Integer.parseInt(perPage) * Integer.parseInt(page));
        int strt = (end - Integer.parseInt(perPage)) + 1;

        Map<String, Object> map = null;
        for (int i = strt; i <= end; i++) {
            map = new HashMap<String, Object>();
            map.put("totalCnt", 50);
            // map.put("rownum", i);
            map.put("invoiceNo", 10000 + i);
            map.put("invoiceDate", "20240423");
            map.put("ftaName", "한-미");
            map.put("status", "판정대기");
            // map.put("age", 10 + i);
            // map.put("email", "name" + i + "@gmail.com");
            // map.put("useYn", "Y");
            // map.put("nameEng", "name" + i + "_eng");
            // map.put("dateymd", "2014-04-16");
            rtList.add(map);
        }

        return rtList;
    }

    public int deleteBoardSample(Map<String, Object> map) {
        // TODO Auto-generated method stub
        return 0;
    }

    public Map<String, Object> searchInvoiceDetail(ParamMap pMap) {
        Map<String, Object> rtMap = new HashMap<String, Object>();

        Map<String, Object> invoiceInfoMap = new HashMap<String, Object>();
        List<Map<String, Object>> invoiceLineList = new ArrayList<Map<String, Object>>();

        invoiceInfoMap.put("invoiceNo", "aaaaa");
        invoiceInfoMap.put("invoiceDate", "2024-12-31");

        Map<String, Object> map = null;
        int i = 0;
        while (i < 10) {
            map = new HashMap<String, Object>();
            map.put("rowIdx", i);
            map.put("aaa", "11111");
            map.put("bbb", "22222");
            map.put("ccc", "33333");
            invoiceLineList.add(map);
            i++;
        }

        rtMap.put("invoiceInfoMap", invoiceInfoMap);
        rtMap.put("invoiceLineList", invoiceLineList);

        return rtMap;
    }

}
