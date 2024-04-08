package com.ktnet.sample;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.ktnet.fta.sample.service.SampleService;

@SpringBootTest
class commonServiceTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SampleService sampleService;

    @Value("${common.file.uploadPath}")
    private String uploadPath;

    @Test
    @DisplayName("porperties test.")
    void test() {
        logger.debug("=============================>>");
        logger.debug("common.file.uploadPath = {}", uploadPath);
        logger.debug("<<=============================");
    }

    @Test
    @DisplayName("전체 조회")
    void test1() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pgStart", 1);
        map.put("pgLimit", 10);

        List<Map<String, Object>> list = null;

        try {
            list = sampleService.searchSampleList(map);

            logger.debug("===searchSampleList Data===");
            for (Map<String, Object> rMap : list) {
                logger.debug("data = {}", rMap.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        assertThat(list).isNotEmpty();
    }

}
