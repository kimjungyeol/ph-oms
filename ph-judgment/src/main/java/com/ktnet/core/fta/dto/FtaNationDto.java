package com.ktnet.core.fta.dto;

import com.ktnet.core.nation.dto.NationRetrieveDto;

public class FtaNationDto {

    private Long id;

    private Long ftaId;

    private String beginDate;

    private String endDate = "29991231";

    private Long nationId;

    private NationRetrieveDto nation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFtaId() {
        return ftaId;
    }

    public void setFtaId(Long ftaId) {
        this.ftaId = ftaId;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getNationId() {
        return nationId;
    }

    public void setNationId(Long nationId) {
        this.nationId = nationId;
    }

    public NationRetrieveDto getNation() {
        return nation;
    }

    public void setNation(NationRetrieveDto nation) {
        this.nation = nation;
    }

    public FtaNationDto() {
    }

    public FtaNationDto(Long id, Long ftaId, String beginDate, String endDate, Long nationId,
            NationRetrieveDto nation) {
        this.id = id;
        this.ftaId = ftaId;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.nationId = nationId;
        this.nation = nation;
    }
}
