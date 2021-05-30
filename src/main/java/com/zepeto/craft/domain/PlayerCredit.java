package com.zepeto.craft.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
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

	@EmbeddedId
	private PlayerCreditId id;

	@MapsId("playerId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLAYER_ID")
	private Player player;

	private int count;

	//== 생성 메서드 ==//
	public static PlayerCredit createPlayerCredit(Long playerId, CreditChargeReqDto creditChargeReqDto) {
		PlayerCreditId playerCreditId = PlayerCreditId.builder()
			.playerId(playerId)
			.creditType(creditChargeReqDto.getCreditType()).build();

		return PlayerCredit.builder()
			.id(playerCreditId)
			.count(creditChargeReqDto.getChargeCount())
			.build();
	}

	public PlayerCredit player(Player player) {
		this.player = player;
		return this;
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
