package com.ktnet.fta.judgment.psr;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ktnet.fta.judgment.dto.JudgmentDto;

@Component
public class RVCPsr {
    Logger logger = LoggerFactory.getLogger(getClass());

    private final DecimalFormat amountFormatter = new DecimalFormat("#,##0");

    public boolean judgment(Long companyId, JudgmentDto judgment) {
        if (judgment.getAmount().compareTo(new BigDecimal(0)) < 1) {
            judgment.addError("judgment.rvc.amount",
                    "[[ 판정물품 금액누락 ]]" + "<br>판매 물품의 금액 정보를 찾을 수 없습니다." + "<br>" + "<br> ** 아래 사항을 확인해 주세요."
                            + "<br>- 판매 물품 정보(수출신고내역, 매출정보)의 판매금액을 등록해주세요." + "<br>" + "<br> ** 관련 메뉴 "
                            + "<br>- 데이터업로드 (재처리) " + "<br>- 기준정보 > 매출 정보");
            return false;
        }

        if ("BD".equals(judgment.getRvcStandard())) {
            return this.judgmentBD(judgment);
        }

        if ("BU".equals(judgment.getRvcStandard())) {
            return this.judgmentBU(judgment);
        }

        if ("NC".equals(judgment.getRvcStandard())) {
            return this.judgmentNC(judgment);
        }

        if ("MC".equals(judgment.getRvcStandard())) {
            return this.judgmentMC(judgment);
        }

        return judgment.getRvcSufficient();
    }

    private boolean judgmentBD(JudgmentDto judgment) {
        // 비원산지 재료 단가오류 체크
        if (judgment.getPriceErrorNonOriginCount() > 0) {
            judgment.setRvcCalculationRate(new BigDecimal(0));
            judgment.addError("judgment.rvc.price",
                    "[[ 판정재료 단가누락 ]]" + "<br>비원산지 재료의 단가 정보를 찾을 수 없습니다."
                            + "<br>(매입정보가 연결되지 않았거나, 단가가 입력되지 않은 자재가 있습니다.)" + "<br>" + "<br> ** 아래 사항을 확인해 주세요."
                            + "<br>- 투입자재의 구매정보 및 구매단가를 등록해주세요." + "<br>" + "<br> ** 관련 메뉴 " + "<br>- 데이터업로드 (재처리) "
                            + "<br>- 기준정보 > 매입 정보");
            return false;
        }

        // ((판가 - 역외산재료비) / 판가) * 100
        BigDecimal originAmount = judgment.getAmount().subtract(judgment.getMaterialAmountNonOrigin());
        BigDecimal calculationRate = originAmount.divide(judgment.getAmount(), MathContext.DECIMAL128)
                .multiply(new BigDecimal(100));
        judgment.setRvcCalculationRate(calculationRate);

        BigDecimal standardRate = judgment.getRvcStandardRate().add(new BigDecimal(judgment.getRvcBuffer()));
        judgment.setRvcSufficient(calculationRate.compareTo(standardRate) >= 0);

        // 계산식
        StringBuilder detail = new StringBuilder();
        detail.append("(").append(amountFormatter.format(judgment.getAmount())).append(" - ")
                .append(amountFormatter.format(judgment.getMaterialAmountNonOrigin())).append(") / ")
                .append(amountFormatter.format(judgment.getAmount())).append(" * 100");
        judgment.setRvcDetail(detail.toString());

        return judgment.getRvcSufficient();
    }

    private boolean judgmentBU(final JudgmentDto judgment) {
        // 원산지 재료 단가오류 체크
        if (judgment.getPriceErrorOriginCount() > 0) {
            judgment.setRvcCalculationRate(new BigDecimal(0));
            judgment.addError("judgment.rvc.price",
                    "[[ 판정재료 단가누락 ]]" + "<br>원산지 재료의 단가 정보를 찾을 수 없습니다." + "<br>(매입정보가 연결되지 않았거나, 단가가 입력되지 않은 자재가 있습니다.)"
                            + "<br>" + "<br> ** 아래 사항을 확인해 주세요." + "<br>- 투입자재의 구매정보 및 구매단가를 등록해주세요." + "<br>"
                            + "<br> ** 관련 메뉴 " + "<br>- 데이터업로드 (재처리) " + "<br>- 기준정보 > 매입 정보");
            return false;
        }

        // (역내산재료비 / 판가) * 100
        BigDecimal originAmount = judgment.getMaterialAmountOrigin();
        BigDecimal calculationRate = originAmount.divide(judgment.getAmount(), MathContext.DECIMAL128)
                .multiply(new BigDecimal(100));
        judgment.setRvcCalculationRate(calculationRate);

        // 계산 비율이 표준 비율 이상인 조건이므로, 버퍼를 더해서 계산
        BigDecimal standardRate = judgment.getRvcStandardRate().add(new BigDecimal(judgment.getRvcBuffer()));
        judgment.setRvcSufficient(calculationRate.compareTo(standardRate) >= 0);

        // 계산식
        StringBuilder detail = new StringBuilder();
        detail.append(amountFormatter.format(judgment.getMaterialAmountOrigin())).append(" / ")
                .append(amountFormatter.format(judgment.getAmount())).append(" * 100");
        judgment.setRvcDetail(detail.toString());

        return judgment.getRvcSufficient();
    }

