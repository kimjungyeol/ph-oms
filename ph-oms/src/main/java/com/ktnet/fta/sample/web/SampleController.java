package com.ktnet.fta.sample.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktnet.common.exception.BizzException;
import com.ktnet.core.map.ParamMap;
import com.ktnet.fta.common.scheduler.TestDynamicScheduler;
import com.ktnet.fta.sample.service.SampleService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SampleController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "sampleService")
    private SampleService sampleService;

    @Autowired
    public TestDynamicScheduler testDynamicScheduler;

    @GetMapping("/sample/search")
    public String searchSample(HttpServletRequest req, Model model, ParamMap pMap) throws Exception {
        logger.debug("searchSample");
        logger.debug("pMap => {}", pMap.toString());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pgStart", 1);
        map.put("pgLimit", 10);

        try {
            List<Map<String, Object>> rtList = sampleService.searchSampleList(map);

            logger.debug("rtMap", rtList.toString());

            model.addAttribute("map", map);
            model.addAttribute("dataList", rtList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "sample/main";
    }

    @GetMapping("/sample/search-one")
    public String searchSampleOne(HttpServletRequest req, Model model) throws Exception {
        logger.debug("searchSampleOne");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "name1");

        Map<String, Object> rtMap = sampleService.searchSample(map);
        rtMap.put("test", "test11");

        logger.debug("rtMap", rtMap.toString());

        model.addAttribute("map", map);
        model.addAttribute("dataMap", rtMap);

        return "sample/main";
    }

    @RequestMapping(value = "/sample/search-map", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody Map<String, Object> sampleSearchMap(HttpServletRequest req, Model model) throws Exception {
        logger.debug("sampleSearchMap");

        Map<String, Object> rtMap = new HashMap<String, Object>();
        rtMap.put("test", "<script>alert(1);</script>");
        rtMap.put("script", "<script></script>");

        return rtMap;
    }

    @SuppressWarnings("null")
    @GetMapping("/sample/error")
    public String error(HttpServletRequest req, Model model) throws Exception {
        logger.debug("errtest");

        // error
        try {
            String aa = null;
            logger.debug(aa.toString());
        } catch (Exception e) {
            throw new BizzException("This is Error Test.");
        }

        return "sample/main";
    }

    @GetMapping("/sample/schedule")
    public String schedule(HttpServletRequest req, Model model) throws Exception {
        logger.debug("schedule");

        testDynamicScheduler.startScheduler();

        return "sample/main";
    }
}