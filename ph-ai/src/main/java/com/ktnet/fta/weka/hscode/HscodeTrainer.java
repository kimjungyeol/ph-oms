package com.ktnet.fta.weka.hscode;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktnet.common.exception.BizzException;
import com.ktnet.fta.weka.hscode.dto.HscodeClassifierDto;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Attribute;
import weka.core.Debug.Random;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSink;
import weka.core.stemmers.LovinsStemmer;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class HscodeTrainer extends HscodeClassifer {
	static Logger logger = LoggerFactory.getLogger(HscodeTrainer.class);
	
    private HscodeTrainer() {
    }

    public static HscodeTrainer getInstance(String location, Long companyId, List<String> classes)
        throws Exception {
        HscodeTrainer INSTANCE = new HscodeTrainer();
        INSTANCE.setLocation(location);
        INSTANCE.setCompanyId(companyId);
       
        INSTANCE.init(classes);
       
        return INSTANCE;
    }

    public void init(List<String> hscodes) throws Exception {
        if (hscodes == null || hscodes.isEmpty()) {
        	throw new BizzException("hscodeClassifier.classes.NotFound");
        }
        // 종속변수 클래스 유형 설정
        this.classes = hscodes.stream().filter(item -> StringUtils.isNotBlank(item))
            .filter(item -> StringUtils.isNotBlank(item) && item.length() >= hscodeEndIndex)
            .map(item -> item.substring(hscodeBeginIndex, hscodeEndIndex)).distinct()
            .collect(Collectors.toList());
        
        // 독립변수 및 종속변수 설정
        this.createAttributes();
        // 인스턴스 객체 목록 설정
        this.createInstances();
        // 분류 알고리즘 설정
        this.createClassifier();
    }

    private void createAttributes() {
        this.attributes.clear();
        // 독립변수 설정 - 원인
        this.attributes.add(new Attribute("itemNameBow", (List<String>) null));
        // 종속변수 설정 - 결과
        this.attributes.add(new Attribute("@@class@@", this.classes));
    }
    
    private void createInstances() {
        this.instances = new Instances(this.getInstanceName(), this.attributes, 0);
        int lastIndex = this.instances.numAttributes() - 1;
        this.instances.setClassIndex(lastIndex);
    }

    public void addInstance(HscodeClassifierDto dto) {
        DenseInstance instance = this.createInstance(dto);
        if (instance == null) {
          return;
        }
        
        // 인스턴스 추가
        this.instances.add(instance);
    }
    
    // 분류기 설정
    private void createClassifier() throws Exception {
        // 필터 설정
        StringToWordVector filter = new StringToWordVector();
        filter.setInputFormat(instances);
        filter.setIDFTransform(true);
        LovinsStemmer stemmer = new LovinsStemmer();
        filter.setStemmer(stemmer);
        filter.setLowerCaseTokens(true);
        
        // 분류기에 필터 설정
        this.classifier = new FilteredClassifier();
        this.classifier.setFilter(filter);
        // 분류기에 알고리즘 설정
        this.classifier.setClassifier(new NaiveBayes());
    }

    /*** 학습 ***/
    public void training() throws Exception {
        logger.debug(">>> classes : " + this.classes.size());
        logger.debug(">>> attribute : " + this.instances.numAttributes());
        logger.debug(">>> instance : " + this.instances.numInstances());
        
        // ARFF 저장
        this.saveArff();
        
        // 해당 알고리즘으로 데이터를 학습한다.
        this.classifier.buildClassifier(instances);
        
        // 교차 검증
        this.evaluation();
        
        // MODEL 저장
        this.saveModel();
    }
    
    /*** 교차 검증 ***/
    public Evaluation evaluation() throws Exception {
        logger.info("Instances size : " + this.instances.size());
        
        if (instances.size() < 10) {
          logger.info("learning data is to small");
          return null;
        }
        
        // 교차검증 실행
        Evaluation eval = new Evaluation(instances);
        eval.crossValidateModel(classifier, instances, instances.size() / 2, new Random(1));
        logger.info("Summary : " + eval.toSummaryString());
        logger.info("Matrix : " + eval.toMatrixString());
        logger.info("ClassDetails : " + eval.toClassDetailsString());
        
        return eval;
    }

    public void save() throws Exception {
        this.saveArff();
        this.saveModel();
    }
    
    private void saveArff() throws Exception {
        File file = this.getArffFile();
        if (!file.getParentFile().isDirectory()) {
          file.getParentFile().mkdirs();
        }
        // instance 저장
        FileOutputStream arffFile = new FileOutputStream(file);
        DataSink.write(arffFile, instances);
        arffFile.close();
    }
    
    private void saveModel() throws Exception {
        File file = this.getModelFile();
        if (!file.getParentFile().isDirectory()) {
          file.getParentFile().mkdirs();
        }
        // model 저장
        FileOutputStream modelFile = new FileOutputStream(file);
        SerializationHelper.write(modelFile, classifier);
        modelFile.close();
    }

}
