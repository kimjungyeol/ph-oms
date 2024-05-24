package com.ktnet.fta.common.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.repeat.policy.CompositeCompletionPolicy;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.batch.repeat.policy.TimeoutTerminationPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.ktnet.fta.sample.service.SampleService;

import jakarta.annotation.Resource;

@Configuration
class TestBatchJob {

    Logger logger = LoggerFactory.getLogger(getClass());
    
    private final String JobName = JobConstant.TESTJOB;
    
    @Autowired
    private DataSource batchDataSource;
    
    @Resource(name = "sampleService")
    private SampleService sampleService;
    
    @Bean(name = JobName)
    Job job(JobRepository jobRepository) {
        return new JobBuilder(JobName, jobRepository).start(step(jobRepository)).build();
    }

    @Bean
    Step step(JobRepository jobRepository) {
        StepBuilder stepBuilderOne = new StepBuilder("step", jobRepository);
        return stepBuilderOne.tasklet(helloWorldTasklet(), transactionManager()).build();
    }

    @Bean
    CompletionPolicy completionPolicy() {
        CompositeCompletionPolicy policy = new CompositeCompletionPolicy();
        policy.setPolicies(
                new CompletionPolicy[] { new TimeoutTerminationPolicy(3), new SimpleCompletionPolicy(1000) });
        return policy;
    }

    @Bean
    DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(batchDataSource);
    }

    @Bean
    @JobScope
    Tasklet helloWorldTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> {
            logger.debug("Hello, World!=============================");
            
            String executeDate = (String) chunkContext.getStepContext().getJobParameters().get("executeDate");
            String path = (String) chunkContext.getStepContext().getJobParameters().get("path");
            
            logger.debug("executeDate : {}", executeDate);
            logger.debug("path : {}", path);
            
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pgStart", 1);
            map.put("pgLimit", 10);
            List<Map<String, Object>> rtList = sampleService.searchSampleList(map);

            logger.debug("rtMap", rtList.toString());

            return RepeatStatus.FINISHED;
        };
    }
}