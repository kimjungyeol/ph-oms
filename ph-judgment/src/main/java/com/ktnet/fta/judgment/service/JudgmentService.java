package com.ktnet.fta.judgment.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktnet.core.fta.dto.FtaDto;
import com.ktnet.fta.judgment.constant.JudgmentType;
import com.ktnet.fta.judgment.dto.JudgmentDto;
import com.ktnet.fta.judgment.psr.CTCPsr;
import com.ktnet.fta.judgment.psr.ConditionPsr;
import com.ktnet.fta.judgment.psr.DOPsr;
import com.ktnet.fta.judgment.psr.RVCPsr;
import com.ktnet.fta.judgment.psr.SPPsr;
import com.ktnet.fta.judgment.psr.WOPsr;

@Service
public class JudgmentService {

    /*** Mapper ***/

    /*** PSR ***/
    @Autowired
    WOPsr woPsr;
    @Autowired
    SPPsr spPsr;
    @Autowired
    CTCPsr ctcPsr;
    @Autowired
    RVCPsr rvcPsr;
    @Autowired
    ConditionPsr conditionPsr;
    @Autowired
    DOPsr doPsr;

    public String test() {
        return "judgment api post test";
    }

    public boolean simulationExecute(Map<String, Object> ftaInfo, List<Map<String, Object>> itemList) {
        boolean result = false;

        // 임시 값임
        Long tempCompanyId = -100L;

        JudgmentDto judgmentDto = this.generateJudgmentDto(ftaInfo, itemList);
        FtaDto ftaDto = new FtaDto();

        this.judgment(tempCompanyId, judgmentDto);

        if ("RVC".equals(judgmentDto.getPsrStandard())) {
            result = rvcPsr.judgment(judgmentDto);
        }

        if ("RVC".equals(ftaInfo.get("psrStandard"))) {
            // result = rvcPsr.judgment(judgmentDto);
        }

        if ("CTH".equals(ftaInfo.get("psrStandard"))) {
            // result = ctcPsr.judgment(judgmentDto);
        }

        return result;
    }

