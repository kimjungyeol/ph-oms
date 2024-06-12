package com.ktnet.fta.psr.dto;

import lombok.ToString;

@ToString
public class PsrStdItemTypeDto {

    private Long standardItemTypeId;

    public Long getStandardItemTypeId() {
        return standardItemTypeId;
    }

    public void setStandardItemTypeId(Long standardItemTypeId) {
        this.standardItemTypeId = standardItemTypeId;
    }
}
