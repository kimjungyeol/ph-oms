package com.ktnet.weka.hscode;

import static org.assertj.core.api.Assertions.assertThat;

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

import com.ktnet.fta.item.service.ItemService;
import com.ktnet.fta.weka.hscode.HscodePredictor;
import com.ktnet.fta.weka.hscode.HscodeTrainer;
import com.ktnet.fta.weka.hscode.dto.HscodeClassifierDto;
import com.ktnet.fta.weka.hscode.dto.PredictResultDto;

//@SpringBootTest
//@ActiveProfiles("local")
public class HscodeClassifierTest {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemService ItemService;
    
   // @Test
    @DisplayName("training test")
    public void training_hs_service_test() throws Exception {
        Long companyId = Long.valueOf(100);
        
        long beforeTime = System.currentTimeMillis();
        
        Map<String, Object> pMap = new HashMap<>();
        List<Map<String, Object>> hscodeList = ItemService.searchHsCodeList(pMap);
        log.info("hscodeList count : " + hscodeList.size());
        
        List<String> classes = new ArrayList<String>();
        for (Map<String, Object> item : hscodeList) {
        	log.info(item.get("hsCode")+" : " + item.get("itemNm"));
        	classes.add(item.get("hsCode")+"");
        }
        
        log.info("classes count : " + classes.size());
        
        HscodeTrainer trainer = HscodeTrainer.getInstance("/wekatest", companyId, classes);
        HscodeClassifierDto classifierDto = null;
        
        for (Map<String, Object> item : hscodeList) {
        	log.info(item.get("hsCode")+" : " + item.get("itemNm"));
        	
        	classifierDto = new HscodeClassifierDto();
        	classifierDto.setItemName(item.get("itemNm")+"");
        	classifierDto.setHscode(item.get("hsCode")+"");
        	
        	trainer.addInstance(classifierDto);
        }
        
        log.info("training start!!");
        
        trainer.training();
        
        long afterTime = System.currentTimeMillis();
        long secDiffTime = (afterTime - beforeTime)/1000;
        System.out.println("시간차이(m) : " + secDiffTime);
        
        assertThat(secDiffTime).isNotNull();
    }
    
    //@Test
    @DisplayName("training test")
    public void training_hs_test() throws Exception {
        Long companyId = Long.valueOf(1);
        
        //hscode
        List<String> classes = new ArrayList<String>();
        classes.add("나) 가나다라마");
        classes.add("4) 가나다1234");
        classes.add("뮤) 매직 홀딩 중화제 크림형 2제 (2015)BULK");
        classes.add("11222222");
        
        HscodeTrainer trainer = HscodeTrainer.getInstance("/wekatest", companyId, classes);
        
        HscodeClassifierDto classifierDto = new HscodeClassifierDto();
        classifierDto.setItemName("가나다라마");
        classifierDto.setHscode("나) 가나다라마");
        trainer.addInstance(classifierDto);
        
        classifierDto = new HscodeClassifierDto();
        classifierDto.setItemName("가나다1234");
        classifierDto.setHscode("4) 가나다1234");
        trainer.addInstance(classifierDto);
        
        classifierDto = new HscodeClassifierDto();
        classifierDto.setItemName("매직 홀딩 중화제 크림형 2제");
        classifierDto.setHscode("뮤) 매직 홀딩 중화제 크림형 2제 (2015)BULK");
        trainer.addInstance(classifierDto);
        
        classifierDto = new HscodeClassifierDto();
        classifierDto.setItemName("컴퓨터");
        classifierDto.setHscode("11222222");
        trainer.addInstance(classifierDto);
        
        trainer.training();
        
        assertThat(trainer).isNotNull();
    }

    @Test
    @DisplayName("predict test")
    public void predict_hs_test() throws Exception {
        Long companyId = Long.valueOf(100);
        
        HscodePredictor predictor = HscodePredictor.getInstance("/wekatest", companyId);
        
//      PredictResultDto result = predictor.predict("뮤) 매직 홀딩 중화제 크림형 2제 (2015)BULK");
//        PredictResultDto result = predictor.predict("컴퓨터");
//      PredictResultDto result2 = predictor.predict("나) 가나다라마");
        
        //String predictStr = "V81B BALL VALVE";
        String predictStr = "BT-CAP";
        
        //가장 예측률이 높은 한건.
        PredictResultDto result = predictor.predict(predictStr);
        log.info(">>> predict result : " + result.getResult());
        log.info(">>> predict rate : " + Math.floor(result.getRate()*100) + " (" + result.getRate() + ")");
        
        //예측률이 높은 n건.
//        List<PredictResultDto> resultList = predictor.predictList(predictStr);
//        for (PredictResultDto dto : resultList) {
//        	log.info(">>> predict result : " + dto.getResult());
//        	log.info(">>> predict rate : " + Math.floor(dto.getRate()*100) + " (" + dto.getRate() + ")");
//        	log.info("===================================");
//        }
        
        assertThat(result).isNotNull();
    }

}
