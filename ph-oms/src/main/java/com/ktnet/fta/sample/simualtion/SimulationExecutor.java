package com.ktnet.fta.sample.simualtion;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktnet.fta.sample.simualtion.psr.CTCPsr;
import com.ktnet.fta.sample.simualtion.psr.RVCPsr;

@Service
public class SimulationExecutor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RVCPsr rvcPsr;

    @Autowired
    CTCPsr ctcPsr;

    // private final RVCPsr rvcPsr;

    public boolean execute(Map<String, Object> ftaInfo, List<Map<String, Object>> itemList) {
        boolean result = false;

        Map<String, Object> judgmentData = this.generateJudgmentData(ftaInfo, itemList);

        if ("RVC".equals(ftaInfo.get("psrStandard"))) {
            result = rvcPsr.judgment(judgmentData);
        }

        if ("CTH".equals(ftaInfo.get("psrStandard"))) {
            result = ctcPsr.judgment(judgmentData);
        }

        // 1. 세번변경 기준 판정
        // 1.1 기준별 세번 일치건수 계산
        // 1.2 CC 기준
        // 1.3 CTH 기준
        // 1.4 CTSH 기준

        // 2. 미소기준 판정

        // 3. 부가가치 기준 판정
        // 3.1 BD
        // 3.2 BU
        // 3.3 NC
        // 3.4 MC

        return result;
    }

    private Map<String, Object> generateJudgmentData(Map<String, Object> ftaInfo, List<Map<String, Object>> itemList) {
        Map<String, Object> judgmentData = new HashMap<String, Object>();

        String hsCode = String.valueOf(ftaInfo.get("hsCode")); // 제품 HS Code
        BigDecimal materialAmountOrigin = BigDecimal.ZERO; // 원산지 재료비
        BigDecimal materialAmountNonOrigin = BigDecimal.ZERO; // 비원산지 재료비

        /* 세번변경 기준 계산을 위한 변수 */
        int hsCodeErrorCount = 0; // HS 오류 카운트
        int ccMatchCount = 0; // 2자리 일치 카운트
        int cthMatchCount = 0; // 4자리 일치 카운트
        int ctshMatchCount = 0; // 6자리 일치 카운트

        /* 미소기준 계산을 위한 변수 */
        BigDecimal ccMatchAmount = BigDecimal.ZERO; // 2자리 일치 비원산지 재료비
        BigDecimal cthMatchAmount = BigDecimal.ZERO; // 4자리 일치 비원산지 재료비
        BigDecimal ctshMatchAmount = BigDecimal.ZERO; // 6자리 일치 비원산지 재료비

        for (Map<String, Object> itemData : itemList) {
            BigDecimal itemAmount = new BigDecimal(String.valueOf(itemData.get("amount")));
            String itemHsCode = String.valueOf(itemData.get("hsCode"));
            String itemOrigin = String.valueOf(itemData.get("origin"));

            // 원산지, 비원산지 재료비
            if ("Y".equals(itemOrigin)) {
                materialAmountOrigin = materialAmountOrigin.add(itemAmount);
            } else {
                materialAmountNonOrigin = materialAmountNonOrigin.add(itemAmount);
            }

            // HS 오류 카운트
            if (itemHsCode.length() < 6) {
                hsCodeErrorCount++;
            }

            // 2자리 일치 카운트
            if (itemHsCode.startsWith(hsCode.substring(0, 2))) {
                ccMatchCount++;
            }

            // 4자리 일치 카운트
            if (itemHsCode.startsWith(hsCode.substring(0, 4))) {
                cthMatchCount++;
            }

            // 6자리 일치 카운트
            if (itemHsCode.startsWith(hsCode.substring(0, 6))) {
                ctshMatchCount++;
            }

            // 2자리 일치 비원산지 재료비
            if ("N".equals(itemOrigin) && itemHsCode.startsWith(hsCode.substring(0, 2))) {
                ccMatchAmount = ccMatchAmount.add(itemAmount);
            }

            // 4자리 일치 비원산지 재료비
            if ("N".equals(itemOrigin) && itemHsCode.startsWith(hsCode.substring(0, 4))) {
                cthMatchAmount = cthMatchAmount.add(itemAmount);
            }

            // 6자리 일치 비원산지 재료비
            if ("N".equals(itemOrigin) && itemHsCode.startsWith(hsCode.substring(0, 6))) {
                ctshMatchAmount = ctshMatchAmount.add(itemAmount);
            }
        }

        judgmentData.put("amount", ftaInfo.get("amount"));
        judgmentData.put("psrStandard", ftaInfo.get("psrStandard"));
        judgmentData.put("rvcStandardRate", ftaInfo.get("rvcStandardRate"));

        judgmentData.put("materialAmountOrigin", materialAmountOrigin);
        judgmentData.put("materialAmountNonOrigin", materialAmountNonOrigin);
        judgmentData.put("hscodeErrorCount", hsCodeErrorCount);
        judgmentData.put("ccMatchCount", ccMatchCount);
        judgmentData.put("cthMatchCount", cthMatchCount);
        judgmentData.put("ctshMatchCount", ctshMatchCount);
        judgmentData.put("ccMatchAmount", ccMatchAmount);
        judgmentData.put("cthMatchAmount", cthMatchAmount);
        judgmentData.put("ctshMatchAmount", ctshMatchAmount);

        // 1. 원산지 재료비 materialAmountOrigin
        // 2. 비원산지 재료비 materialAmountNonOrigin

        /* 세번변경 기준 계산을 위한 변수 */
        // !! 역내산일 경우 HS CODE 가 동일해도 되는지 확인 필요
        // 3. HS 오류 카운트 hscodeErrorCount
        // 4. 2자리 일치 카운트 ccMatchCount
        // 5. 4자리 일치 카운트 cthMatchCount
        // 6. 6자리 일치 카운트 ctshMatchCount

        /* 미소기준 계산을 위한 변수 */
        // 7. 2자리 일치 비원산지 재료비 ccMatchAmount
        // 8. 4자리 일치 비원산지 재료비 cthMatchAmount
        // 9. 6자리 일치 비원산지 재료비 ctshMatchAmount

        return judgmentData;
    }

}
