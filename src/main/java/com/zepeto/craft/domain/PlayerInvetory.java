package com.zepeto.craft.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "ITEM_ID")
	private Long itemId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLAYER_ID")
	private Player player;

	private int count;

	//== 생성 메서드 ==//
	public static PlayerInvetory createPlayerInventory(Long itemId, int count) {
		return PlayerInvetory.builder()
			.itemId(itemId)
			.count(count)
			.build();
	}

	public void player(Player player) {
		this.player = player;
	}

	public void addCount(int itemCount) {
		this.count += itemCount;
	}
}
