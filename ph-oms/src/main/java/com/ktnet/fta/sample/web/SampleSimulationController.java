package com.ktnet.fta.sample.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    @RequestMapping(value = "/sample/judgment", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody Map<String, Object> judgment(HttpServletRequest req, Model model, ParamMap pMap)
            throws Exception {
        logger.debug(">> judgment <<");

        Map<String, Object> rtMap = new HashMap<String, Object>();

        RestClient restClient = RestClient.create();

        // String result =
        // restClient.get().uri("http://localhost:8090/api/get/test").retrieve().body(String.class);

        ObjectMapper mapper = new ObjectMapper();

        System.out.println("pMap : " + mapper.writeValueAsString(pMap));

        ResponseEntity<Void> result = restClient.post().uri("http://localhost:8090/api/post/test")
                .contentType(MediaType.APPLICATION_JSON).body(mapper.writeValueAsString(pMap)).retrieve()
                .toBodilessEntity();

        System.out.println("result : " + result.getStatusCode());

        rtMap.put("result", result.getStatusCode());

        //////////////////////////////////////////////////////////
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject("http://localhost:8090/api/post/test",
                mapper.writeValueAsString(pMap), String.class);
        System.out.println("response : " + response);

        return rtMap;
    }

}
