package com.ktnet.fta.weka.hscode.dto;

public class PredictResultDto {

    private String key;
    private String result;
    private double rate = 0;
    
    public PredictResultDto() {
    }
    
    public PredictResultDto(String key, String result, double rate) {
    	this.key = key;
    	this.result = result;
    	this.rate = rate;
    }

	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public double getRate() {
		return rate;
	}
	
	public void setRate(double rate) {
		this.rate = rate;
	}

}
