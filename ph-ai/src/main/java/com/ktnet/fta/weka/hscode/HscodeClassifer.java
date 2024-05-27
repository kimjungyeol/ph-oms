package com.ktnet.fta.weka.hscode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktnet.common.analyzer.BagOfWordAnalyzer;
import com.ktnet.fta.weka.hscode.dto.HscodeClassifierDto;

import lombok.extern.slf4j.Slf4j;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

@Slf4j
public class HscodeClassifer {
	
	static Logger logger = LoggerFactory.getLogger(HscodeClassifer.class);

    protected static final String INSTANCE_NAME = "HscodeClassifier";
    
    // 모델 파일 위치
    private String location;
    // 회사 ID
    protected Long companyId;
    // hs code 길이
    protected int hscodeBeginIndex = 0;
    protected int hscodeEndIndex = 6;
    
    // 독립변수 및 종속변수
    protected ArrayList<Attribute> attributes = new ArrayList<>();
    
    // 인스턴스 목록 객체
    protected Instances instances;
    
    // 분류 알고리즘
    protected FilteredClassifier classifier;
    
    // 종속 변수의 클래스 목록
    protected List<String> classes = new ArrayList<>();
    
    public String getInstanceName() {
        return INSTANCE_NAME;
    }
    
    public File getArffFile() {
        return new File(this.location + "/model/" + String.valueOf(this.companyId),
        this.getInstanceName() + "_(" + hscodeBeginIndex + "_" + hscodeEndIndex + ").arff");
    }
    
    public File getModelFile() {
        return new File(this.location + "/model/" + String.valueOf(this.companyId),
        this.getInstanceName() + "_(" + hscodeBeginIndex + "_" + hscodeEndIndex + ").model");
    }
    
    public DenseInstance createInstance(HscodeClassifierDto classifierDto) {
        // 특성정보(품명 - 형태소 분석)
        String itemName = classifierDto.getItemName();
        logger.debug(">>> itemName : " + itemName);
        String itemNameBow = BagOfWordAnalyzer.analyze(itemName);
        
        logger.debug(">>> itemNameBow : " + itemNameBow);
        if (StringUtils.isBlank(itemNameBow)) {
          return null;
        }
        // 독립변수 설정
        double[] value = new double[this.attributes.size()];
        int index = 0;
        value[index] = this.attributes.get(index++).addStringValue(itemNameBow);
        if (StringUtils.isNotBlank(classifierDto.getHscode())
            && classifierDto.getHscode().length() >= hscodeEndIndex) {
          // 종속변수 설정
          value[index] = this.classes
              .indexOf(classifierDto.getHscode().substring(hscodeBeginIndex, hscodeEndIndex));
        }
        
        // 인스턴스 추가
        return new DenseInstance(1.0, value);
    }

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public int getHscodeBeginIndex() {
		return hscodeBeginIndex;
	}

	public void setHscodeBeginIndex(int hscodeBeginIndex) {
		this.hscodeBeginIndex = hscodeBeginIndex;
	}

	public int getHscodeEndIndex() {
		return hscodeEndIndex;
	}

	public void setHscodeEndIndex(int hscodeEndIndex) {
		this.hscodeEndIndex = hscodeEndIndex;
	}

	public ArrayList<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(ArrayList<Attribute> attributes) {
		this.attributes = attributes;
	}

	public Instances getInstances() {
		return instances;
	}

	public void setInstances(Instances instances) {
		this.instances = instances;
	}

	public FilteredClassifier getClassifier() {
		return classifier;
	}

	public void setClassifier(FilteredClassifier classifier) {
		this.classifier = classifier;
	}

	public List<String> getClasses() {
		return classes;
	}

	public void setClasses(List<String> classes) {
		this.classes = classes;
	}
}
