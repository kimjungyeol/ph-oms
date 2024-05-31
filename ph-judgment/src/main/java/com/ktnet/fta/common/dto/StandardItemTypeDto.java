package com.ktnet.fta.common.dto;

public class StandardItemTypeDto {

    private Long id;

    private Long standardItemId;

    private StandardItemDto standardItem;

    private String code;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStandardItemId() {
        return standardItemId;
    }

    public void setStandardItemId(Long standardItemId) {
        this.standardItemId = standardItemId;
    }

    public StandardItemDto getStandardItem() {
        return standardItem;
    }

    public void setStandardItem(StandardItemDto standardItem) {
        this.standardItem = standardItem;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StandardItemTypeDto() {
    }

    public StandardItemTypeDto(Long id, Long standardItemId, StandardItemDto standardItem, String code,
            String description) {
        this.id = id;
        this.standardItemId = standardItemId;
        this.standardItem = standardItem;
        this.code = code;
        this.description = description;
    }

}