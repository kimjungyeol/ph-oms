package com.ktnet.fta.simulation.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktnet.fta.judgment.dto.JudgmentDto;
import com.ktnet.fta.psr.dto.PsrSearchParamsDto;
import com.ktnet.fta.psr.dto.PsrSearchResultDto;
import com.ktnet.fta.psr.service.PsrService;

@Service("simulationService")
public class SimulationService {

    @Autowired
    private PsrService psrService;

    public boolean simulationExecute(Map<String, Object> ftaInfo, List<Map<String, Object>> itemList) {
        boolean result = false;

        // 임시 값임
        Long tempCompanyId = -100L;

        JudgmentDto judgmentDto = this.generateJudgmentDto(ftaInfo, itemList);

        // PSR 조회
        PsrSearchParamsDto psrParams = new PsrSearchParamsDto();
        List<String> hscodes = new ArrayList<String>();

        hscodes.add(String.valueOf(ftaInfo.get("hscode")));

        psrParams.setHscodes(hscodes);
        PsrSearchResultDto psrResult = psrService.searchPsrSearchResult(psrParams);

        return result;
    }

    private JudgmentDto generateJudgmentDto(Map<String, Object> ftaInfo, List<Map<String, Object>> itemList) {
        BigDecimal amount = new BigDecimal(String.valueOf(ftaInfo.get("amount"))); // 제품 amount
        String psrStandard = String.valueOf(ftaInfo.get("psrStandard"));
        BigDecimal rvcStandardRate = new BigDecimal(String.valueOf(ftaInfo.get("rvcStandardRate")));

        String hscode = String.valueOf(ftaInfo.get("hscode")); // 제품 HS Code
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
            String itemHscode = String.valueOf(itemData.get("hscode"));
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
            if (itemHscode.length() < 6) {
                hscodeErrorCount++;
            }

            // 2자리 일치 카운트 ***
            if (itemHscode.startsWith(hscode.substring(0, 2))) {
                ccMatchCount++;
            }

            // 4자리 일치 카운트 ***
            if (itemHscode.startsWith(hscode.substring(0, 4))) {
                cthMatchCount++;
            }

            // 6자리 일치 카운트 ***
            if (itemHscode.startsWith(hscode.substring(0, 6))) {
                ctshMatchCount++;
            }

            // 2자리 일치 비원산지 재료비
            if ("N".equals(itemOrigin) && itemHscode.startsWith(hscode.substring(0, 2))) {
                ccMatchAmount = ccMatchAmount.add(itemAmount);
            }

            // 4자리 일치 비원산지 재료비
            if ("N".equals(itemOrigin) && itemHscode.startsWith(hscode.substring(0, 4))) {
                cthMatchAmount = cthMatchAmount.add(itemAmount);
            }

            // 6자리 일치 비원산지 재료비
            if ("N".equals(itemOrigin) && itemHscode.startsWith(hscode.substring(0, 6))) {
                ctshMatchAmount = ctshMatchAmount.add(itemAmount);
            }

        }

        // PSR 조회 추가 필요

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

        return judgmentDto;
    }
}
