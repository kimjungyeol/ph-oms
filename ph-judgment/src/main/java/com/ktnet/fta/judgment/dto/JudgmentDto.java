package com.ktnet.fta.judgment.dto;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.ktnet.fta.judgment.constant.DetailsType;
import com.ktnet.fta.judgment.constant.JudgmentType;
import com.ktnet.fta.psr.dto.PsrSearchItemDto;

public class JudgmentDto {

    private static DecimalFormat amountFormatter = new DecimalFormat("#,##0.00");

    private Long id;

    private List<JudgmentErrorDetailDto> errors = new ArrayList<>();

    private List<JudgmentConditionDetailDto> conditions = new ArrayList<>();

    private JudgmentSetupDto setupDto;

    private PsrSearchItemDto psrDto;

    private Boolean useJudgment;

    private DetailsType detailsType;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<JudgmentErrorDetailDto> getErrors() {
        return errors;
    }

    public void setErrors(List<JudgmentErrorDetailDto> errors) {
        this.errors = errors;
    }

    public List<JudgmentConditionDetailDto> getConditions() {
        return conditions;
    }

    public void setConditions(List<JudgmentConditionDetailDto> conditions) {
        this.conditions = conditions;
    }

    public JudgmentSetupDto getSetupDto() {
        return setupDto;
    }

    public void setSetupDto(JudgmentSetupDto setupDto) {
        this.setupDto = setupDto;
    }

    public PsrSearchItemDto getPsrDto() {
        return psrDto;
    }

    public void setPsrDto(PsrSearchItemDto psrDto) {
        this.psrDto = psrDto;
    }

    public Boolean getUseJudgment() {
        return useJudgment;
    }

    public void setUseJudgment(Boolean useJudgment) {
        this.useJudgment = useJudgment;
    }

    public DetailsType getDetailsType() {
        return detailsType;
    }

    public void setDetailsType(DetailsType detailsType) {
        this.detailsType = detailsType;
    }

    public JudgmentType getJudgmentType() {
        return judgmentType;
    }

