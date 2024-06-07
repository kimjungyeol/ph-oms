package com.ktnet.judgment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ktnet.fta.judgment.dto.JudgmentDto;
import com.ktnet.fta.judgment.dto.JudgmentSetupDto;
import com.ktnet.fta.judgment.mapper.JudgmentMapper;
import com.ktnet.fta.judgment.service.JudgmentService;
import com.ktnet.fta.psr.dto.PsrStdItemTypeDto;
import com.ktnet.fta.psr.service.PsrService;

@SpringBootTest
public class JudgmentApplicationTests {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JudgmentService judgmentService;

    @Autowired
    private PsrService psrService;

    @Autowired
    private JudgmentMapper judgmentMapper;

    @Test
    @DisplayName("test.")
    void dtoTest() {
        logger.debug("========================  JUnit judgment Test  ========================");

        // int cnt = judgmentMapper.selectJudgmentTest();
        // Map<String, Object> pMap = new HashMap<String, Object>();
        // judgmentMapper.selectJudgmentTest(pMap);
        // int cnt = judgmentService.searchJudgmentTest(pMap);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", "274342");
        params.put("detailsId", "97880");
        // List<JudgmentSetupDto> setups = judgmentMapper.selectJudgmentSetup(pMap);

        Map<Long, JudgmentSetupDto> setupMap = judgmentService.findSetupMap(params);
        //////////////////////////////
        PsrStdItemTypeDto itemType = psrService.searchPsrStdItemType(159420L, 104815L);
        System.out.println(itemType.getStandardItemTypeId());

        System.out.println("test success");

        /////////////////////////////
        params.put("groupId", 274465);
        params.put("detailsId", "");
        List<JudgmentDto> judgmentDtos = judgmentMapper.selectJudgmentList(params);
        System.out.println(judgmentDtos.size());
        System.out.println(judgmentDtos.toString());
    }

    @Test
    @DisplayName("judgment test.")
    void judgmentTest() {
        logger.debug("========================  JUnit judgment Test  ========================");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("groupId", "275794");
        map.put("companyId", "8048");

        judgmentService.judgmentExecute(map);

        System.out.println("SUCCESS");

    }

    @Test
    @DisplayName("simulation test.")
    void simulationTest() {
        logger.debug("========================  JUnit simulation Test  ========================");

        Map<String, Object> ftaInfo = new HashMap<String, Object>();
        List<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>();

        // ftaId, destinationCode, hsCode, amount, psrStandard, rvcStandardRate
        // itemName, hsCode, amount, origin

        // ftaInfo
        ftaInfo.put("ftaId", "KOREA-EU");
        ftaInfo.put("destinationCode", "KR");
        ftaInfo.put("hsCode", "830140");
        ftaInfo.put("amount", "100000");
        ftaInfo.put("psrStandard", "RVC");
        ftaInfo.put("rvcStandardRate", "70");

        // itemList
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("itemName", "mtr1");
        map.put("hsCode", "830140");
        map.put("amount", "50000");
        map.put("origin", "Y");
        itemList.add(map);

        map = new HashMap<String, Object>();
        map.put("itemName", "mtr2");
        map.put("hsCode", "830140");
        map.put("amount", "30000");
        map.put("origin", "Y");
        itemList.add(map);

        map = new HashMap<String, Object>();
        map.put("itemName", "mtr3");
        map.put("hsCode", "830140");
        map.put("amount", "20000");
        map.put("origin", "N");
        itemList.add(map);

        judgmentService.simulationExecute(ftaInfo, itemList);

    }
}
