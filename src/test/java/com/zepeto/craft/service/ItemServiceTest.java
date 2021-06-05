package com.zepeto.craft.service;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.zepeto.craft.domain.CreditType;
import com.zepeto.craft.domain.Grade;
import com.zepeto.craft.domain.Items;
import com.zepeto.craft.domain.Player;
import com.zepeto.craft.domain.PlayerCredit;
import com.zepeto.craft.dto.CreditChargeReqDto;
import com.zepeto.craft.exception.NotEnoughCreditException;

@SpringBootTest
@Transactional
class ItemServiceTest {

	@Autowired
	ItemService itemService;
	@Autowired
	EntityManager em;
	@Autowired
	PlayerCreditService playerCreditService;

	@Test
	public void 아이템구매() throws Exception {
		// given
		Player player = Player.builder()
			.grade(Grade.GENERAL)
			.playerCredits(new ArrayList<>())
			.playerInvetories(new ArrayList<>())
			.build();
		em.persist(player);
		List<CreditChargeReqDto> creditChargeReqList = new ArrayList<>();

		CreditChargeReqDto dto1 = new CreditChargeReqDto();
		dto1.setCreditType(CreditType.FREE);
		dto1.setChargeCount(1);
		creditChargeReqList.add(dto1);

		CreditChargeReqDto dto2 = new CreditChargeReqDto();
		dto2.setCreditType(CreditType.PAID);
		dto2.setChargeCount(1);
		creditChargeReqList.add(dto2);
		playerCreditService.deposit(player.getId(), creditChargeReqList);

		// when
		int i = itemService.buyItem(player.getId(), Items.MILK.getId(), 1);
		em.flush();
		// then
		assertThat(i).isEqualTo(0);
	}

	@Test
	public void 아이템구매_실패_재화부족() throws Exception {
		// given
		Player player = Player.builder()
			.grade(Grade.GENERAL)
			.playerCredits(new ArrayList<>())
			.playerInvetories(new ArrayList<>())
			.build();
		em.persist(player);
		List<CreditChargeReqDto> creditChargeReqList = new ArrayList<>();

		CreditChargeReqDto dto1 = new CreditChargeReqDto();
		dto1.setCreditType(CreditType.FREE);
		dto1.setChargeCount(1);
		creditChargeReqList.add(dto1);

		CreditChargeReqDto dto2 = new CreditChargeReqDto();
		dto2.setCreditType(CreditType.PAID);
		dto2.setChargeCount(1);
		creditChargeReqList.add(dto2);
		playerCreditService.deposit(player.getId(), creditChargeReqList);

		// when , then
		Assertions.assertThrows(NotEnoughCreditException.class,
			() -> itemService.buyItem(player.getId(), Items.MILK.getId(), 2));
	}

	@Test
	public void 아이템구매2() throws Exception {
		// given
		Player player = Player.builder()
			.grade(Grade.GENERAL)
			.playerCredits(new ArrayList<>())
			.playerInvetories(new ArrayList<>())
			.build();
		em.persist(player);
		List<CreditChargeReqDto> creditChargeReqList = new ArrayList<>();

		CreditChargeReqDto dto1 = new CreditChargeReqDto();
		dto1.setCreditType(CreditType.FREE);
		dto1.setChargeCount(5);
		creditChargeReqList.add(dto1);

		CreditChargeReqDto dto2 = new CreditChargeReqDto();
		dto2.setCreditType(CreditType.PAID);
		dto2.setChargeCount(1);
		creditChargeReqList.add(dto2);
		playerCreditService.deposit(player.getId(), creditChargeReqList);

		// when
		int restCredit = itemService.buyItem(player.getId(), Items.COKE.getId(), 2);

		// then
		assertThat(restCredit).isEqualTo(2);

		List<PlayerCredit> playerCredits = player.getPlayerCredits();
		for (PlayerCredit pc : playerCredits) {
			if (pc.getCreditType() == CreditType.FREE) {
				assertThat(pc.getCount()).isEqualTo(1);
			}

			if (pc.getCreditType() == CreditType.PAID) {
				assertThat(pc.getCount()).isEqualTo(1);
			}
		}

	}
}