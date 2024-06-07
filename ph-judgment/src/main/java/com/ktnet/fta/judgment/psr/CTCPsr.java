package com.ktnet.fta.judgment.psr;

import java.math.BigDecimal;
import java.math.MathContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ktnet.fta.judgment.dto.JudgmentDto;

@Component
public class CTCPsr {
    Logger logger = LoggerFactory.getLogger(getClass());

    public boolean judgment(JudgmentDto judgment) {
        logger.debug("세번변경 유형 : " + judgment.getCtcStandard());
        if (judgmentCTC(judgment)) {
            return judgment.getCtcSufficient();
        }

        return judgmentDeminimis(judgment);
    }

    private boolean judgmentCTC(JudgmentDto judgment) {
        int hsMatchCount = 0;

        if ("CC".equals(judgment.getCtcStandard())) {
            // 2자리 변경기준
            hsMatchCount = judgment.getCcMatchCount();

        } else if ("CTH".equals(judgment.getCtcStandard())) {
            // 4자리 변경기준
            hsMatchCount = judgment.getCthMatchCount();

        } else if ("CTSH".equals(judgment.getCtcStandard())) {
            // 6자리 변경기준
            hsMatchCount = judgment.getCtshMatchCount();
        }

        if (hsMatchCount == 0) {
            judgment.setCtcSufficient(Boolean.TRUE);
            return judgment.getCtcSufficient();
        }

        logger.debug("세번변경 불충족 : " + hsMatchCount + " 건의 자재 HS 일치");
        return judgment.getCtcSufficient();
    }

    private boolean judgmentDeminimis(JudgmentDto judgment) {
        logger.debug("미소기준 유형 : " + judgment.getDeminimisStandard());

        if (judgment.getHscodeErrorCount() > 0) {
            judgment.addError("judgment.deminimis.amount",
                    "[[ 미소기준 미충족 ]]" + "<br>미소기준 판정을 위해서는 모든 자재가 품목분류 되어야 합니다." + "<br>" + "<br> ** 아래 사항을 확인해 주세요."
                            + "<br>- 투입자재중 HS코드가 등록되지 않은 자재의 HS코드를 등록해 주세요." + "<br>" + "<br> ** 관련 메뉴 "
                            + "<br>- 원산지분석 > 분류대상 분석");
            return false;
        }

        if ("C".equals(judgment.getCtcStandard())) {
            // 금액 또는 중량 기준
            return judgmentDeminimisOfTypeC(judgment);

        }
        if ("B".equals(judgment.getCtcStandard())) {
            // 금액 및 CTSH 기준
            return judgmentDeminimisOfTypeB(judgment);
        }
        if ("W".equals(judgment.getCtcStandard())) {
            // 중량 기준
            return judgmentDeminimisOfTypeW(judgment);
        }

        // 금액 기준
        return judgmentDeminimisOfTypeA(judgment);
    }

    private boolean judgmentDeminimisOfTypeC(JudgmentDto judgment) {
        /*** 금액 또는 중량 중, 하나만 충족하면 되는 조건 ***/
        BigDecimal amountRate = this.getDeminimisRateByAmount(judgment);
        BigDecimal weightRate = this.getDeminimisRateByWeight(judgment);

        if (amountRate.compareTo(weightRate) < 1) {
            // 금액 미소비율이 중량 미소비율 보다 작은 경우, 금액 비율 사용
            judgment.setDeminimisStandard("A");
            judgment.setDeminimisCalculationRate(amountRate);
            // 충족 여부 설정
            judgment.setDeminimisSufficient(this.getDeminimisSufficient(judgment, amountRate));
            judgment.setCtcSufficient(judgment.getDeminimisSufficient());

            return judgment.getDeminimisSufficient();
        }

        // 중량 기준 설정
        judgment.setDeminimisStandard("W");
        judgment.setDeminimisCalculationRate(weightRate);
        // 충족 여부 설정
        judgment.setDeminimisSufficient(getDeminimisSufficient(judgment, weightRate));
        judgment.setCtcSufficient(judgment.getDeminimisSufficient());

        return judgment.getDeminimisSufficient();
    }

