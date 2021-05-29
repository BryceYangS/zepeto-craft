package com.zepeto.craft.dto;

import com.zepeto.craft.domain.CreditType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerCreditDto {
	Long playerId;
	CreditType creditType;
	int count;
}
