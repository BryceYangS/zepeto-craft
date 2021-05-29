package com.zepeto.craft.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerCreditId implements Serializable {
	@Column(name = "PLAYER_ID")
	private Long playerId;

	@Enumerated(EnumType.STRING)
	@Column(name = "CREDIT_TYPE")
	private CreditType creditType;

}
