package com.zepeto.craft.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerInvetory {

	@EmbeddedId
	private PlayerInvetoryId id;

	@MapsId("playerId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLAYER_ID")
	private Player player;

	private int count;

	//== 생성 메서드 ==//
	public static PlayerInvetory createPlayerInventory(Long playerId, Long itemId, int count) {
		PlayerInvetoryId playerInventoryId = PlayerInvetoryId.builder()
			.playerId(playerId)
			.itemId(itemId)
			.build();

		return PlayerInvetory.builder()
			.id(playerInventoryId)
			.count(count)
			.build();
	}

	public PlayerInvetory player(Player player) {
		this.player = player;
		return this;
	}

	public void addCount(int itemCount) {
		this.count += itemCount;
	}
}
