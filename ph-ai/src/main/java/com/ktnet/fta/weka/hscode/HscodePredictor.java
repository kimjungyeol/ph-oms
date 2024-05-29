package com.ktnet.fta.weka.hscode;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktnet.common.exception.BizzException;
import com.ktnet.fta.weka.hscode.dto.HscodeClassifierDto;
import com.ktnet.fta.weka.hscode.dto.PredictResultDto;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import weka.classifiers.Classifier;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;

@Getter
@Slf4j
public class HscodePredictor extends HscodeClassifer {
	
	static Logger logger = LoggerFactory.getLogger(HscodePredictor.class);

    private static Map<Long, HscodePredictor> INSTANCES = new HashMap<>();
    
    private HscodePredictor() {
    }
    
    public static HscodePredictor getInstance(String location, Long companyId) throws Exception {
        HscodePredictor INSTANCE = INSTANCES.get(companyId);
        if (INSTANCE == null) {
            INSTANCE = new HscodePredictor();
            INSTANCE.setLocation(location);
            INSTANCE.setCompanyId(companyId);
            INSTANCE.load();
            
            INSTANCES.put(companyId, INSTANCE);
        }
        return INSTANCE;
    }
    
    /*** ARFF 및 모델 로드 ***/
    public void load() throws Exception {
        this.loadArff();
        this.loadModel();
    }
    
    private void loadArff() throws Exception {
        File file = this.getArffFile();
        if (!file.exists()) {
        	  logger.info(">> arff File location : " + file.getAbsolutePath());
            throw new BizzException("hscodeClassifier.arff.NotFound");
        }
        // 분류기 인스턴스 로드
        DataSource source = new DataSource(file.getAbsolutePath());
        // 인스턴스 목록 설정
        this.instances = source.getDataSet();
        
        // 독립변수 및 종속변수 설정
        this.attributes.clear();
        for (int index = 0; index < instances.numAttributes(); index++) {
            this.attributes.add(instances.attribute(index));
        }
        
        // 마지막 종속 변수 속성
        Attribute lastAttribute = instances.attribute(instances.numAttributes() - 1);
        
        // 종속 변수 클래스 목록
        Enumeration<Object> classObjects = lastAttribute.enumerateValues();
        this.classes.clear();
        while (classObjects.hasMoreElements()) {
            this.classes.add(String.valueOf(classObjects.nextElement()));
        }
       
        // 종속 변수 인덱스 설정
        instances.setClassIndex(lastAttribute.index());
        
        // 메모리 확보를 위해 인스턴스 데이터 메모리에서 제거
        this.instances.delete();
    }
    
    private void loadModel() throws Exception {
        File file = this.getModelFile();
        
        if (!file.exists()) {
        	logger.info(">> model File location : " + file.getAbsolutePath());    	
            throw new BizzException("hscodeClassifier.model.NotFound");
        }
        
        // 모델 파일이 있는 경우, 모델 파일로 부터 로드
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            this.classifier = (FilteredClassifier) SerializationHelper.read(fis);
        } catch (Exception e) {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
                fis = null;
            }
            e.printStackTrace();
        }
    }
    
    public PredictResultDto predict(String itemName) throws Exception {
        HscodeClassifierDto dto = new HscodeClassifierDto(itemName);
        return this.predict(dto);
    }
    
    public PredictResultDto predict(HscodeClassifierDto dto) throws Exception {
        // 인스턴스 설정
        Instance instance = this.createInstance(dto);
        //logger.info(">>>> instance : " + instance);
        
        PredictResultDto predictDto = new PredictResultDto();
        
        if (instance == null) {
        	predictDto.setRate(0);
            return predictDto;
        }
        instances.add(instance);
        
        Classifier model = new J48();
        classifier.setClassifier(model);
        
        // 데이터 예측
        for (int i = 0; i < instances.numInstances(); i++) {
        	instances.instance(i).setClassValue(classifier.classifyInstance(instances.instance(i)));  //예측
        }
        
        // 예측 결과 가져오기
        Instance lastInstance = instances.lastInstance();
        int classValue = (int) lastInstance.classValue();
        
        // 결과 정보 등록.
        predictDto.setResult(lastInstance.stringValue(this.attributes.size() - 1));
        predictDto.setRate(classifier.distributionForInstance(lastInstance)[classValue]);
        
        return predictDto;
    }
    
    /**
     * 데이터 예측 결과를 n개 리턴.
     * @param itemName
     * @return
     * @throws Exception
     */
    public List<PredictResultDto> predictList(String itemName) throws Exception {
        HscodeClassifierDto dto = new HscodeClassifierDto(itemName);
        return this.predictList(dto);
    }
    public List<PredictResultDto> predictList(HscodeClassifierDto dto) throws Exception {
    	// 인스턴스 설정
    	Instance instance = this.createInstance(dto);
    	//logger.info(">>>> instance : " + instance);
    	
    	List<PredictResultDto> predictResultList = new ArrayList<>();
    	PredictResultDto predictDto = new PredictResultDto();
    	
    	if (instance == null) {
    		predictDto.setResult("");
    		predictDto.setRate(0);
    		predictResultList.add(predictDto);
    		return predictResultList;
    	}
    	
    	instances.add(instance);
    	
    	// 데이터 예측
    	for (int i = 0; i < instances.numInstances(); i++) {
    		instances.instance(i).setClassValue(classifier.classifyInstance(instances.instance(i)));  //예측
    	}
    	
    	// 예측 결과 가져오기
    	Instance lastInstance = instances.lastInstance();
    	
    	// 예측 데이터들의 예측률 정보.
    	double[] prediction = classifier.distributionForInstance(instances.lastInstance());
    	//System.out.println("prediction CNT : " + prediction.length);
    	//System.out.println("=============================");
    	
    	int idx = 0;
    	for (double v : prediction) {
    		if ((Math.floor(v*100)) >= 0.1) {
    			// 결과 정보 등록.
    			predictDto = new PredictResultDto();
    			predictDto.setResult(lastInstance.classAttribute().value(idx));
    			predictDto.setRate(v);
    			predictResultList.add(predictDto);
    			
    			//System.out.println("prediction result : " + lastInstance.classAttribute().value(idx));
    			//System.out.println("rate  : " + classifier.distributionForInstance(lastInstance)[idx]);
    			//System.out.println("prediction rate   : " + Math.floor(v*100));
    		}
    		idx++;
    	}
    	
    	return predictResultList;
    }

}
