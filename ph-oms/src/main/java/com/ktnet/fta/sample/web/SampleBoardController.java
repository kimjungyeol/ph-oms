package com.ktnet.fta.sample.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktnet.common.dto.ResultResponseDto;
import com.ktnet.common.dto.ToastUiResponseDto;
import com.ktnet.core.map.ParamMap;
import com.ktnet.fta.common.web.BasicController;
import com.ktnet.fta.sample.service.SampleBoardService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SampleBoardController extends BasicController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "sampleBoardService")
    private SampleBoardService sampleBoardService;

    @RequestMapping(value = "/sample/board/list/search", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity<ToastUiResponseDto> sampleBoardListSearch(HttpServletRequest req, Model model, ParamMap pMap)
            throws Exception {
        logger.debug(">>sampleBoardListSearch<<");
        logger.debug("pMap => {}", pMap.getMap().toString());

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
            map.put("rownum", i);
            map.put("title", "title title title" + i);
            map.put("contents", "contents contents contents contents" + i);
            map.put("name", "name" + i);
            map.put("age", 10 + i);
            map.put("email", "name" + i + "@gmail.com");
            map.put("useYn", "Y");
            map.put("nameEng", "name" + i + "_eng");
            rtList.add(map);
        }

        return new ResponseEntity<>(ToastUiResponse(rtList, pMap.getMap()), HttpStatus.OK);
    }

    @RequestMapping(value = "/sample/board/list/save", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody Map<String, Object> sampleBoardListSave(HttpServletRequest req, Model model, ParamMap pMap)
            throws Exception {
        logger.debug(">> sampleBoardListSave <<");

        int result = sampleBoardService.saveBoardSample(pMap.getMap());

        Map<String, Object> rtMap = new HashMap<String, Object>();
        rtMap.put("data", "");
        rtMap.put("result", result);

        return rtMap;
    }

    @RequestMapping(value = "/sample/board/list2/search", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity<ToastUiResponseDto> sampleBoardList2Search(HttpServletRequest req, Model model, ParamMap pMap)
            throws Exception {
        logger.debug(">> sampleBoardList2Search <<");
        logger.debug("pMap => {}", pMap.getMap().toString());

        List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();

        HashMap<String, Object> paramMap = (HashMap<String, Object>) pMap.getMap();
        String perPage = paramMap.get("perPage") == null ? "10" : paramMap.get("perPage") + "";
        String page = paramMap.get("page") == null ? "1" : paramMap.get("page") + "";

        int end = (Integer.parseInt(perPage) * Integer.parseInt(page));
        int strt = (end - Integer.parseInt(perPage)) + 1;

        Map<String, Object> map = null;
        for (int i = strt; i <= end; i++) {
            map = new HashMap<String, Object>();
            map.put("totalCnt", 100);
            map.put("rownum", i);
            map.put("name", "_name" + i);
            map.put("age", 10 + i);
            map.put("email", "_name" + i + "@gmail.com");
            map.put("useYn", "Y");
            map.put("nameEng", "_name" + i + "_eng");
            rtList.add(map);
        }

        return new ResponseEntity<>(ToastUiResponse(rtList, pMap.getMap()), HttpStatus.OK);
    }

    @RequestMapping(value = "/sample/board/register/search", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity<ResultResponseDto> sampleBoardRegisterSearch(HttpServletRequest req, Model model,
            ParamMap pMap) throws Exception {
        logger.debug(">> sampleBoardRegisterSearch <<");
        logger.debug("pMap => {}", pMap.getMap().toString());

        HashMap<String, Object> resultMap = (HashMap<String, Object>) pMap.getMap();
        if (!resultMap.isEmpty()) {
            resultMap.put("use_select", "Y");
            resultMap.put("registerId", "master");
            resultMap.put("registerDt", "9999/01/01");
        }

        return new ResponseEntity<>(ResultResponse(resultMap), HttpStatus.OK);
    }

    @RequestMapping(value = "/sample/board/register/save", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity<ResultResponseDto> sampleBoardRegisterSave(HttpServletRequest req, Model model, ParamMap pMap)
            throws Exception {
        logger.debug(">> sampleBoardRegisterSearch <<");
        logger.debug("pMap => {}", pMap.getMap().toString());

        boolean result = true;

        return new ResponseEntity<>(ResultResponse(result), HttpStatus.OK);
    }
}