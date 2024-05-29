package com.ktnet.core.psr.base.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.ktnet.core.fta.dto.FtaDto;
import com.ktnet.core.item.dto.StandardItemTypeDto;
import com.ktnet.core.psr.condition.dto.PsrConditionDto;

public class PsrDto {

    private Long id;

    private Long ftaId;

    private FtaDto fta;

    private Long standardItemTypeId;

    private Long sortSn;

    private StandardItemTypeDto standardItemType;

    private String nameOfCo; // 증명서 표기법

    private String nameOfDo; // 확인서 표기법

    private String description;

    /*** 완전 생산 기준 ***/
    private Boolean woUse; // 완전생산기준

    /*** 가공 공정 기준 ***/
    private Boolean spUse; // 가공공정기준

    /*** 세번 변경 기준 ***/
    private Boolean ctcUse; // 세번변경기준

    private String ctcStandard; // 세번변경유형

    private String deminimisStandard; // 미소기준

    private BigDecimal deminimisRate; // 미소기준 비율

    /*** 부가가치 기준 ***/
    private Boolean rvcUse; // 부가가치기준

    private String rvcStandard; // RVC 기준

    private BigDecimal rvcRate; // RVC 비율

    /*** 예외 기준 ***/
    private Boolean conditionUse; // 예외기준
    private Boolean choiceUse; // 선택기준

    private List<PsrConditionDto> conditions;

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

    public FtaDto getFta() {
        return fta;
    }

    public void setFta(FtaDto fta) {
        this.fta = fta;
    }

    public Long getStandardItemTypeId() {
        return standardItemTypeId;
    }

    public void setStandardItemTypeId(Long standardItemTypeId) {
        this.standardItemTypeId = standardItemTypeId;
    }

    public Long getSortSn() {
        return sortSn;
    }

    public void setSortSn(Long sortSn) {
        this.sortSn = sortSn;
    }

    public StandardItemTypeDto getStandardItemType() {
        return standardItemType;
    }

    public void setStandardItemType(StandardItemTypeDto standardItemType) {
        this.standardItemType = standardItemType;
    }

    public String getNameOfCo() {
        return nameOfCo;
    }

    public void setNameOfCo(String nameOfCo) {
        this.nameOfCo = nameOfCo;
    }

    public String getNameOfDo() {
        return nameOfDo;
    }

    public void setNameOfDo(String nameOfDo) {
        this.nameOfDo = nameOfDo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getWoUse() {
        return woUse;
    }

    public void setWoUse(Boolean woUse) {
        this.woUse = woUse;
    }

    public Boolean getSpUse() {
        return spUse;
    }

    public void setSpUse(Boolean spUse) {
        this.spUse = spUse;
    }

    public Boolean getCtcUse() {
        return ctcUse;
    }

    public void setCtcUse(Boolean ctcUse) {
        this.ctcUse = ctcUse;
    }

    public String getCtcStandard() {
        return ctcStandard;
    }

    public void setCtcStandard(String ctcStandard) {
        this.ctcStandard = ctcStandard;
    }

    public String getDeminimisStandard() {
        return deminimisStandard;
    }

    public void setDeminimisStandard(String deminimisStandard) {
        this.deminimisStandard = deminimisStandard;
    }

    public BigDecimal getDeminimisRate() {
        return deminimisRate;
    }

    public void setDeminimisRate(BigDecimal deminimisRate) {
        this.deminimisRate = deminimisRate;
    }

    public Boolean getRvcUse() {
        return rvcUse;
    }

    public void setRvcUse(Boolean rvcUse) {
        this.rvcUse = rvcUse;
    }

    public String getRvcStandard() {
        return rvcStandard;
    }

    public void setRvcStandard(String rvcStandard) {
        this.rvcStandard = rvcStandard;
    }

    public BigDecimal getRvcRate() {
        return rvcRate;
    }

    public void setRvcRate(BigDecimal rvcRate) {
        this.rvcRate = rvcRate;
    }

    public Boolean getConditionUse() {
        return conditionUse;
    }

    public void setConditionUse(Boolean conditionUse) {
        this.conditionUse = conditionUse;
    }

    public Boolean getChoiceUse() {
        return choiceUse;
    }

    public void setChoiceUse(Boolean choiceUse) {
        this.choiceUse = choiceUse;
    }

    public List<PsrConditionDto> getConditions() {
        return conditions;
    }

    public void setConditions(List<PsrConditionDto> conditions) {
        this.conditions = conditions;
    }

    public PsrDto() {
    }

    public PsrDto(Long id, Long ftaId, FtaDto fta, Long standardItemTypeId, Long sortSn,
            StandardItemTypeDto standardItemType, String nameOfCo, String nameOfDo, String description, Boolean woUse,
            Boolean spUse, Boolean ctcUse, String ctcStandard, String deminimisStandard, BigDecimal deminimisRate,
            Boolean rvcUse, String rvcStandard, BigDecimal rvcRate, Boolean conditionUse, Boolean choiseUse,
            List<PsrConditionDto> conditions) {
        this.id = id;
        this.ftaId = ftaId;
        this.fta = fta;
        this.standardItemTypeId = standardItemTypeId;
        this.sortSn = sortSn;
        this.standardItemType = standardItemType;
        this.nameOfCo = nameOfCo;
        this.nameOfDo = nameOfDo;
        this.description = description;
        this.woUse = woUse;
        this.spUse = spUse;
        this.ctcUse = ctcUse;
        this.ctcStandard = ctcStandard;
        this.deminimisStandard = deminimisStandard;
        this.deminimisRate = deminimisRate;
        this.rvcUse = rvcUse;
        this.rvcStandard = rvcStandard;
        this.rvcRate = rvcRate;
        this.conditionUse = conditionUse;
        this.choiceUse = choiseUse;
        this.conditions = conditions != null ? conditions : new ArrayList<>();
    }

    public void setChoiceUse() {
        if (this.woUse || this.spUse) {
            this.choiceUse = true;
            return;
        }
        for (PsrConditionDto condition : conditions) {
            if ("5".equals(condition.getType())) {
                this.choiceUse = true;
                return;
            }
            if ("12".equals(condition.getType())) {
                this.choiceUse = true;
                return;
            }
        }
    }
}
