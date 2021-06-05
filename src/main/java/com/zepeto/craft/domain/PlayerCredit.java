package com.zepeto.craft.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zepeto.craft.dto.CreditChargeReqDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "player_credit")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerCredit {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLAYER_ID")
	private Player player;

	@Enumerated(EnumType.STRING)
	@Column(name = "CREDIT_TYPE")
	private CreditType creditType;

	private int count;

	//== 생성 메서드 ==//
	public static PlayerCredit createPlayerCredit(CreditChargeReqDto creditChargeReqDto) {
		return PlayerCredit.builder()
			.creditType(creditChargeReqDto.getCreditType())
			.count(creditChargeReqDto.getChargeCount())
			.build();
	}

	public void player(Player player) {
		this.player = player;
	}

	//== 비즈니스 로직 ==//

	public void charge(int addValue) {
		this.count += addValue;
	}

	public void minusCount(int minusValue) {
		this.count -= minusValue;
	}

	//== 조회 로직 ==//
}
