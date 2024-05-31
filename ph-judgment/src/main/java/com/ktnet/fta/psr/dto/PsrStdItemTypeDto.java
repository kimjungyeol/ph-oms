package com.ktnet.fta.psr.dto;

public class PsrStdItemTypeDto {

    private Long standardItemTypeId;

    public Long getStandardItemTypeId() {
        return standardItemTypeId;
    }

    public void setStandardItemTypeId(Long standardItemTypeId) {
        this.standardItemTypeId = standardItemTypeId;
    }

    public PsrStdItemTypeDto() {

    }

    public PsrStdItemTypeDto(Long standardItemTypeId) {

        this.standardItemTypeId = standardItemTypeId;
    }
}
