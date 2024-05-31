package com.ktnet.fta.judgment.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ktnet.fta.common.web.BasicController;
import com.ktnet.fta.judgment.service.JudgmentService;

@RestController
public class JudgmentController extends BasicController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    JudgmentService judgmentService;

    @GetMapping("/api/get/test")
    public String getTest() {
        return "api get teset";
    }

    @PostMapping("/api/post/test")
    public ResponseEntity<String> test(@RequestBody Map<String, Object> reqMap) {

        logger.debug(reqMap.toString());

        String result = judgmentService.test();
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("result", result);

        return ResponseEntity.ok("SUCCESS..!");
    }

    @PostMapping("/api/simulation")
    public void simulation() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        Map<String, Object> ftaInfo = new HashMap<String, Object>();
        List<Map<String, Object>> itemList = new ArrayList<>();

        reqMap.put("k", "v");
        judgmentService.simulationExecute(ftaInfo, itemList);
    }

    @PostMapping("/api/judgment")
    public void judgment() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("k", "v");
        judgmentService.judgmentExecute(reqMap);
    }
}
