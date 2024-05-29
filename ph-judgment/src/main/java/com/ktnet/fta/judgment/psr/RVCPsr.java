package com.ktnet.fta.judgment.psr;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ktnet.fta.judgment.dto.JudgmentDto;

@Component
public class RVCPsr {
    Logger logger = LoggerFactory.getLogger(getClass());

    private final DecimalFormat amountFormatter = new DecimalFormat("#,##0");

    public boolean judgment(JudgmentDto judgment) {
        boolean result = false;
        boolean resultBD = false;
        boolean resultBU = false;
        boolean resultNC = false;
        boolean resultMC = false;

        logger.debug("RVC PSR Judgment");

        // BD
        resultBD = this.judgmentBD(judgment);

        return result;
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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean judgment(Map<String, Object> judgmentData) {
        boolean result = false;
        boolean resultBD = false;
        boolean resultBU = false;
        boolean resultNC = false;
        boolean resultMC = false;

        logger.debug("RVC PSR Judgment");

        // BD
        resultBD = this.judgmentBD(judgmentData);
        // BU
        resultBU = this.judgmentBU(judgmentData);
        // NC
        resultNC = this.judgmentNC(judgmentData);
        // MC
        resultMC = this.judgmentMC(judgmentData);

        if (resultBD || resultBU || resultNC || resultMC) {
            result = true;
        }

        return result;
    }

    private boolean judgmentBD(Map<String, Object> judgmentData) {
        boolean resultBD = false;

        // ((판가 - 역외산재료비) / 판가) * 100
        BigDecimal amount = new BigDecimal(String.valueOf(judgmentData.get("amount"))); // 판가
        BigDecimal materialAmountNonOrigin = new BigDecimal(
                String.valueOf(judgmentData.get("materialAmountNonOrigin"))); // 역외산재료비
        BigDecimal originAmount = amount.subtract(materialAmountNonOrigin); // (판가 - 역외산재료비)
        BigDecimal calculationRate = originAmount.divide(amount, MathContext.DECIMAL128).multiply(new BigDecimal(100)); // 부가가치비율
        BigDecimal rvcStandardRate; // 기준비율
        BigDecimal rvcBufferRate = BigDecimal.ZERO; // 버퍼비율

        // 기준 비율 확인 필요
        if (judgmentData.get("rvcStandardRate").toString().isEmpty()) {
            rvcStandardRate = new BigDecimal(70);
        } else {
            rvcStandardRate = new BigDecimal(String.valueOf(judgmentData.get("rvcStandardRate")));
        }

        // 버퍼 없음
        // 계산 비율이 표준 비율 이상인 조건이므로, 버퍼를 더해서 계산
        rvcStandardRate = rvcStandardRate.add(rvcBufferRate);

        // 계산
        resultBD = calculationRate.compareTo(rvcStandardRate) >= 0;

        // 계산식
        StringBuilder detail = new StringBuilder();
        detail.append("(").append(amountFormatter.format(amount)).append(" - ")
                .append(amountFormatter.format(materialAmountNonOrigin)).append(") / ")
                .append(amountFormatter.format(amount)).append(" * 100");

        return resultBD;

        // return false;
    }

    private boolean judgmentBU(Map<String, Object> judgmentData) {
        boolean resultBU = false;

        // (역내산재료비 / 판가) * 100
        BigDecimal originAmount = new BigDecimal(String.valueOf(judgmentData.get("materialAmountOrigin"))); // 역내산재료비
        BigDecimal amount = new BigDecimal(String.valueOf(judgmentData.get("amount"))); // 판가
        BigDecimal calculationRate = originAmount.divide(amount, MathContext.DECIMAL128).multiply(new BigDecimal(100)); // 부가가치비율
        BigDecimal rvcStandardRate; // 기준비율
        BigDecimal rvcBufferRate = BigDecimal.ZERO; // 버퍼비율

        // 기준 비율 확인 필요
        if (judgmentData.get("rvcStandardRate").toString().isEmpty()) {
            rvcStandardRate = new BigDecimal(70);
        } else {
            rvcStandardRate = new BigDecimal(String.valueOf(judgmentData.get("rvcStandardRate")));
        }

        // 버퍼 없음
        // 계산 비율이 표준 비율 이상인 조건이므로, 버퍼를 더해서 계산
        rvcStandardRate = rvcStandardRate.add(rvcBufferRate);

        // 계산
        resultBU = calculationRate.compareTo(rvcStandardRate) >= 0;

        // 계산식
        StringBuilder detail = new StringBuilder();
        detail.append(amountFormatter.format(originAmount)).append(" / ").append(amountFormatter.format(amount))
                .append(" * 100");

        return resultBU;
    }

    private boolean judgmentNC(Map<String, Object> judgmentData) {
        boolean resultNC = false;

        // ((순원가 - 역외산재료비) / 순원가) * 100
        BigDecimal amount = new BigDecimal(String.valueOf(judgmentData.get("amount"))); // 순원가
        BigDecimal materialAmountNonOrigin = new BigDecimal(
                String.valueOf(judgmentData.get("materialAmountNonOrigin"))); // 역외산재료비
        BigDecimal originAmount = amount.subtract(materialAmountNonOrigin); // (순원가 - 역외산재료비)
        BigDecimal calculationRate = originAmount.divide(amount, MathContext.DECIMAL128).multiply(new BigDecimal(100));
        BigDecimal rvcStandardRate; // 기준비율
        BigDecimal rvcBufferRate = BigDecimal.ZERO; // 버퍼비율

        // 기준 비율 확인 필요
        if (judgmentData.get("rvcStandardRate").toString().isEmpty()) {
            rvcStandardRate = new BigDecimal(70);
        } else {
            rvcStandardRate = new BigDecimal(String.valueOf(judgmentData.get("rvcStandardRate")));
        }

        // 버퍼 없음
        // 계산 비율이 표준 비율 이상인 조건이므로, 버퍼를 더해서 계산
        rvcStandardRate = rvcStandardRate.add(rvcBufferRate);

        // 계산
        resultNC = calculationRate.compareTo(rvcStandardRate) >= 0;

        // 계산식
        StringBuilder detail = new StringBuilder();
        detail.append("(").append(amountFormatter.format(amount)).append(" - ")
                .append(amountFormatter.format(materialAmountNonOrigin)).append(") / ")
                .append(amountFormatter.format(amount)).append(" * 100");

        return resultNC;
    }

    private boolean judgmentMC(Map<String, Object> judgmentData) {
        boolean resultMC = false;

        // (역외산재료비 / 판가(exwork)) * 100
        BigDecimal materialAmountNonOrigin = new BigDecimal(
                String.valueOf(judgmentData.get("materialAmountNonOrigin"))); // 역외산재료비
        BigDecimal amount = new BigDecimal(String.valueOf(judgmentData.get("amount"))); // 판가(exwork)
        BigDecimal calculationRate = materialAmountNonOrigin.divide(amount, MathContext.DECIMAL128)
                .multiply(new BigDecimal(100));
        BigDecimal rvcStandardRate; // 기준비율
        BigDecimal rvcBufferRate = BigDecimal.ZERO; // 버퍼비율

        // 기준 비율 확인 필요
        if (judgmentData.get("rvcStandardRate").toString().isEmpty()) {
            rvcStandardRate = new BigDecimal(70);
        } else {
            rvcStandardRate = new BigDecimal(String.valueOf(judgmentData.get("rvcStandardRate")));
        }

        // 버퍼 없음
        // 계산 비율이 표준 비율 이하인 조건이므로, 버퍼를 차감해서 계산
        rvcStandardRate = rvcStandardRate.subtract(rvcBufferRate);

        // 계산
        resultMC = calculationRate.compareTo(rvcStandardRate) <= 0;

        // 계산식
        StringBuilder detail = new StringBuilder();
        detail.append(amountFormatter.format(materialAmountNonOrigin)).append(" / ")
                .append(amountFormatter.format(amount)).append(" * 100");

        return resultMC;
    }

    public boolean judgment(Long companyId, JudgmentDto judgment) {
        // TODO Auto-generated method stub
        return false;
    }
}