    public void setJudgmentType(JudgmentType judgmentType) {
        this.judgmentType = judgmentType;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getEoId() {
        return eoId;
    }

    public void setEoId(Long eoId) {
        this.eoId = eoId;
    }

    public Long getFtaId() {
        return ftaId;
    }

    public void setFtaId(Long ftaId) {
        this.ftaId = ftaId;
    }

    public Long getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(Long itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public Long getPsrId() {
        return psrId;
    }

    public void setPsrId(Long psrId) {
        this.psrId = psrId;
    }

    public Long getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(Long classificationId) {
        this.classificationId = classificationId;
    }

    public Long getSortSn() {
        return sortSn;
    }

    public void setSortSn(Long sortSn) {
        this.sortSn = sortSn;
    }

    public String getHscode() {
        return hscode;
    }

    public void setHscode(String hscode) {
        this.hscode = hscode;
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

    public String getPsrDescription() {
        return psrDescription;
    }

    public void setPsrDescription(String psrDescription) {
        this.psrDescription = psrDescription;
    }

    public Boolean getDoUse() {
        return doUse;
    }

    public void setDoUse(Boolean doUse) {
        this.doUse = doUse;
    }

    public Boolean getDoSufficient() {
        return doSufficient;
    }

    public void setDoSufficient(Boolean doSufficient) {
        this.doSufficient = doSufficient;
    }

    public Boolean getWoUse() {
        return woUse;
    }

    public void setWoUse(Boolean woUse) {
        this.woUse = woUse;
    }

    public Boolean getWoSufficient() {
        return woSufficient;
    }

    public void setWoSufficient(Boolean woSufficient) {
        this.woSufficient = woSufficient;
    }

    public Boolean getSpUse() {
        return spUse;
    }

    public void setSpUse(Boolean spUse) {
        this.spUse = spUse;
    }

    public Boolean getSpSufficient() {
        return spSufficient;
    }

    public void setSpSufficient(Boolean spSufficient) {
        this.spSufficient = spSufficient;
    }

    public Boolean getCtcUse() {
        return ctcUse;
    }

    public void setCtcUse(Boolean ctcUse) {
        this.ctcUse = ctcUse;
    }

    public Boolean getCtcSufficient() {
        return ctcSufficient;
    }

    public void setCtcSufficient(Boolean ctcSufficient) {
        this.ctcSufficient = ctcSufficient;
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

    public BigDecimal getDeminimisStandardRate() {
        return deminimisStandardRate;
    }

    public void setDeminimisStandardRate(BigDecimal deminimisStandardRate) {
        this.deminimisStandardRate = deminimisStandardRate;
    }

    public Long getDeminimisBuffer() {
        return deminimisBuffer;
    }

    public void setDeminimisBuffer(Long deminimisBuffer) {
        this.deminimisBuffer = deminimisBuffer;
    }

    public BigDecimal getDeminimisCalculationRate() {
        return deminimisCalculationRate;
    }

    public void setDeminimisCalculationRate(BigDecimal deminimisCalculationRate) {
        this.deminimisCalculationRate = deminimisCalculationRate;
    }

    public Boolean getDeminimisSufficient() {
        return deminimisSufficient;
    }

    public void setDeminimisSufficient(Boolean deminimisSufficient) {
        this.deminimisSufficient = deminimisSufficient;
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

    public BigDecimal getRvcStandardRate() {
        return rvcStandardRate;
    }

    public void setRvcStandardRate(BigDecimal rvcStandardRate) {
        this.rvcStandardRate = rvcStandardRate;
    }

    public Long getRvcBuffer() {
        return rvcBuffer;
    }

    public void setRvcBuffer(Long rvcBuffer) {
        this.rvcBuffer = rvcBuffer;
    }

    public BigDecimal getRvcCalculationRate() {
        return rvcCalculationRate;
    }

    public void setRvcCalculationRate(BigDecimal rvcCalculationRate) {
        this.rvcCalculationRate = rvcCalculationRate;
    }

    public Boolean getRvcSufficient() {
        return rvcSufficient;
    }

    public void setRvcSufficient(Boolean rvcSufficient) {
        this.rvcSufficient = rvcSufficient;
    }

    public String getRvcDetail() {
        return rvcDetail;
    }

    public void setRvcDetail(String rvcDetail) {
        this.rvcDetail = rvcDetail;
    }

    public Boolean getConditionUse() {
        return conditionUse;
    }

    public void setConditionUse(Boolean conditionUse) {
        this.conditionUse = conditionUse;
    }

    public Boolean getConditionSufficient() {
        return conditionSufficient;
    }

    public void setConditionSufficient(Boolean conditionSufficient) {
        this.conditionSufficient = conditionSufficient;
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

    public String getPsrStandard() {
        return psrStandard;
    }

    public void setPsrStandard(String psrStandard) {
        this.psrStandard = psrStandard;
    }

    public Boolean getSufficient() {
        return sufficient;
    }

    public void setSufficient(Boolean sufficient) {
        this.sufficient = sufficient;
    }

    public Long getItemTypeCount() {
        return itemTypeCount;
    }

    public void setItemTypeCount(Long itemTypeCount) {
        this.itemTypeCount = itemTypeCount;
    }

    public Boolean getAccmltstdr() {
        return accmltstdr;
    }

    public void setAccmltstdr(Boolean accmltstdr) {
        this.accmltstdr = accmltstdr;
    }

    public Boolean getEtc() {
        return etc;
    }

    public void setEtc(Boolean etc) {
        this.etc = etc;
    }

    public JudgmentDto() {

    }

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
        // this.errors.add(JudgmentErrorDetailDto.builder().errorType(errorType).contents(contents).build());
        JudgmentErrorDetailDto judgmentErrorDetailDto = new JudgmentErrorDetailDto();
        judgmentErrorDetailDto.setErrorType(errorType);
        judgmentErrorDetailDto.setContents(contents);
        this.errors.add(judgmentErrorDetailDto);
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
            // this.conditions.add(JudgmentConditionDetailDto.builder().build().update(item));

            JudgmentConditionDetailDto judgmentConditionDetailDto = new JudgmentConditionDetailDto();
            this.conditions.add(judgmentConditionDetailDto.update(item));

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
