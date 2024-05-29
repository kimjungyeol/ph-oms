package com.ktnet.judgment;

import java.math.BigDecimal;
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
import com.ktnet.fta.judgment.dto.TestDto;
import com.ktnet.fta.judgment.service.JudgmentService;

@SpringBootTest
public class JudgmentApplicationTests {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JudgmentService judgmentService;

    @Test
    @DisplayName("DTO test.")
    void dtoTest() {
        logger.debug("========================  JUnit judgment Test  ========================");

        // TestDto testDto = TestDto.builder();
        // TestDto testDto =
        // TestDto.builder().value("aaaaaaaaaa").testValue("bbbbbbbbbbb").build();

        TestDto testDto = TestDto.builder().build();
        TestDto testDto2 = new TestDto();

        logger.debug(testDto.getValue());
        logger.debug(testDto.getTestValue());

        logger.debug(testDto2.getValue());
        logger.debug(testDto2.getTestValue());

        // 초기화 확인 필요
        JudgmentDto judgment = JudgmentDto.builder().amount(new BigDecimal(10000)).build();
        JudgmentDto judgment2 = new JudgmentDto();

        // logger.debug("judgmentDto", judgment.toString());
        logger.debug("judgment", judgment.toString());
        logger.debug("judgment2", judgment2.toString());
    }

    @Test
    @DisplayName("Judgment test.")
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
