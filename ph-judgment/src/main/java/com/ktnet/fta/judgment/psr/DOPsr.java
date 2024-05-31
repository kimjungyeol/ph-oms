package com.ktnet.fta.judgment.psr;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ktnet.fta.judgment.dto.JudgmentDto;
import com.ktnet.fta.judgment.mapper.JudgmentMapper;

@Component
public class DOPsr {

    private final static String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";

    @Autowired
    private JudgmentMapper judgmentMapper;

    public boolean judgment(Long companyId, JudgmentDto judgment) {

        // 확인서 판정 사용 설정
        judgment.setDoUse(Boolean.TRUE);

        // 대표 수취확인서 확인
        Map<String, Object> eoReceipt = judgmentMapper.selectJudgmentPurchase(judgment.getEoId());

        if (eoReceipt == null) {
            judgment.addError("judgment.do.empty",
                    "[[ 상품 구매 원산지확인서 미수취 ]]" + "<br>상품의 경우, 매입 정보 및 수취된 확인서가 필요합니다." + "<br>" + "<br> ** 관련 메뉴 "
                            + "<br>- 원산지확인서 수취 > 원산지확인 요청" + "<br>- 원산지확인서 수취 > 원산지확인 수취" + "<br>- 원산지분석 > 분류대상 분석");
            judgment.setDoSufficient(false);
            return false;
        }

        if (StringUtils.isBlank(String.valueOf(eoReceipt.get("eoReceipt")))) {
            judgment.addError("judgment.do.psr", "[[ 수취 확인서 결정기준누락 ]]" + "<br>수취 확인서의 결정기준이 없습니다." + "<br>"
                    + "<br> ** 관련 메뉴 " + "<br>- 원산지확인서 수취 > 원산지확인 요청" + "<br>- 원산지확인서 수취 > 원산지확인 수취");
            judgment.setDoSufficient(false);
            return false;
        }

        String standardPsrName = judgment.getNameOfDo().replaceAll(match, "");
        String receiptPsrName = String.valueOf(eoReceipt.get("originPlacePsr")).replaceAll(match, "");

        if (!standardPsrName.equalsIgnoreCase(receiptPsrName)) {
            judgment.addError("judgment.do.psr", "[[ 결정기준 불일치 ]]" + "<br>수취 확인서의 결정기준이 일치하지 않습니다." + "<br>"
                    + "<br> ** 관련 메뉴 " + "<br>- 원산지확인서 수취 > 원산지확인 수취");
            judgment.setDoSufficient(false);
            return false;
        }
        judgment.setDoSufficient(true);
        return true;
    }

}