    private JudgmentDto generateJudgmentDto(Map<String, Object> ftaInfo, List<Map<String, Object>> itemList) {
        BigDecimal amount = new BigDecimal(String.valueOf(ftaInfo.get("amount"))); // 제품 amount
        String psrStandard = String.valueOf(ftaInfo.get("psrStandard"));
        BigDecimal rvcStandardRate = new BigDecimal(String.valueOf(ftaInfo.get("rvcStandardRate")));

        String hsCode = String.valueOf(ftaInfo.get("hsCode")); // 제품 HS Code
        BigDecimal materialAmountOrigin = BigDecimal.ZERO; // 원산지 재료비
        BigDecimal materialAmountNonOrigin = BigDecimal.ZERO; // 비원산지 재료비
        int priceErrorOriginCount = 0; // 원산지 재료비 오류
        int priceErrorNonOriginCount = 0; // 비원산지 재료비 오류

        /* 세번변경 기준 계산을 위한 변수 */
        int hscodeErrorCount = 0; // HS 오류 카운트
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

            // 원산지 재료비 오류
            if ("Y".equals(itemOrigin) && (itemAmount == null || itemAmount.equals(BigDecimal.ZERO))) {
                priceErrorOriginCount++;
            }

            // 비원산지 재료비 오류
            if ("N".equals(itemOrigin) && (itemAmount == null || itemAmount.equals(BigDecimal.ZERO))) {
                priceErrorNonOriginCount++;
            }

            // HS 오류 카운트
            if (itemHsCode.length() < 6) {
                hscodeErrorCount++;
            }

            // 2자리 일치 카운트 ***
            if (itemHsCode.startsWith(hsCode.substring(0, 2))) {
                ccMatchCount++;
            }

            // 4자리 일치 카운트 ***
            if (itemHsCode.startsWith(hsCode.substring(0, 4))) {
                cthMatchCount++;
            }

            // 6자리 일치 카운트 ***
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

        // PSR 조회 추가 필요

//        JudgmentDto judgmentDto2 = JudgmentDto.builder().amount(amount).psrStandard(psrStandard)
//                .rvcStandardRate(rvcStandardRate).materialAmountOrigin(materialAmountOrigin)
//                .materialAmountNonOrigin(materialAmountNonOrigin).hscodeErrorCount(hscodeErrorCount)
//                .ccMatchCount(ccMatchCount).cthMatchCount(cthMatchCount).ctshMatchCount(ctshMatchCount)
//                .ccMatchAmount(ccMatchAmount).cthMatchAmount(cthMatchAmount).ctshMatchAmount(ctshMatchAmount)
//                .deminimisBuffer(3L).rvcBuffer(5L).sufficient(Boolean.FALSE).doSufficient(Boolean.FALSE)
//                .woSufficient(Boolean.FALSE).spSufficient(Boolean.FALSE).ctcSufficient(Boolean.FALSE)
//                .deminimisSufficient(Boolean.FALSE).rvcSufficient(Boolean.FALSE).conditionSufficient(Boolean.FALSE)
//                .accmltstdr(Boolean.FALSE).etc(Boolean.FALSE).priceErrorOriginCount(priceErrorOriginCount)
//                .priceErrorNonOriginCount(priceErrorNonOriginCount).build();

        JudgmentDto judgmentDto = new JudgmentDto();

        judgmentDto.setAmount(amount);
        judgmentDto.setPsrStandard(psrStandard);
        judgmentDto.setRvcStandardRate(rvcStandardRate);
        judgmentDto.setMaterialAmountOrigin(materialAmountOrigin);
        judgmentDto.setMaterialAmountNonOrigin(materialAmountNonOrigin);
        judgmentDto.setHscodeErrorCount(hscodeErrorCount);
        judgmentDto.setCcMatchCount(ccMatchCount);
        judgmentDto.setCthMatchCount(cthMatchCount);
        judgmentDto.setCtshMatchCount(ctshMatchCount);
        judgmentDto.setCcMatchAmount(ccMatchAmount);
        judgmentDto.setCthMatchAmount(cthMatchAmount);
        judgmentDto.setCtshMatchAmount(ctshMatchAmount);
        judgmentDto.setDeminimisBuffer(3L);
        judgmentDto.setRvcBuffer(5L);
        judgmentDto.setSufficient(Boolean.FALSE);
        judgmentDto.setDoSufficient(Boolean.FALSE);
        judgmentDto.setWoSufficient(Boolean.FALSE);
        judgmentDto.setSpSufficient(Boolean.FALSE);
        judgmentDto.setCtcSufficient(Boolean.FALSE);
        judgmentDto.setDeminimisSufficient(Boolean.FALSE);
        judgmentDto.setRvcSufficient(Boolean.FALSE);
        judgmentDto.setConditionSufficient(Boolean.FALSE);
        judgmentDto.setAccmltstdr(Boolean.FALSE);
        judgmentDto.setEtc(Boolean.FALSE);
        judgmentDto.setPriceErrorOriginCount(priceErrorOriginCount);
        judgmentDto.setPriceErrorNonOriginCount(priceErrorNonOriginCount);

        // PSR 조회 후 update 해줘야함
        // 1. deminimisBuffer (미소기준 버퍼)
        // 2. rvcBuffer (부가가치기준 버퍼)
        // 3. 예외기준??

//        JudgmentDto judgmentDto = JudgmentDto.builder().amount(amount).psrStandard(psrStandard)
//                .rvcStandardRate(rvcStandardRate).materialAmountOrigin(materialAmountOrigin)
//                .materialAmountNonOrigin(materialAmountNonOrigin).hscodeErrorCount(hscodeErrorCount)
//                .ccMatchCount(ccMatchCount).cthMatchCount(cthMatchCount).ctshMatchCount(ctshMatchCount)
//                .ccMatchAmount(ccMatchAmount).cthMatchAmount(cthMatchAmount).ctshMatchAmount(ctshMatchAmount).build();

        return judgmentDto;
    }

    private void judgment(final Long companyId, final JudgmentDto judgment) {
        boolean result = true;

        if (judgment.getJudgmentType().equals(JudgmentType.PURCHASE)) {
            // 상품 판정
            // result = this.doPsr.judgment(judgment);
            judgment.updateSufficient(result);
            return;
        }

        // 완전 생산 기준 판정
        if (judgment.getWoUse()) {
            // result = result && this.woPsr.judgment(companyId, judgment);
        }

        // 가공공정 기준 판정
        if (judgment.getSpUse()) {
            // result = result && this.spPsr.judgment(companyId, judgment);
        }

        // 세번변경 기준 판정
        if (judgment.getCtcUse()) {
            // result = result && this.ctcPsr.judgment(companyId, judgment);
        }

        // 부가가치 기준 판정
        if (judgment.getRvcUse()) {
            // result = result && this.rvcPsr.judgment(companyId, judgment);
        }

        // 예외기준 판정
        if (judgment.getConditionUse()) {
            // result = result && this.conditionPsr.judgment(companyId, judgment);
        }

        // 최종 결과 반영
        judgment.updateSufficient(result);

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean simulationExecute2(Map<String, Object> ftaInfo, List<Map<String, Object>> itemList) {
        boolean result = false;

        Map<String, Object> judgmentData = this.generateJudgmentData(ftaInfo, itemList);

        if ("RVC".equals(ftaInfo.get("psrStandard"))) {
            // result = rvcPsr.judgment(judgmentData);
        }

        if ("CTH".equals(ftaInfo.get("psrStandard"))) {
            // result = ctcPsr.judgment(judgmentData);
        }

        return result;
    }

    public void judgmentExecute(Map<String, Object> reqMap) {
        // TODO Auto-generated method stub

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