    private boolean judgmentDeminimisOfTypeB(JudgmentDto judgment) {
        /*** 금액 및 세번변경을 모두 충족해야 되는 조건 ***/
        BigDecimal amountRate = this.getDeminimisRateByAmount(judgment);

        // 금액 미소비율이 중량 미소비율 보다 작은 경우, 금액 비율 사용
        judgment.setDeminimisStandard("A");
        judgment.setDeminimisCalculationRate(amountRate);
        // 충족 여부 설정 (미소기준 및 세번변경(CTSH) 충족
        judgment.setDeminimisSufficient(
                getDeminimisSufficient(judgment, amountRate) && judgment.getCtshMatchCount() == 0);
        judgment.setCtcSufficient(judgment.getDeminimisSufficient());

        return judgment.getDeminimisSufficient();
    }

    private boolean judgmentDeminimisOfTypeW(JudgmentDto judgment) {
        /*** 중량 기준 조건 ***/
        BigDecimal weightRate = this.getDeminimisRateByWeight(judgment);

        // 중량 기준 설정
        judgment.setDeminimisStandard("W");
        judgment.setDeminimisCalculationRate(weightRate);
        // 충족 여부 설정
        judgment.setDeminimisSufficient(getDeminimisSufficient(judgment, weightRate));
        judgment.setCtcSufficient(judgment.getDeminimisSufficient());

        return judgment.getDeminimisSufficient();
    }

    private boolean judgmentDeminimisOfTypeA(JudgmentDto judgment) {
        /*** 금액 기준 조건 ***/
        BigDecimal amountRate = this.getDeminimisRateByAmount(judgment);

        // 금액 미소비율이 중량 미소비율 보다 작은 경우, 금액 비율 사용
        judgment.setDeminimisStandard("A");
        judgment.setDeminimisCalculationRate(amountRate);
        // 충족 여부 설정 (미소기준 및 세번변경(CTSH) 충족
        judgment.setDeminimisSufficient(getDeminimisSufficient(judgment, amountRate));
        judgment.setCtcSufficient(judgment.getDeminimisSufficient());

        return judgment.getDeminimisSufficient();
    }

    private boolean getDeminimisSufficient(final JudgmentDto judgment, BigDecimal calculationRate) {
        // 계산비율이 기준비율 보다 작아야 하는 조건이므로 버퍼를 차감
        BigDecimal standardRate = judgment.getDeminimisStandardRate()
                .subtract(new BigDecimal(judgment.getDeminimisBuffer()));
        return calculationRate.compareTo(standardRate) < 1;
    }

