package com.ktnet.fta.psr.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.ktnet.core.hscode.constant.HscodeType;

import lombok.ToString;

@ToString
public class PsrSearchItemDto {

    private Long id;

    private Long ftaId;

    private String ftaCode;

    private String ftaName;

    private HscodeType hscodeType;

    private String hscode;

    private Long standardItemId;

    private String standardItemName;

    private String standardItemEnglishName;

    private Long standardItemTypeId;

    private String standardItemTypeCode;

    private String standardItemTypeDescription;

    private Long ftaPsrId;

    private Long sortSn;

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

    private List<PsrConditionDto> conditions = new ArrayList<>();

    /*** 판정 참조 정보 ***/
    private BigDecimal quantity;

    private BigDecimal amount;

    private BigDecimal weight;

    private BigDecimal materialAmountOrigin;

    private BigDecimal materialAmountNonOrigin;

    private BigDecimal materialWeightOrigin;

    private BigDecimal materialWeightNonOrigin;

    private Integer priceErrorOriginCount;

    private Integer priceErrorNonOriginCount;

    private Integer weightErrorOriginCount;

    private Integer weightErrorNonOriginCount;

    private Integer hscodeErrorCount;

    private Integer ccMatchCount;

    private Integer cthMatchCount;

    private Integer ctshMatchCount;

    private BigDecimal ccMatchAmount;

    private BigDecimal cthMatchAmount;

    private BigDecimal ctshMatchAmount;

    private BigDecimal ccMatchWeight;

    private BigDecimal cthMatchWeight;

    private BigDecimal ctshMatchWeight;

    private Integer ccMatchAmountErrorCount;

    private Integer cthMatchAmountErrorCount;

    private Integer ctshMatchAmountErrorCount;

    private Integer ccMatchWeightErrorCount;

    private Integer cthMatchWeightErrorCount;

    private Integer ctshMatchWeightErrorCount;

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

    public String getFtaCode() {
        return ftaCode;
    }

    public void setFtaCode(String ftaCode) {
        this.ftaCode = ftaCode;
    }

    public String getFtaName() {
        return ftaName;
    }

    public void setFtaName(String ftaName) {
        this.ftaName = ftaName;
    }

    public HscodeType getHscodeType() {
        return hscodeType;
    }

    public void setHscodeType(HscodeType hscodeType) {
        this.hscodeType = hscodeType;
    }

    public String getHscode() {
        return hscode;
    }

    public void setHscode(String hscode) {
        this.hscode = hscode;
    }

    public Long getStandardItemId() {
        return standardItemId;
    }

    public void setStandardItemId(Long standardItemId) {
        this.standardItemId = standardItemId;
    }

    public String getStandardItemName() {
        return standardItemName;
    }

    public void setStandardItemName(String standardItemName) {
        this.standardItemName = standardItemName;
    }

    public String getStandardItemEnglishName() {
        return standardItemEnglishName;
    }

    public void setStandardItemEnglishName(String standardItemEnglishName) {
        this.standardItemEnglishName = standardItemEnglishName;
    }

    public Long getStandardItemTypeId() {
        return standardItemTypeId;
    }

    public void setStandardItemTypeId(Long standardItemTypeId) {
        this.standardItemTypeId = standardItemTypeId;
    }

    public String getStandardItemTypeCode() {
        return standardItemTypeCode;
    }

    public void setStandardItemTypeCode(String standardItemTypeCode) {
        this.standardItemTypeCode = standardItemTypeCode;
    }

    public String getStandardItemTypeDescription() {
        return standardItemTypeDescription;
    }

    public void setStandardItemTypeDescription(String standardItemTypeDescription) {
        this.standardItemTypeDescription = standardItemTypeDescription;
    }

    public Long getFtaPsrId() {
        return ftaPsrId;
    }

    public void setFtaPsrId(Long ftaPsrId) {
        this.ftaPsrId = ftaPsrId;
    }

    public Long getSortSn() {
        return sortSn;
    }

