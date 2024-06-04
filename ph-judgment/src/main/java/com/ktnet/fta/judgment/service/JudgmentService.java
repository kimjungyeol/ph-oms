package com.ktnet.fta.judgment.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktnet.fta.judgment.constant.DetailsType;
import com.ktnet.fta.judgment.constant.JudgmentType;
import com.ktnet.fta.judgment.dto.JudgmentConditionDetailDto;
import com.ktnet.fta.judgment.dto.JudgmentDto;
import com.ktnet.fta.judgment.dto.JudgmentErrorDetailDto;
import com.ktnet.fta.judgment.dto.JudgmentSetupDto;
import com.ktnet.fta.judgment.mapper.JudgmentMapper;
import com.ktnet.fta.judgment.psr.CTCPsr;
import com.ktnet.fta.judgment.psr.ConditionPsr;
import com.ktnet.fta.judgment.psr.DOPsr;
import com.ktnet.fta.judgment.psr.RVCPsr;
import com.ktnet.fta.judgment.psr.SPPsr;
import com.ktnet.fta.judgment.psr.WOPsr;
import com.ktnet.fta.psr.dto.PsrSearchItemDto;
import com.ktnet.fta.psr.dto.PsrSearchParamsDto;
import com.ktnet.fta.psr.dto.PsrSearchResultDto;
import com.ktnet.fta.psr.dto.PsrStdItemTypeDto;
import com.ktnet.fta.psr.service.PsrService;

@Service("judgmentService")
public class JudgmentService {

    /*** Service ***/
    @Autowired
    private PsrService psrService;

    /*** Mapper ***/
    @Autowired
    private JudgmentMapper judgmentMapper;

    /*** PSR ***/
    @Autowired
    private WOPsr woPsr;
    @Autowired
    private SPPsr spPsr;
    @Autowired
    private CTCPsr ctcPsr;
    @Autowired
    private RVCPsr rvcPsr;
    @Autowired
    private ConditionPsr conditionPsr;
    @Autowired
    private DOPsr doPsr;

    public String test() {
        return "judgment api post test";
    }