    private boolean judgmentNC(final JudgmentDto judgment) {
        // 원산지 재료 단가오류 체크
        if (judgment.getPriceErrorNonOriginCount() > 0) {
            judgment.setRvcCalculationRate(new BigDecimal(0));
            judgment.addError("judgment.rvc.price",
                    "[[ 판정재료 단가누락 ]]" + "<br>비원산지 재료의 단가 정보를 찾을 수 없습니다."
                            + "<br>(매입정보가 연결되지 않았거나, 단가가 입력되지 않은 자재가 있습니다.)" + "<br>" + "<br> ** 아래 사항을 확인해 주세요."
                            + "<br>- 투입자재의 구매정보 및 구매단가를 등록해주세요." + "<br>" + "<br> ** 관련 메뉴 " + "<br>- 데이터업로드 (재처리) "
                            + "<br>- 기준정보 > 매입 정보");
            return false;
        }

        // ((순원가 - 역외산재료비) / 순원가) * 100
        BigDecimal originAmount = judgment.getAmount().subtract(judgment.getMaterialAmountNonOrigin());
        BigDecimal calculationRate = originAmount.divide(judgment.getAmount(), MathContext.DECIMAL128)
                .multiply(new BigDecimal(100));
        judgment.setRvcCalculationRate(calculationRate);

        // 계산 비율이 표준 비율 이상인 조건이므로, 버퍼를 더해서 계산
        BigDecimal standardRate = judgment.getRvcStandardRate().add(new BigDecimal(judgment.getRvcBuffer()));
        judgment.setRvcSufficient(calculationRate.compareTo(standardRate) >= 0);

        // 계산식
        StringBuilder detail = new StringBuilder();
        detail.append("(").append(amountFormatter.format(judgment.getAmount())).append(" - ")
                .append(amountFormatter.format(judgment.getMaterialAmountNonOrigin())).append(") / ")
                .append(amountFormatter.format(judgment.getAmount())).append(" * 100");
        judgment.setRvcDetail(detail.toString());

        return judgment.getRvcSufficient();
    }

    private boolean judgmentMC(final JudgmentDto judgment) {
        // 원산지 재료 단가오류 체크
        if (judgment.getPriceErrorNonOriginCount() > 0) {
            judgment.setRvcCalculationRate(new BigDecimal(100));
            judgment.addError("judgment.rvc.price",
                    "[[ 판정재료 단가누락 ]]" + "<br>비원산지 재료의 단가 정보를 찾을 수 없습니다."
                            + "<br>(매입정보가 연결되지 않았거나, 단가가 입력되지 않은 자재가 있습니다.)" + "<br>" + "<br> ** 아래 사항을 확인해 주세요."
                            + "<br>- 투입자재의 구매정보 및 구매단가를 등록해주세요." + "<br>" + "<br> ** 관련 메뉴 " + "<br>- 데이터업로드 (재처리) "
                            + "<br>- 기준정보 > 매입 정보");
            return false;
        }

        // (역외산재료비 / 판가(exwork)) * 100
        BigDecimal nonOriginAmount = judgment.getMaterialAmountNonOrigin();
        BigDecimal calculationRate = nonOriginAmount.divide(judgment.getAmount(), MathContext.DECIMAL128)
                .multiply(new BigDecimal(100));
        judgment.setRvcCalculationRate(calculationRate);

        // 계산 비율이 표준 비율 이하인 조건이므로, 버퍼를 차감해서 계산
        BigDecimal standardRate = judgment.getRvcStandardRate().subtract(new BigDecimal(judgment.getRvcBuffer()));
        judgment.setRvcSufficient(calculationRate.compareTo(standardRate) <= 0);

        // 계산식
        StringBuilder detail = new StringBuilder();
        detail.append(amountFormatter.format(judgment.getMaterialAmountNonOrigin())).append(" / ")
                .append(amountFormatter.format(judgment.getAmount())).append(" * 100");
        judgment.setRvcDetail(detail.toString());

        return judgment.getRvcSufficient();
    }

}