    public void setSortSn(Long sortSn) {
        this.sortSn = sortSn;
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

    public List<PsrConditionDto> getConditions() {
        return conditions;
    }

    public void setConditions(List<PsrConditionDto> conditions) {
        this.conditions = conditions;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getMaterialAmountOrigin() {
        return materialAmountOrigin;
    }

    public void setMaterialAmountOrigin(BigDecimal materialAmountOrigin) {
        this.materialAmountOrigin = materialAmountOrigin;
    }

    public BigDecimal getMaterialAmountNonOrigin() {
        return materialAmountNonOrigin;
    }

    public void setMaterialAmountNonOrigin(BigDecimal materialAmountNonOrigin) {
        this.materialAmountNonOrigin = materialAmountNonOrigin;
    }

    public BigDecimal getMaterialWeightOrigin() {
        return materialWeightOrigin;
    }

    public void setMaterialWeightOrigin(BigDecimal materialWeightOrigin) {
        this.materialWeightOrigin = materialWeightOrigin;
    }

    public BigDecimal getMaterialWeightNonOrigin() {
        return materialWeightNonOrigin;
    }

    public void setMaterialWeightNonOrigin(BigDecimal materialWeightNonOrigin) {
        this.materialWeightNonOrigin = materialWeightNonOrigin;
    }

    public Integer getPriceErrorOriginCount() {
        return priceErrorOriginCount;
    }

    public void setPriceErrorOriginCount(Integer priceErrorOriginCount) {
        this.priceErrorOriginCount = priceErrorOriginCount;
    }

    public Integer getPriceErrorNonOriginCount() {
        return priceErrorNonOriginCount;
    }

    public void setPriceErrorNonOriginCount(Integer priceErrorNonOriginCount) {
        this.priceErrorNonOriginCount = priceErrorNonOriginCount;
    }

    public Integer getWeightErrorOriginCount() {
        return weightErrorOriginCount;
    }

    public void setWeightErrorOriginCount(Integer weightErrorOriginCount) {
        this.weightErrorOriginCount = weightErrorOriginCount;
    }

    public Integer getWeightErrorNonOriginCount() {
        return weightErrorNonOriginCount;
    }

    public void setWeightErrorNonOriginCount(Integer weightErrorNonOriginCount) {
        this.weightErrorNonOriginCount = weightErrorNonOriginCount;
    }

    public Integer getHscodeErrorCount() {
        return hscodeErrorCount;
    }

    public void setHscodeErrorCount(Integer hscodeErrorCount) {
        this.hscodeErrorCount = hscodeErrorCount;
    }

    public Integer getCcMatchCount() {
        return ccMatchCount;
    }

    public void setCcMatchCount(Integer ccMatchCount) {
        this.ccMatchCount = ccMatchCount;
    }

    public Integer getCthMatchCount() {
        return cthMatchCount;
    }

    public void setCthMatchCount(Integer cthMatchCount) {
        this.cthMatchCount = cthMatchCount;
    }

    public Integer getCtshMatchCount() {
        return ctshMatchCount;
    }

    public void setCtshMatchCount(Integer ctshMatchCount) {
        this.ctshMatchCount = ctshMatchCount;
    }

    public BigDecimal getCcMatchAmount() {
        return ccMatchAmount;
    }

    public void setCcMatchAmount(BigDecimal ccMatchAmount) {
        this.ccMatchAmount = ccMatchAmount;
    }

    public BigDecimal getCthMatchAmount() {
        return cthMatchAmount;
    }

    public void setCthMatchAmount(BigDecimal cthMatchAmount) {
        this.cthMatchAmount = cthMatchAmount;
    }

    public BigDecimal getCtshMatchAmount() {
        return ctshMatchAmount;
    }

    public void setCtshMatchAmount(BigDecimal ctshMatchAmount) {
        this.ctshMatchAmount = ctshMatchAmount;
    }

    public BigDecimal getCcMatchWeight() {
        return ccMatchWeight;
    }

    public void setCcMatchWeight(BigDecimal ccMatchWeight) {
        this.ccMatchWeight = ccMatchWeight;
    }

    public BigDecimal getCthMatchWeight() {
        return cthMatchWeight;
    }

    public void setCthMatchWeight(BigDecimal cthMatchWeight) {
        this.cthMatchWeight = cthMatchWeight;
    }

    public BigDecimal getCtshMatchWeight() {
        return ctshMatchWeight;
    }

    public void setCtshMatchWeight(BigDecimal ctshMatchWeight) {
        this.ctshMatchWeight = ctshMatchWeight;
    }

    public Integer getCcMatchAmountErrorCount() {
        return ccMatchAmountErrorCount;
    }

    public void setCcMatchAmountErrorCount(Integer ccMatchAmountErrorCount) {
        this.ccMatchAmountErrorCount = ccMatchAmountErrorCount;
    }

    public Integer getCthMatchAmountErrorCount() {
        return cthMatchAmountErrorCount;
    }

    public void setCthMatchAmountErrorCount(Integer cthMatchAmountErrorCount) {
        this.cthMatchAmountErrorCount = cthMatchAmountErrorCount;
    }

    public Integer getCtshMatchAmountErrorCount() {
        return ctshMatchAmountErrorCount;
    }

    public void setCtshMatchAmountErrorCount(Integer ctshMatchAmountErrorCount) {
        this.ctshMatchAmountErrorCount = ctshMatchAmountErrorCount;
    }

    public Integer getCcMatchWeightErrorCount() {
        return ccMatchWeightErrorCount;
    }

    public void setCcMatchWeightErrorCount(Integer ccMatchWeightErrorCount) {
        this.ccMatchWeightErrorCount = ccMatchWeightErrorCount;
    }

    public Integer getCthMatchWeightErrorCount() {
        return cthMatchWeightErrorCount;
    }

    public void setCthMatchWeightErrorCount(Integer cthMatchWeightErrorCount) {
        this.cthMatchWeightErrorCount = cthMatchWeightErrorCount;
    }

    public Integer getCtshMatchWeightErrorCount() {
        return ctshMatchWeightErrorCount;
    }

    public void setCtshMatchWeightErrorCount(Integer ctshMatchWeightErrorCount) {
        this.ctshMatchWeightErrorCount = ctshMatchWeightErrorCount;
    }

    public void setChoiceUse(Boolean choiceUse) {
        this.choiceUse = choiceUse;
    }

    public Boolean getChoiceUse() {
        if (this.woUse || this.spUse) {
            this.choiceUse = true;
            return this.choiceUse;
        }
        for (PsrConditionDto condition : conditions) {
            if ("2".equals(condition.getType())) {
                this.choiceUse = true;
                return this.choiceUse;
            }
            if ("5".equals(condition.getType())) {
                this.choiceUse = true;
                return this.choiceUse;
            }
            if ("12".equals(condition.getType())) {
                this.choiceUse = true;
                return this.choiceUse;
            }
        }
        return false;
    }
}
