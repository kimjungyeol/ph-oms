package com.ktnet.fta.sample.simualtion.psr;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RVCPsr {
    Logger logger = LoggerFactory.getLogger(getClass());

    private final DecimalFormat amountFormatter = new DecimalFormat("#,##0");

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
}
