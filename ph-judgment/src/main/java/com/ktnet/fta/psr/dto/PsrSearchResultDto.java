package com.ktnet.fta.psr.dto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import lombok.ToString;

@ToString
public class PsrSearchResultDto {

    private List<PsrSearchItemDto> psrs = new ArrayList<>();

    public List<PsrSearchItemDto> getPsrs() {
        return psrs;
    }

    public void setPsrs(List<PsrSearchItemDto> psrs) {
        this.psrs = psrs;
    }

    public PsrSearchResultDto() {

    }

    public PsrSearchResultDto(List<PsrSearchItemDto> psrs) {
        this.psrs = psrs != null ? psrs : this.psrs;
    }

    public List<PsrSearchItemDto> getPsrs(String hscode, Long ftaId) {
        if (StringUtils.isBlank(hscode)) {
            return new ArrayList<>();
        }

        // log.debug(">>>>> psrs : " + psrs.size());

        // 가능 hs 목록 구하기
        List<String> possibleHscodes = psrs.stream()
                .filter(item -> hscode.startsWith(item.getHscode()) && item.getFtaId().equals(ftaId))
                .map(item -> item.getHscode()).distinct().sorted(Comparator.comparing(String::length).reversed())
                .collect(Collectors.toList());
        // log.debug(">>>>> possibleHscodes : " + possibleHscodes.size());
        // 가능 hs 없는 경우 빈 결과 리턴
        if (possibleHscodes.isEmpty()) {
            return new ArrayList<>();
        }
        // 길이가 가장 큰 hs 코드 기준 처리
        String psrHscode = possibleHscodes.get(0);
        // log.debug(">>>>> psrHscode : " + psrHscode);

        // 결과 리턴
        return psrs.stream().filter(item -> psrHscode.equals(item.getHscode()) && item.getFtaId().equals(ftaId))
                .collect(Collectors.toList());
    }

    public PsrSearchItemDto getDefaultPsr(String hscode, Long ftaId) {
        List<PsrSearchItemDto> psrs = this.getPsrs(hscode, ftaId);
        if (psrs.isEmpty()) {
            return null;
        }
        return psrs.stream().sorted(Comparator.comparing(PsrSearchItemDto::getSortSn)).findFirst().orElse(null);
    }

    public List<Long> getStandardItemTypeIds(String hscode, Long ftaId) {
        if (StringUtils.isBlank(hscode)) {
            return new ArrayList<>();
        }

        return psrs.stream().filter(item -> hscode.startsWith(item.getHscode()) && item.getFtaId().equals(ftaId))
                .map(item -> item.getStandardItemTypeId()).distinct().collect(Collectors.toList());
    }
}
