package com.ktnet.core.psr.search.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.ktnet.core.hscode.constant.HscodeType;
import com.ktnet.core.psr.condition.dto.PsrConditionDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class PsrSearchItemDto {

    private Long id;

    private Long ftaId;

    private String ftaCode;

    private String ftaName;

    // @Enumerated(EnumType.STRING)
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

    @Setter
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

    @Builder
    public PsrSearchItemDto(Long id, Long ftaId, String ftaCode, String ftaName, HscodeType hscodeType, String hscode,
            Long standardItemId, String standardItemName, String standardItemEnglishName, Long standardItemTypeId,
            String standardItemTypeCode, String standardItemTypeDescription, Long sortSn, String nameOfCo,
            String nameOfDo, String description, Boolean woUse, Boolean spUse, Boolean ctcUse, String ctcStandard,
            String deminimisStandard, BigDecimal deminimisRate, Boolean rvcUse, String rvcStandard, BigDecimal rvcRate,
            Boolean conditionUse, Boolean choiceUse, List<PsrConditionDto> conditions, BigDecimal quantity,
            BigDecimal amount, BigDecimal weight, BigDecimal materialAmountOrigin, BigDecimal materialAmountNonOrigin,
            BigDecimal materialWeightOrigin, BigDecimal materialWeightNonOrigin, Integer priceErrorOriginCount,
            Integer priceErrorNonOriginCount, Integer weightErrorOriginCount, Integer weightErrorNonOriginCount,
            Integer hscodeErrorCount, Integer ccMatchCount, Integer cthMatchCount, Integer ctshMatchCount,
            BigDecimal ccMatchAmount, BigDecimal cthMatchAmount, BigDecimal ctshMatchAmount, BigDecimal ccMatchWeight,
            BigDecimal cthMatchWeight, BigDecimal ctshMatchWeight, Integer ccMatchAmountErrorCount,
            Integer cthMatchAmountErrorCount, Integer ctshMatchAmountErrorCount, Integer ccMatchWeightErrorCount,
            Integer cthMatchWeightErrorCount, Integer ctshMatchWeightErrorCount) {
        this.id = id;
        this.ftaId = ftaId;
        this.ftaCode = ftaCode;
        this.ftaName = ftaName;
        this.hscodeType = hscodeType;
        this.hscode = hscode;
        this.standardItemId = standardItemId;
        this.standardItemName = standardItemName;
        this.standardItemEnglishName = standardItemEnglishName;
        this.standardItemTypeId = standardItemTypeId;
        this.standardItemTypeCode = standardItemTypeCode;
        this.standardItemTypeDescription = standardItemTypeDescription;
        this.sortSn = sortSn;
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
        this.choiceUse = choiceUse;
        this.conditions = conditions;
        this.quantity = quantity;
        this.amount = amount;
        this.weight = weight;
        this.materialAmountOrigin = materialAmountOrigin;
        this.materialAmountNonOrigin = materialAmountNonOrigin;
        this.materialWeightOrigin = materialWeightOrigin;
        this.materialWeightNonOrigin = materialWeightNonOrigin;
        this.priceErrorOriginCount = priceErrorOriginCount;
        this.priceErrorNonOriginCount = priceErrorNonOriginCount;
        this.weightErrorOriginCount = weightErrorOriginCount;
        this.weightErrorNonOriginCount = weightErrorNonOriginCount;
        this.hscodeErrorCount = hscodeErrorCount;
        this.ccMatchCount = ccMatchCount;
        this.cthMatchCount = cthMatchCount;
        this.ctshMatchCount = ctshMatchCount;
        this.ccMatchAmount = ccMatchAmount;
        this.cthMatchAmount = cthMatchAmount;
        this.ctshMatchAmount = ctshMatchAmount;
        this.ccMatchWeight = ccMatchWeight;
        this.cthMatchWeight = cthMatchWeight;
        this.ctshMatchWeight = ctshMatchWeight;
        this.ccMatchAmountErrorCount = ccMatchAmountErrorCount;
        this.cthMatchAmountErrorCount = cthMatchAmountErrorCount;
        this.ctshMatchAmountErrorCount = ctshMatchAmountErrorCount;
        this.ccMatchWeightErrorCount = ccMatchWeightErrorCount;
        this.cthMatchWeightErrorCount = cthMatchWeightErrorCount;
        this.ctshMatchWeightErrorCount = ctshMatchWeightErrorCount;
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
