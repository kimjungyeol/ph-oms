package com.ktnet.fta.simulation.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktnet.fta.common.web.BasicController;
import com.ktnet.fta.simulation.service.SimulationService;

import jakarta.annotation.Resource;

@RestController
public class SimulationController extends BasicController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "simulationService")
    private SimulationService simulationService;

    @PostMapping("/api/simulation")
    public void simulation() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("k", "v");

        Map<String, Object> ftaInfo = new HashMap<String, Object>();
        List<Map<String, Object>> itemList = new ArrayList<>();

        simulationService.simulationExecute(ftaInfo, itemList);
    }
}