    public boolean simulationExecute(Map<String, Object> ftaInfo, List<Map<String, Object>> itemList) {
        boolean result = false;

        // 임시 값임
        Long tempCompanyId = -100L;

        JudgmentDto judgmentDto = this.generateJudgmentDto(ftaInfo, itemList);

        this.judgment(tempCompanyId, judgmentDto);

//        if ("RVC".equals(judgmentDto.getPsrStandard())) {
//            result = rvcPsr.judgment(judgmentDto);
//        }
//
//        if ("CTH".equals(ftaInfo.get("psrStandard"))) {
//            // result = ctcPsr.judgment(judgmentDto);
//        }

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

    private void judgment(final Long companyId, final JudgmentDto judgment) {
        boolean result = true;

        if (judgment.getJudgmentType().equals(JudgmentType.PURCHASE)) {
            // 상품 판정
            result = this.doPsr.judgment(companyId, judgment);
            judgment.updateSufficient(result);
            return;
        }

        // 완전 생산 기준 판정
        if (judgment.getWoUse()) {
            result = result && this.woPsr.judgment(companyId, judgment);
        }

        // 가공공정 기준 판정
        if (judgment.getSpUse()) {
            result = result && this.spPsr.judgment(companyId, judgment);
        }

        // 세번변경 기준 판정
        if (judgment.getCtcUse()) {
            result = result && this.ctcPsr.judgment(companyId, judgment);
        }

        // 부가가치 기준 판정
        if (judgment.getRvcUse()) {
            result = result && this.rvcPsr.judgment(companyId, judgment);
        }

        // 예외기준 판정
        if (judgment.getConditionUse()) {
            result = result && this.conditionPsr.judgment(companyId, judgment);
        }

        // 최종 결과 반영
        judgment.updateSufficient(result);

    }

    public void judgmentExecute(Map<String, Object> params) {
        // 임시 값임
        Long tempCompanyId = -100L;

        // 데이터 초기화
        this.clear(params);

        // 임시 값임
        params.put("companyId", tempCompanyId);

        // 설정정보 가져오기
        Map<Long, JudgmentSetupDto> setupMap = this.findSetupMap(params);

        // 판정 데이터 조회
        List<JudgmentDto> judgmentDtos = this.judgmentMapper.selectJudgment(params);
        List<JudgmentDto> judgmentEntities = new ArrayList<>();

        // PSR 조회
        PsrSearchParamsDto psrParams = new PsrSearchParamsDto();
        List<String> hscodes = judgmentDtos.stream().filter(item -> StringUtils.isNotBlank(item.getHscode()))
                .map(item -> item.getHscode()).collect(Collectors.toList());

        psrParams.getHscodes().addAll(hscodes);
        PsrSearchResultDto psrResult = psrService.searchPsrSearchResult(psrParams);

        for (JudgmentDto judgmentDto : judgmentDtos) {
            if (judgmentDto.getJudgmentType() == null) {
                // 판정 유형 설정 없는 경우, 판정 제품 유형에 따라 결정
                judgmentDto.setJudgmentType(
                        judgmentDto.getDetailsType() == DetailsType.PRODUCT ? JudgmentType.PSR : JudgmentType.PURCHASE);
            }

            if (judgmentDto.getUseJudgment() != true) {
                // 판정 제외 설정 된 경우
                judgmentDto.setNameOfDo("판정 제외 설정");
                judgmentDto.setPsrDescription("해당 품목은 판정 제외 처리되어 있습니다. 비원산지 물품으로 판정됩니다.");
                judgmentDto.addError("judgment.setting.ignore", "해당 품목은 판정 제외 처리되어 있습니다. 비원산지 물품으로 판정됩니다.");
                // judgmentEntities.add(Judgment.builder().build().update(judgmentDto));
                judgmentEntities.add(judgmentDto); // 확인 필요

                continue;
            }

            if (StringUtils.isBlank(judgmentDto.getHscode())) {
                judgmentDto.setNameOfDo("품목분류  오류");
                judgmentDto.setPsrDescription("판정 품목의 HS 코드를 찾을 수 없습니다. 품목분류를 먼저 진행해 주세요.");
                judgmentDto.addError("judgment.hscode.empty", "판정 품목의 HS 코드를 찾을 수 없습니다. 품목분류를 먼저 진행해 주세요.");
                // judgmentEntities.add(Judgment.builder().build().update(judgmentDto));
                judgmentEntities.add(judgmentDto); // 확인 필요
                continue;
            }

            // 대상 소명서 결정기준 리스트 가져오기
            List<PsrSearchItemDto> psrs = psrResult.getPsrs(judgmentDto.getHscode(), judgmentDto.getFtaId());
            if (psrs.isEmpty()) {
                // 결정기준이 없는 경우
                judgmentDto.setNameOfDo("결정기준 오류");
                judgmentDto.setPsrDescription("원산지 결정기준을 찾을 수 없습니다. HS 코드가 정확한지 확인해 주세요.");
                judgmentDto.addError("judgment.psr.empty", "원산지 결정기준을 찾을 수 없습니다. HS 코드가 정확한지 확인해 주세요.");
                // judgmentEntities.add(Judgment.builder().build().update(judgmentDto));
                judgmentEntities.add(judgmentDto);
                continue;
            }

            // 대상 소명서 품목 유형 체크
            // 품목분류 정보가 등록됬을 경우만
            if (judgmentDto.getClassificationId() != null) {
                List<Long> standardItemTypeIds = psrResult.getStandardItemTypeIds(judgmentDto.getHscode(),
                        judgmentDto.getFtaId());
                if (standardItemTypeIds.size() > 1) {
                    // 품목유형 선택여부 확인
                    Long standardItemId = psrs.stream().map(item -> item.getStandardItemId()).findFirst().orElse(null);
                    // 해당 결정기준에 대한 유형이 단건이 아닌경우, 품목분류에 지정된 유형 확인
                    Long itemTypeId = null;

                    // PsrStdItemTypeDto itemType = explainOriginRepository
                    // .findItemTypeIdFrDto(judgmentDto.getClassificationId(), standardItemId);

                    PsrStdItemTypeDto itemType = psrService.searchPsrStdItemType(judgmentDto.getClassificationId(),
                            standardItemId);

                    if (itemType != null) {
                        itemTypeId = itemType.getStandardItemTypeId();
                    }

                    if (itemTypeId == null) {
                        // 품목 유형이 지정 안된 경우, 오류
                        judgmentDto.setNameOfDo("세분류 오류");
                        judgmentDto.setPsrDescription("판정 품목의 세분류 선택이 필요합니다. 품목 분류에서 세부 분류를 선택해 주세요.");
                        judgmentDto.addError("judgment.types.multy", "품목분류에서 해당 품목의 세부 분류를 선택해 주세요.");
                        judgmentEntities.add(judgmentDto);
                        continue;
                    } else {
                        // 품목 유형이 지정 된 경우, 해당 유형 결정기준만 판정
                        final Long selectItemTypeId = itemTypeId;
                        psrs = psrs.stream().filter(item -> item.getStandardItemTypeId().equals(selectItemTypeId))
                                .collect(Collectors.toList());
                    }
                }
            }

            // 결정기준 별로, 판정 객체 생성
            for (PsrSearchItemDto psrDto : psrs) {
                // 판정 객체 설정
                judgmentDto.update(psrDto);
                judgmentDto.update(setupMap.get(psrDto.getFtaId()));

                // 판정 실행
                this.judgment(tempCompanyId, judgmentDto);

                // 결정기준 별로 엔티티 저장
                judgmentEntities.add(judgmentDto);
            }
        }

        // 판정 정보 저장
        this.save(tempCompanyId, judgmentEntities);
    }

    // 초기화
    private void clear(Map<String, Object> params) {
        judgmentMapper.deleteJudgmentCondition(params);
        judgmentMapper.deleteJudgment(params);
    }

    // 설정정보 가져오기
    public Map<Long, JudgmentSetupDto> findSetupMap(Map<String, Object> params) {
        // FTA 별 설정정보 가져오기
        List<JudgmentSetupDto> setups = judgmentMapper.selectJudgmentSetup(params);

        Map<Long, JudgmentSetupDto> setupMap = new HashMap<>();

        for (JudgmentSetupDto setup : setups) {
            setupMap.put(setup.getFtaId(), setup);
        }

        return setupMap;
    }

    private void save(Long tempCompanyId, List<JudgmentDto> judgmentEntities) {
        this.saveJudgment(judgmentEntities);
        this.saveJudgmentCondition(judgmentEntities);
        this.saveJudgmentError(judgmentEntities);
    }

    private void saveJudgment(List<JudgmentDto> judgmentEntities) {
        for (JudgmentDto judgment : judgmentEntities) {
            // *mybatis forEach로 변경 필요
            judgmentMapper.insertJudgment(judgment);
        }
    }

    private void saveJudgmentCondition(List<JudgmentDto> judgmentEntities) {
        for (JudgmentDto judgment : judgmentEntities) {
            for (JudgmentConditionDetailDto condition : judgment.getConditions()) {
                // *mybatis forEach로 변경 필요
                judgmentMapper.insertJudgmentCondition(condition);
            }
        }
    }

    private void saveJudgmentError(List<JudgmentDto> judgmentEntities) {
        for (JudgmentDto judgment : judgmentEntities) {
            for (JudgmentErrorDetailDto error : judgment.getErrors()) {
                // *mybatis forEach로 변경 필요
                error.setGroupId(judgment.getGroupId());
                error.setJudgmentId(judgment.getId());

                judgmentMapper.insertJudgmentError(error);
            }
        }
    }

    public List<Map<String, Object>> searchJudgmentTest(Map<String, Object> pMap) {

        return judgmentMapper.selectJudgmentTest(pMap);
    }
}
