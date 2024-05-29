package com.ktnet.core.psr.base.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.ktnet.core.fta.dto.FtaDto;
import com.ktnet.core.item.dto.StandardItemTypeDto;
import com.ktnet.core.psr.condition.dto.PsrConditionDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
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

    @Setter
    private List<PsrConditionDto> conditions;

    @Builder
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
