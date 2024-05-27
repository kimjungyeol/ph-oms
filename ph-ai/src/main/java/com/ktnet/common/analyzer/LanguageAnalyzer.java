package com.ktnet.common.analyzer;

import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import lombok.NoArgsConstructor;
import opennlp.tools.langdetect.Language;
import opennlp.tools.langdetect.LanguageDetector;
import opennlp.tools.langdetect.LanguageDetectorME;
import opennlp.tools.langdetect.LanguageDetectorModel;

@NoArgsConstructor
public class LanguageAnalyzer {

    private String modelPath;
    private LanguageDetectorModel model;
    private LanguageDetector detector;
    
    public LanguageAnalyzer(String modelPath) throws IOException {
        this.modelPath = modelPath;
        Resource resource = new ClassPathResource(modelPath + "/langdetect-183.bin");
        model = new LanguageDetectorModel(resource.getInputStream());
        detector = new LanguageDetectorME(model);
    }
    
    public Language predict(String word) {
        return detector.predictLanguage(word);
    }

	public String getModelPath() {
		return modelPath;
	}
	
	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}
	
	public LanguageDetectorModel getModel() {
		return model;
	}
	
	public void setModel(LanguageDetectorModel model) {
		this.model = model;
	}
	
	public LanguageDetector getDetector() {
		return detector;
	}
	
	public void setDetector(LanguageDetector detector) {
		this.detector = detector;
	}
}
