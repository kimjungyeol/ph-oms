package com.ktnet.fta.judgment.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ktnet.common.response.ApiResponse;
import com.ktnet.fta.common.web.BasicController;
import com.ktnet.fta.judgment.service.JudgmentService;
import com.ktnet.fta.simulation.service.SimulationService;

import jakarta.annotation.Resource;

@RestController
public class JudgmentController extends BasicController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "judgmentService")
    private JudgmentService judgmentService;

    @Resource(name = "simulationService")
    private SimulationService simulationService;

    @GetMapping("/api/get/test")
    public String getTest() {
        return "api get teset";
    }

    @PostMapping("/api/post/test")
    // public String test(@RequestBody Map<String, Object> reqMap) {
    public ApiResponse<?> test(@RequestBody Map<String, Object> reqMap) {

        logger.debug(reqMap.toString());

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("resultMap", "SU");

        return new ApiResponse<>(resultMap, 1, "SUCCESS");

        // return "SSSSS";
    }

//    @PostMapping("/api/post/test")
//    public String test(@RequestBody Map<String, Object> reqMap) {
//
//        logger.debug(reqMap.toString());
//
//        String result = judgmentService.test();
//        Map<String, String> resultMap = new HashMap<String, String>();
//        resultMap.put("result", result);
//
//        return "SUCCESS";
//    }

    @PostMapping("/api/co/judgment")
    public void judgment(@RequestBody Map<String, Object> reqMap) {
        // Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("k", "v");

        judgmentService.judgmentExecute(reqMap);

        // Invoice No 기준으로 판정
        List<String> invoiceNos = (List<String>) reqMap.get("invoiceNos");

        for (String invoiceNo : invoiceNos) {
            // 1. 품목명세 생성
            // 2. BOM명세 생성
            // 3. 자재명세 생성
            // 4. 구매명세 생성
            // 5. 소명서 생성
            // 6. 원산지 판정
            // 7. 판정결과 반영

            // Invoice No 기준으로 판정
            // judgmentService.judgmentExecute(reqMap);
        }
    }
}