    private BigDecimal getDeminimisRateByAmount(final JudgmentDto judgment) {
        BigDecimal deminimisRate = new BigDecimal(100);

        if (judgment.getAmount().compareTo(new BigDecimal(0)) < 1) {
            judgment.addError("judgment.deminimis.amount",
                    "[[ 판정물품 금액누락 ]]" + "<br>판매 물품의 금액 정보를 찾을 수 없습니다." + "<br>" + "<br> ** 아래 사항을 확인해 주세요."
                            + "<br>- 판매 물품 정보(수출신고내역, 매출정보)의 판매금액을 등록해주세요." + "<br>" + "<br> ** 관련 메뉴 "
                            + "<br>- 데이터업로드 (재처리) " + "<br>- 기준정보 > 매출 정보");
            // 판매 금액 이상
            return deminimisRate;
        }

        BigDecimal matchAmount = null;
        int errorCount = 0;

        // 금액 비율 계산
        if ("CC".equals(judgment.getCtcStandard())) {
            // 2자리 변경기준
            matchAmount = judgment.getCcMatchAmount();
            errorCount = judgment.getCcMatchAmountErrorCount();

        } else if ("CTH".equals(judgment.getCtcStandard())) {
            // 4자리 변경기준
            matchAmount = judgment.getCthMatchAmount();
            errorCount = judgment.getCthMatchAmountErrorCount();

        } else if ("CTSH".equals(judgment.getCtcStandard())) {
            // 6자리 변경기준
            matchAmount = judgment.getCtshMatchAmount();
            errorCount = judgment.getCtshMatchAmountErrorCount();
        }

        if (errorCount > 0) {
            judgment.addError("judgment.deminimis.price",
                    "[[ 판정재료 단가누락 ]]" + "<br>HS 코드가 일치하는 자재의 단가를 찾을 수 없습니다."
                            + "<br>(매입정보가 연결되지 않았거나, 단가가 입력되지 않은 자재가 있습니다.)" + "<br>" + "<br> ** 아래 사항을 확인해 주세요."
                            + "<br>- 투입자재의 구매정보 및 구매단가를 등록해주세요." + "<br>" + "<br> ** 관련 메뉴 " + "<br>- 데이터업로드 (재처리) "
                            + "<br>- 기준정보 > 매입 정보");
            return deminimisRate;
        }

        logger.debug(">>>>> judgment.getCtcStandard : " + judgment.getCtcStandard());
        logger.debug(">>>>> judgment : " + judgment);
        // 미소 비율 계산
        deminimisRate = matchAmount.divide(judgment.getAmount(), MathContext.DECIMAL128).multiply(new BigDecimal(100));

        return deminimisRate;
    }

    private BigDecimal getDeminimisRateByWeight(final JudgmentDto judgment) {
        BigDecimal deminimisRate = new BigDecimal(100);

        if (judgment.getWeight().compareTo(new BigDecimal(0)) < 1) {
            judgment.addError("judgment.deminimis.weight",
                    "[[ 판정물품 중량누락 ]]" + "<br>판매 물품의 중량 정보를 찾을 수 없습니다." + "<br>"
                            + "<br>- 판매 물품 정보(수출신고내역, 매출정보)의 중량을 등록해주세요." + "<br>" + "<br> ** 관련 메뉴 "
                            + "<br>- 데이터업로드 (재처리) " + "<br>- 기준정보 > 매출 정보");
            // 판매 금액 이상
            return deminimisRate;
        }

        BigDecimal matchWeight = null;
        int errorCount = 0;

        // 금액 비율 계산
        if ("CC".equals(judgment.getCtcStandard())) {
            // 2자리 변경기준
            matchWeight = judgment.getCcMatchWeight();
            errorCount = judgment.getCcMatchWeightErrorCount();

        } else if ("CTH".equals(judgment.getCtcStandard())) {
            // 4자리 변경기준
            matchWeight = judgment.getCthMatchWeight();
            errorCount = judgment.getCthMatchWeightErrorCount();

        } else if ("CTSH".equals(judgment.getCtcStandard()) && judgment.getCtshMatchCount() == 0) {
            // 6자리 변경기준
            matchWeight = judgment.getCtshMatchWeight();
            errorCount = judgment.getCtshMatchWeightErrorCount();
        }

        if (errorCount > 0) {
            judgment.addError("judgment.deminimis.weight",
                    "[[ 판정재료 중량누락 ]]" + "<br>HS 코드가 일치(미소기준 비원산지)하는 자재의 중량을 찾을 수 없습니다."
                            + "<br>(매입정보가 연결되지 않았거나, 단위환산 관리가 되지 않은 자재가 있습니다.)" + "<br>" + "<br> ** 아래 사항을 확인해 주세요."
                            + "<br>- 투입자재의 구매정보 및 중량을 등록해주세요." + "<br>" + "<br> ** 관련 메뉴 " + "<br>- 데이터업로드 (재처리) "
                            + "<br>- 기준정보 > 매입 정보" + "<br>- 관리정보 > 단위환산 관리");
            return deminimisRate;
        }

        // 미소 비율 계산
        deminimisRate = matchWeight.divide(judgment.getWeight(), MathContext.DECIMAL128).multiply(new BigDecimal(100));

        return deminimisRate;
    }
}
