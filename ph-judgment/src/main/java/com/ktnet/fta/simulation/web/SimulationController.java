package com.ktnet.fta.simulation.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import com.ktnet.fta.common.web.BasicController;
import com.ktnet.fta.simulation.service.SimulationService;

import jakarta.annotation.Resource;

@RestController
public class SimulationController extends BasicController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "simulationService")
    private SimulationService simulationService;

}
