package com.ktnet.sample;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class SimulationServiceTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    @DisplayName("SIMULATION TEST")
    void test() {
        Map<String, String> pMap = new HashMap<String, String>();

        pMap.put("params", "jjjj");

        ObjectMapper mapper = new ObjectMapper();

        // String jsonString = mapper.writeValueAsString(pMap);
//        System.out.println("pMap : " + mapper.writeValueAsString(pMap));
//
//        RestClient restClient = RestClient.create();
//
//        Map<?, ?> response = restClient.post().uri("http://localhost:8090/api/post/test")
//                .contentType(MediaType.APPLICATION_JSON).body(mapper.writeValueAsString(pMap)).retrieve()
//                .body(Map.class);
//
//        System.out.println("result : " + response);

    }
}
