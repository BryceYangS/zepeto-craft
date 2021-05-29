package com.zepeto.craft.service;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.zepeto.craft.domain.CreditType;
import com.zepeto.craft.domain.Grade;
import com.zepeto.craft.domain.Player;
import com.zepeto.craft.domain.PlayerCredit;
import com.zepeto.craft.dto.CreditChargeReqDto;
import com.zepeto.craft.repository.PlayerCreditRepository;

@SpringBootTest
@Transactional
class PlayerCreditServiceTest {

	@Autowired
	EntityManager em;
	@Autowired
	PlayerCreditService playerCreditService;
	@Autowired
	PlayerCreditRepository playerCreditRepository;

	@Test
	public void 재화충전() throws Exception {
		//given
		Player player = Player.builder().grade(Grade.GENERAL).playerCredits(new ArrayList<>()).build();
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

		//when
		playerCreditService.chargePlayerCredit(player.getId(), creditChargeReqList);

		//then
		List<PlayerCredit> credits = playerCreditRepository.findByPlayerId(player.getId());
		assertThat(credits.size()).isEqualTo(2);
	}

}