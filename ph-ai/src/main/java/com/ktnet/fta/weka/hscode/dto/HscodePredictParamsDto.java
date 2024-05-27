package com.ktnet.fta.weka.hscode.dto;

import java.util.List;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
public class HscodePredictParamsDto {

    private List<HscodeClassifierDto> datas;
    
    @Builder
    public HscodePredictParamsDto(List<HscodeClassifierDto> datas) {
        this.datas = datas != null ? datas : this.datas;
    }

	public List<HscodeClassifierDto> getDatas() {
		return datas;
	}
	
	public void setDatas(List<HscodeClassifierDto> datas) {
		this.datas = datas;
	}
}
