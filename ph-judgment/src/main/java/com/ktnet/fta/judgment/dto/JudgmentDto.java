package com.ktnet.fta.judgment.dto;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.ktnet.core.psr.search.dto.PsrSearchItemDto;
import com.ktnet.fta.judgment.constant.DetailsType;
import com.ktnet.fta.judgment.constant.JudgmentType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class JudgmentDto {

    private static DecimalFormat amountFormatter = new DecimalFormat("#,##0.00");

    private Long id;

    private List<JudgmentErrorDetailDto> errors = new ArrayList<>();

    private List<JudgmentConditionDetailDto> conditions = new ArrayList<>();

    private JudgmentSetupDto setupDto;

    private PsrSearchItemDto psrDto;

    private Boolean useJudgment;

    // @Enumerated(EnumType.STRING)
    private DetailsType detailsType;

    // @Enumerated(EnumType.STRING)
    private JudgmentType judgmentType;

    private Long groupId;

    private Long eoId;

    private Long ftaId;

    private Long itemTypeId;

    private Long psrId;

    private Long classificationId;

    private Long sortSn;

    private String hscode;

    private String nameOfCo;

    private String nameOfDo;

    private String psrDescription;

    private Boolean doUse;

    private Boolean doSufficient;

    private Boolean woUse;

    private Boolean woSufficient;

    private Boolean spUse;

    private Boolean spSufficient;

    private Boolean ctcUse;

    private Boolean ctcSufficient;

    private String ctcStandard;

    private String deminimisStandard;

    private BigDecimal deminimisStandardRate;

    private Long deminimisBuffer = 3L;

    private BigDecimal deminimisCalculationRate;

    private Boolean deminimisSufficient;

    private Boolean rvcUse;

    private String rvcStandard;

    private BigDecimal rvcStandardRate;

    private Long rvcBuffer = 5L;

    private BigDecimal rvcCalculationRate;

    private Boolean rvcSufficient;

    private String rvcDetail;

    private Boolean conditionUse;

    private Boolean conditionSufficient;

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

    private String psrStandard;

    private Boolean sufficient = Boolean.FALSE;

    private Long itemTypeCount;

    private Boolean accmltstdr;

    private Boolean etc;

    @Builder
    public JudgmentDto(Long id, Boolean useJudgment, JudgmentType judgmentType, Long groupId, Long eoId, Long ftaId,
            Long itemTypeId, Long psrId, Long classificationId, Long sortSn, String hscode, String nameOfCo,
            String nameOfDo, String psrDescription, Boolean doUse, Boolean doSufficient, Boolean woUse,
            Boolean woSufficient, Boolean spUse, Boolean spSufficient, Boolean ctcUse, Boolean ctcSufficient,
            String ctcStandard, String deminimisStandard, BigDecimal deminimisStandardRate, Long deminimisBuffer,
            BigDecimal deminimisCalculationRate, Boolean deminimisSufficient, Boolean rvcUse, String rvcStandard,
            BigDecimal rvcStandardRate, Long rvcBuffer, BigDecimal rvcCalculationRate, Boolean rvcSufficient,
            String rvcDetail, Boolean conditionUse, Boolean conditionSufficient, BigDecimal quantity, BigDecimal amount,
            BigDecimal weight, BigDecimal materialAmountOrigin, BigDecimal materialAmountNonOrigin,
            BigDecimal materialWeightOrigin, BigDecimal materialWeightNonOrigin, Integer priceErrorOriginCount,
            Integer priceErrorNonOriginCount, Integer weightErrorOriginCount, Integer weightErrorNonOriginCount,
            Integer hscodeErrorCount, Integer ccMatchCount, Integer cthMatchCount, Integer ctshMatchCount,
            BigDecimal ccMatchAmount, BigDecimal cthMatchAmount, BigDecimal ctshMatchAmount, BigDecimal ccMatchWeight,
            BigDecimal cthMatchWeight, BigDecimal ctshMatchWeight, Integer ccMatchAmountErrorCount,
            Integer cthMatchAmountErrorCount, Integer ctshMatchAmountErrorCount, Integer ccMatchWeightErrorCount,
            Integer cthMatchWeightErrorCount, Integer ctshMatchWeightErrorCount, String psrStandard, Boolean sufficient,
            Long itemTypeCount, Boolean accmltstdr, Boolean etc) {
        this.id = id;
        this.judgmentType = judgmentType;
        this.groupId = groupId;
        this.eoId = eoId;
        this.ftaId = ftaId;
        this.itemTypeId = itemTypeId;
        this.psrId = psrId;
        this.classificationId = classificationId;
        this.sortSn = sortSn;
        this.hscode = hscode;
        this.nameOfCo = nameOfCo;
        this.nameOfDo = nameOfDo;
        this.psrDescription = psrDescription;
        this.doUse = doUse;
        this.doSufficient = doSufficient;
        this.woUse = woUse;
        this.woSufficient = woSufficient;
        this.spUse = spUse;
        this.spSufficient = spSufficient;
        this.ctcUse = ctcUse;
        this.ctcSufficient = ctcSufficient;
        this.ctcStandard = ctcStandard;
        this.deminimisStandard = deminimisStandard;
        this.deminimisStandardRate = deminimisStandardRate;
        this.deminimisBuffer = deminimisBuffer;
        this.deminimisCalculationRate = deminimisCalculationRate;
        this.deminimisSufficient = deminimisSufficient;
        this.rvcUse = rvcUse;
        this.rvcStandard = rvcStandard;
        this.rvcStandardRate = rvcStandardRate;
        this.rvcBuffer = rvcBuffer;
        this.rvcCalculationRate = rvcCalculationRate;
        this.rvcSufficient = rvcSufficient;
        this.rvcDetail = rvcDetail;
        this.conditionUse = conditionUse;
        this.conditionSufficient = conditionSufficient;
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
        this.psrStandard = psrStandard;
        this.sufficient = sufficient;
        this.itemTypeCount = itemTypeCount;
        this.accmltstdr = accmltstdr;
        this.etc = etc;
    }

    public void addError(String errorType, String contents) {
        this.errors.add(JudgmentErrorDetailDto.builder().errorType(errorType).contents(contents).build());
    }

    public JudgmentDto update(JudgmentSetupDto setupDto) {
        if (setupDto == null) {
            return this;
        }
        this.rvcBuffer = setupDto.getRvcBuffer() != null ? setupDto.getRvcBuffer() : this.rvcBuffer;
        this.deminimisBuffer = setupDto.getDeminimisBuffer() != null ? setupDto.getDeminimisBuffer()
                : this.deminimisBuffer;
        return this;
    }

    public JudgmentDto update(PsrSearchItemDto psrDto) {
        this.psrId = psrDto.getId();
        this.itemTypeId = psrDto.getStandardItemTypeId();
        this.sortSn = psrDto.getSortSn();
        this.nameOfCo = psrDto.getNameOfCo();
        this.nameOfDo = psrDto.getNameOfDo();
        this.psrDescription = psrDto.getDescription();
        this.doUse = psrDto.getWoUse();
        this.doSufficient = Boolean.FALSE;
        this.woUse = psrDto.getWoUse();
        this.woSufficient = Boolean.FALSE;
        this.spUse = psrDto.getSpUse();
        this.spSufficient = Boolean.FALSE;
        this.ctcUse = psrDto.getCtcUse();
        this.ctcSufficient = Boolean.FALSE;
        this.ctcStandard = psrDto.getCtcStandard();
        this.deminimisStandard = psrDto.getDeminimisStandard();
        this.deminimisStandardRate = psrDto.getDeminimisRate();
        this.deminimisSufficient = Boolean.FALSE;
        this.rvcUse = psrDto.getRvcUse();
        this.rvcStandard = psrDto.getRvcStandard();
        this.rvcStandardRate = psrDto.getRvcRate();
        this.rvcSufficient = Boolean.FALSE;
        this.conditionUse = psrDto.getConditionUse();
        this.conditionSufficient = Boolean.FALSE;

        // 결정기준별 처리를 위한 변수 초기화
        this.conditions.clear();
        this.errors.clear();

        // 예외기준 처리
        psrDto.getConditions().stream().forEach(item -> {
            this.conditions.add(JudgmentConditionDetailDto.builder().build().update(item));
        });
        this.accmltstdr = Boolean.FALSE;
        this.etc = Boolean.FALSE;

        return this;
    }

    public JudgmentDto updateSufficient(Boolean sufficient) {
        this.sufficient = sufficient;

        // CTC, PSR, OP, PE, Others
        if (this.woUse && this.rvcUse) {
            // WO+RVC
            this.psrStandard = "WO+RVC";
        } else if (this.woUse && this.ctcUse) {
            // CC+WO
            this.psrStandard = this.ctcStandard + "+WO";
        } else if (this.woUse) {
            // WO, WP
            this.psrStandard = this.nameOfCo;
        } else if (this.ctcUse && this.rvcUse) {
            // CC+RVC, CTH+RVC, CTSH+RVC
            this.psrStandard = this.ctcStandard + "+RVC";
        } else if (this.ctcUse && this.spUse) {
            // CC+SP
            this.psrStandard = this.ctcStandard + "+SP";
        } else if (this.ctcUse) {
            // CC, CTH, CTSH
            this.psrStandard = this.ctcStandard;
        } else if (this.rvcUse) {
            // RVC
            this.psrStandard = this.rvcStandardRate != null ? "RVC" : "";
        } else if (this.rvcUse) {
            // SP
            this.psrStandard = "SP";
        }

        if (this.rvcUse && this.rvcStandard == "BD") {
            // BD 기준
            StringBuilder builder = new StringBuilder();
            builder.append("(").append(amountFormatter.format(this.amount)).append(" - ")
                    .append(amountFormatter.format(this.materialAmountNonOrigin)).append(") / ")
                    .append(amountFormatter.format(this.amount)).append(" * 100");
            this.rvcDetail = builder.toString();
        } else if (this.rvcUse && this.rvcStandard == "BU") {
            // BU 기준
            StringBuilder builder = new StringBuilder();
            builder.append(amountFormatter.format(this.materialAmountOrigin)).append(" / ")
                    .append(amountFormatter.format(this.amount)).append(" * 100");
            this.rvcDetail = builder.toString();
        } else if (this.rvcUse && this.rvcStandard == "NC") {
            // NC 기준
            StringBuilder builder = new StringBuilder();
            builder.append("(").append(amountFormatter.format(this.amount)).append(" - ")
                    .append(amountFormatter.format(this.materialAmountNonOrigin)).append(") / ")
                    .append(amountFormatter.format(this.amount)).append(" * 100");
            this.rvcDetail = builder.toString();
        } else if (this.rvcUse && this.rvcStandard == "MC") {
            StringBuilder builder = new StringBuilder();
            builder.append(amountFormatter.format(this.materialAmountNonOrigin)).append(" / ")
                    .append(amountFormatter.format(this.amount)).append(" * 100");
            this.rvcDetail = builder.toString();
        }

        return this;
    }
}
