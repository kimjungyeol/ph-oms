package com.ktnet.fta.sample.web;

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

import com.ktnet.common.dto.ResultResponseDto;
import com.ktnet.common.dto.ToastUiResponseDto;
import com.ktnet.core.map.ParamMap;
import com.ktnet.fta.common.web.BasicController;
import com.ktnet.fta.sample.service.SampleInvoiceService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SampleInvoiceController extends BasicController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "sampleInvoiceService")
    private SampleInvoiceService sampleInvoiceService;

    @RequestMapping(value = "/sample/invoice/list/search", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity<ToastUiResponseDto> sampleInvoiceListSearch(HttpServletRequest req, Model model,
            ParamMap pMap) throws Exception {
        logger.debug(">>sampleInvoiceListSearch<<");
        logger.debug("pMap => {}", pMap.getMap().toString());

        List<Map<String, Object>> rtList = sampleInvoiceService.searchInvoiceList(pMap);

        return new ResponseEntity<>(ToastUiResponse(rtList, pMap.getMap()), HttpStatus.OK);
    }

    @RequestMapping(value = "/sample/invoice/list/delete", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity<ResultResponseDto> sampleInvoiceListDelete(HttpServletRequest req, Model model, ParamMap pMap)
            throws Exception {
        logger.debug(">> sampleBoardListSave <<");
        logger.debug("pMap => {}", pMap.getMap().toString());

        int result = sampleInvoiceService.deleteBoardSample(pMap.getMap());

        Map<String, Object> rtMap = new HashMap<String, Object>();
        rtMap.put("data", "");
        rtMap.put("result", result);

        return new ResponseEntity<>(ResultResponse(rtMap), HttpStatus.OK);
    }

    @RequestMapping(value = "/sample/invoice/detail/search", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity<ResultResponseDto> sampleInvoiceDetailSearch(HttpServletRequest req, Model model,
            ParamMap pMap) throws Exception {
        logger.debug(">>sampleInvoiceListSearch<<");
        logger.debug("pMap => {}", pMap.getMap().toString());

        Map<String, Object> rtMap = sampleInvoiceService.searchInvoiceDetail(pMap);

        logger.debug("rtMap => {}", rtMap);

        return new ResponseEntity<>(ResultResponse(rtMap), HttpStatus.OK);
    }
}
