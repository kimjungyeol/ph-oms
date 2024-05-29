package com.ktnet.fta.sample.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktnet.fta.common.job.JobConstant;
import com.ktnet.fta.common.scheduler.TestDynamicScheduler;
import com.ktnet.fta.common.web.BasicController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class SampleRestController extends BasicController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
    public TestDynamicScheduler testDynamicScheduler;
	
	@Autowired
    private JobLauncher asyncJobLauncher;

	@Autowired
	@Qualifier(JobConstant.TESTJOB)
	private Job job;

	@SuppressWarnings("null")
    @GetMapping("/sample/rest/errtest")
    public String errtest(HttpServletRequest req, Model model) throws Exception {
    	logger.info("errtest");
    	
    	//error
		String aa = null;
		logger.debug(aa.toString());
    	
    	return "sample/sampleView";
    }
	
	@GetMapping("/sample/schedule")
	public void schedule(HttpServletRequest req, Model model) throws Exception {
		logger.debug("schedule");
		
		//scheduler start or stop시 사용.
		testDynamicScheduler.startScheduler();
		//testDynamicScheduler.stopScheduler();
	}
	
	@GetMapping("/sample/batchjob")
	public void batchjob(HttpServletRequest req, Model model) throws Exception {
		logger.debug("batchjob");
		
		// Batch Job 실행.
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("executeDate", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
				.addString("path", "12312312").toJobParameters();
		asyncJobLauncher.run(job, jobParameters);
	}
    
}