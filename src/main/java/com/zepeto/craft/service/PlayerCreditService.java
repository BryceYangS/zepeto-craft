package com.zepeto.craft.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zepeto.craft.domain.Player;
import com.zepeto.craft.domain.PlayerCredit;
import com.zepeto.craft.dto.CreditChargeReqDto;
import com.zepeto.craft.repository.PlayerCreditRepository;
import com.zepeto.craft.repository.PlayerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PlayerCreditService {

	private final PlayerCreditRepository playerCreditRepository;
	private final PlayerRepository playerRepository;

	/**
	 * 재화 충전
	 *
	 * @param playerId
	 * @param creditChargeReqList
	 * @return
	 *
	 * <comment>
	 * 	재화 데이터가 DB에 없을 경우 초기화 데이터를 저장
	 * 	재화 데이터가 있을 경우 재화 충전 결과 저장
	 * </comment>
	 */
	public Long deposit(Long playerId, List<CreditChargeReqDto> creditChargeReqList) {

		Player player = playerRepository.findById(playerId).get();

		List<PlayerCredit> playerCredits = new ArrayList<>();
		for (CreditChargeReqDto dto : creditChargeReqList) {
			PlayerCredit pc = PlayerCredit.createPlayerCredit(player.getId(), dto);
			playerCredits.add(pc);
		}

		player.chargeCredit(playerCredits);

		return playerId;
	}
}
