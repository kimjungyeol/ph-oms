package com.ktnet.sample;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ktnet.fta.common.scheduler.TestDynamicScheduler;

@SpringBootTest
class scheduleTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    public TestDynamicScheduler testDynamicScheduler;
    
    @Test
    @DisplayName("ScheduleTest")
	void schedule() {
        logger.debug("schedule");

        testDynamicScheduler.startScheduler();

        assertThat("").isNullOrEmpty();
    }

}
