package com.ktnet.weka.hscode;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktnet.fta.weka.hscode.HscodePredictor;
import com.ktnet.fta.weka.hscode.HscodeTrainer;
import com.ktnet.fta.weka.hscode.dto.HscodeClassifierDto;
import com.ktnet.fta.weka.hscode.dto.PredictResultDto;

//@SpringBootTest
//@ActiveProfiles("local")
public class HscodeClassifierTest {

    private Logger log = LoggerFactory.getLogger(this.getClass());

//  @Value("${spring.servlet.multipart.location:#{null}}")
//  private String location;
//
  //@Autowired
  //private ItemRepository itemRepository;

//  @Test
//  @DisplayName("training test")
//  public void training_hs_test() throws Exception {
//    Long companyId = Long.valueOf(1);
//
//    List<HscodeClassifierDto> hscodeClassifers = itemRepository.findHscodeByCompany(companyId);
//    List<String> classes =
//        hscodeClassifers.stream().map(item -> item.getHscode()).collect(Collectors.toList());
//
//    HscodeTrainer trainer = HscodeTrainer.getInstance(location, companyId, classes);
//    hscodeClassifers = itemRepository.findHscodeClassifierByCompany(companyId);
//    hscodeClassifers.stream().forEach(item -> {
//      trainer.addInstance(item);
//    });
//
//    trainer.training();
//  }
//  
 // @Test
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
  }

  @Test
  @DisplayName("predict test")
  public void predict_hs_test() throws Exception {
      Long companyId = Long.valueOf(1);
      
      HscodePredictor predictor = HscodePredictor.getInstance("/wekatest", companyId);
      
//    PredictResultDto result = predictor.predict("뮤) 매직 홀딩 중화제 크림형 2제 (2015)BULK");
      PredictResultDto result = predictor.predict("컴퓨터");
//    PredictResultDto result2 = predictor.predict("나) 가나다라마");
      
      log.info(">>> predict result : " + result.getResult());
      log.info(">>> predict rate : " + Math.floor(result.getRate()*100) + " (" + result.getRate() + ")");

  }

}
