package com.zepeto.craft.service;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.zepeto.craft.domain.CreditType;
import com.zepeto.craft.domain.Grade;
import com.zepeto.craft.domain.Items;
import com.zepeto.craft.domain.Player;
import com.zepeto.craft.domain.policy.BuyPolicy;
import com.zepeto.craft.dto.CreditChargeReqDto;

@SpringBootTest
@Transactional
class ItemServiceTest {
	@Autowired
	Map<String, BuyPolicy> buyPolicyMap;
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
}