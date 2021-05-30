package com.zepeto.craft.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Player {

	@Id
	@GeneratedValue
	@Column(name = "PLAYER_ID")
	private Long id;

	@Enumerated(EnumType.STRING)
	private Grade grade;

	@OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
	private List<PlayerCredit> playerCredits = new ArrayList<>();

	@OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
	private List<PlayerInvetory> playerInvetories = new ArrayList<>();

	// 연관관계 메서드
	private void insertCredit(PlayerCredit playerCredit) {
		playerCredits.add(playerCredit);
		playerCredit.player(this);
	}

	private void insertInventory(PlayerInvetory changeInvetory) {
		playerInvetories.add(changeInvetory);
		changeInvetory.player(this);
	}

	//==비즈니스 로직==//

	/**
	 * 재화 충전
	 * @param chargeCredits
	 */
	public void chargeCredit(List<PlayerCredit> chargeCredits) {

		for (PlayerCredit chargeCredit : chargeCredits) {
			Optional<PlayerCredit> optionalPlayerCredit = playerCredits.stream()
				.filter(playerCredit ->
					playerCredit.getId().getCreditType() == chargeCredit.getId().getCreditType())
				.findFirst();

			if (optionalPlayerCredit.isPresent()) {
				optionalPlayerCredit.get().charge(chargeCredit.getCount());
				continue;
			}

			insertCredit(chargeCredit);
		}
	}

	/**
	 * 아이템 구매
	 * @param changedInvetory
	 */
	public void buyItem(PlayerInvetory changedInvetory) {

		Optional<PlayerInvetory> playerInventoryByItemId = playerInvetories.stream()
			.filter(playerInvetory ->
				playerInvetory.getId().getItemId() == changedInvetory.getId().getItemId())
			.findFirst();

		if (playerInventoryByItemId.isPresent()) {
			playerInventoryByItemId.get().addCount(changedInvetory.getCount());
			return;
		}

		insertInventory(changedInvetory);
	}

	//==조회 로직==/
	public int fetchTotalCredits() {
		int total = 0;
		for (PlayerCredit credit : playerCredits) {
			total += credit.getCount();
		}
		return total;
	}
}
