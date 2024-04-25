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
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.Commit;

import com.ktnet.fta.sample.service.SampleService;

@SpringBootTest
class sampleApplicationTest {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    SampleService sampleService;
	
	@Value("${common.file.uploadPath}")
    private String uploadPath;
	
	@Value("${common.file.allowExtention}")
	private String allowExtention;
	
	@Autowired
	Environment environment;
	
	@Test
    @DisplayName("porperties test.")
	void propTest() {
		logger.debug("=============================>>");
		logger.debug("common.file.uploadPath = {}", uploadPath);
		logger.debug("common.file.allowExtention = {}", allowExtention);
		logger.debug("system.lang = {}", environment.getProperty("system.lang"));
		logger.debug("server.port = {}", environment.getProperty("server.port"));
		logger.debug("<<=============================");
	}

	@Test
    @DisplayName("전체 조회")
    void test1() {
    	Map<String, Object> map = new HashMap<String,Object>();
    	map.put("pgStart", 1);
    	map.put("pgLimit", 10);
    	
    	List<Map<String, Object>> list = null;
    	
    	try {
    		list = sampleService.searchSampleList(map);
    		
    		logger.debug("===searchSampleList Data===");
    		for (Map<String, Object> rMap : list) {
    			logger.debug("data = {}", rMap.toString());
    		}
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	assertThat(list).isNotEmpty();
    }
	
	//@Test
	@DisplayName("단건 조회")
	void test2() {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("name", "name1");
		
		Map<String, Object> rtMap = sampleService.searchSample(map);
		
		logger.debug("===searchSample Data===");
		logger.debug("data = {}", rtMap.toString());
		
		assertThat(rtMap).isNotEmpty();
	}

    //@Test
    @Commit
    @DisplayName("생성/수정")
    void test3() {
  	    Map<String, Object> map = new HashMap<String,Object>();
  	    map.put("tran", "U");
  	    //map.put("tran", "I");
  	    map.put("name", "test99");
  	    map.put("nameEng", "test99_eng");
  	    map.put("age", "25");
  	    map.put("email", "adasdf@ktnet.com");
  	    map.put("useyn", "Y");
  	    
  	    try {
  	  	    int rtnCnt = sampleService.saveSample(map);
  	  	    assertThat(rtnCnt).isEqualTo(1);
  	    } catch(Exception e) {
      		e.printStackTrace();
  	    }
    }
    
    //@Test
    @Commit
    @DisplayName("Transaction Test")
    void test4() {
    	Map<String, Object> map = new HashMap<String,Object>();
    	map.put("tran", "U");
    	map.put("name", "test99");
    	map.put("age", "77");
    	map.put("email", "77777@ktnet.com");
    	map.put("useyn", "Y");
    	
    	try {
    		int rtnCnt = sampleService.transTest(map);
    		assertThat(rtnCnt).isEqualTo(1);
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }

}
