package com.ktnet.fta.details;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ktnet.fta.details.document.service.DetailsInvoiceService;
import com.ktnet.fta.details.group.service.DetailsGroupService;

@SpringBootTest
public class DetailsGeneratorTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DetailsInvoiceService detailsInvoiceService;
    @Autowired
    private DetailsGroupService detailsGroupService;

    @Test
    @DisplayName("test.")
    public void detailsGeneratorTest() {
        Map<String, Object> map = new HashMap<String, Object>();

        Map<String, Object> resultMap = detailsGroupService.searchDetailsGroup(1017L);

        logger.debug(resultMap.toString());
    }
}
