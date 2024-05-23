package com.ktnet.fta.sample.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktnet.core.map.ParamMap;
import com.ktnet.fta.common.web.BasicController;
import com.ktnet.fta.sample.simualtion.SimulationExecutor;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SampleSimulationController extends BasicController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    SimulationExecutor simulationExecutor;

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/sample/simulation", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody Map<String, Object> simulation(HttpServletRequest req, Model model, ParamMap pMap)
            throws Exception {
        logger.debug(">> simulation <<");

        Map<String, Object> rtMap = new HashMap<String, Object>();
        boolean result = false;

        Map<String, Object> ftaInfo = (Map<String, Object>) pMap.get("formData");
        List<Map<String, Object>> itemList = (List<Map<String, Object>>) pMap.get("rowData");

        result = simulationExecutor.execute(ftaInfo, itemList);

        rtMap.put("result", result);

        return rtMap;
    }

    public void judgment(Map<String, Object> ftaInfo, List<Map<String, Object>> mrtList) {
        boolean result = false;

        if ("CTH".equals(ftaInfo.get("psrStandard"))) {

        }

        if ("RCV".equals(ftaInfo.get("psrStandard"))) {

        }

        // 0. 판정용 데이터 생성

        // 1. 세번변경 기준 판정
        // 1.1 기준별 세번 일치건수 계산
        // 1.2 CC 기준
        // 1.3 CTH 기준
        // 1.4 CTSH 기준

        // 2. 미소기준 판정

        // 3. 부가가치 기준 판정
        // 3.1 BD
        // 3.2 BU
        // 3.3 NC
        // 3.4 MC

    }

    // public void
}
