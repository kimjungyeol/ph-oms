package com.ktnet.fta.common.scheduler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.support.CronTrigger;

@Configuration
@EnableScheduling
public class TestDynamicScheduler extends DynamicAbstractScheduler {
	
	Logger logger = LoggerFactory.getLogger(getClass());
    
	@Value("${schedule.test.cron}")
    private String testCron;
    
    private JobLauncher jobLauncher;
    
    private Job job;
    
    TestDynamicScheduler(JobLauncher jobLauncher, @Qualifier("testJob")Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }
    
    @Override
    public void runner() {
    	logger.debug("TestDynamicScheduler run : {}", new Date());
    	
    	//Batch Job 실행.
        JobParameters parameters = new JobParametersBuilder()
        				.addDate("curDate", new Date())
                        .addString("JobID", String.valueOf(System.currentTimeMillis()))
                        .toJobParameters();
        try {
            jobLauncher.run(job, parameters);
        } catch(Exception e) {}
    }
    
    @Override
    public Trigger getTrigger() { return new CronTrigger(testCron); }
}