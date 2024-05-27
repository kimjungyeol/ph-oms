package com.ktnet.fta.weka.hscode.dto;

public class HscodeClassifierDto {

    private String itemId;
    private String itemCode;
    private String itemName;
    private String itemStandard;
    private String hscode;
    
    public HscodeClassifierDto() {
    }
    public HscodeClassifierDto(String itemName) {
    	this.itemName = itemName;
    }
    public HscodeClassifierDto(String itemId, String itemCode, String itemName, String itemStandard, String hscode) {
        this.itemId = itemId;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemStandard = itemStandard;
        this.hscode = hscode;
    }

	public String getItemId() {
		return itemId;
	}
	
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	public String getItemCode() {
		return itemCode;
	}
	
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public String getItemStandard() {
		return itemStandard;
	}
	
	public void setItemStandard(String itemStandard) {
		this.itemStandard = itemStandard;
	}
	
	public String getHscode() {
		return hscode;
	}
	
	public void setHscode(String hscode) {
		this.hscode = hscode;
	}

}


