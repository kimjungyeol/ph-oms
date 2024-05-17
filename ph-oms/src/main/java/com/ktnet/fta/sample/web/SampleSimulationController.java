package com.ktnet.fta.sample.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktnet.core.map.ParamMap;
import com.ktnet.fta.common.web.BasicController;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SampleSimulationController extends BasicController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/sample/judgment/simulation", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody Map<String, Object> simulation(HttpServletRequest req, Model model, ParamMap pMap)
            throws Exception {
        logger.debug(">> simulation <<");

        logger.debug(">> pMap <<", pMap);

        Map<String, Object> rtMap = new HashMap<String, Object>();

        return rtMap;
    }
}
