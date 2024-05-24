package com.ktnet.sample;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import com.ktnet.fta.common.job.JobConstant;

@SpringBootTest
class batchJobTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private JobLauncher asyncJobLauncher;

	@Autowired
	@Qualifier(JobConstant.TESTJOB)
	private Job job;
    
    @Test
    @DisplayName("BatchJobTest")
	void schedule() {
        logger.debug("BatchJobTest");
        
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("executeDate", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .addString("path", "12312312").toJobParameters();
        
        try {
			asyncJobLauncher.run(job, jobParameters);
		} catch (Exception e) {
			e.printStackTrace();
		}

        assertThat("").isNullOrEmpty();
    }

}
