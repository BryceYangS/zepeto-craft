package com.zepeto.craft.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zepeto.craft.domain.Items;
import com.zepeto.craft.domain.Player;
import com.zepeto.craft.domain.PlayerInvetory;
import com.zepeto.craft.domain.policy.BuyPolicy;
import com.zepeto.craft.domain.policy.PolicyFactory;
import com.zepeto.craft.exception.NotEnoughCreditException;
import com.zepeto.craft.repository.PlayerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

	private final PlayerRepository playerRepository;
	private final PolicyFactory policyFactory;

	public int buyItem(Long playerId, Long itemId, int count) {
		Player player = playerRepository.findById(playerId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID입니다."));

		Items item = Items.resolve(itemId);
		int price = item.getPrice() * count;

		if (player.fetchTotalCredits() < price) {
			throw new NotEnoughCreditException("재화가 부족합니다");
		}

		BuyPolicy buyPolicy = policyFactory.getBuyPolicy(player);
		buyPolicy.apply(price, player.getPlayerCredits());

		PlayerInvetory playerInvetory = PlayerInvetory.createPlayerInventory(itemId, count);
		player.buyItem(playerInvetory);

		return player.fetchTotalCredits();
	}
}
