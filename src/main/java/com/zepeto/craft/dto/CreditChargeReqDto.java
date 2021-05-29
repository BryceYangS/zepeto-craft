package com.zepeto.craft.dto;

import javax.validation.constraints.PositiveOrZero;

import com.zepeto.craft.domain.CreditType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditChargeReqDto {

	private CreditType creditType;

	@PositiveOrZero(message = "충전량은 음수일 수 없습니다.")
	private int chargeCount;

}
