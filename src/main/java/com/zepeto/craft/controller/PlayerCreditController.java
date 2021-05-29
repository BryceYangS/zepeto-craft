package com.zepeto.craft.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zepeto.craft.domain.PlayerCredit;
import com.zepeto.craft.dto.CreditChargeReqDto;
import com.zepeto.craft.dto.PlayerCreditDto;
import com.zepeto.craft.repository.PlayerCreditRepository;
import com.zepeto.craft.service.PlayerCreditService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PlayerCreditController {

	private final PlayerCreditService playerCreditService;
	private final PlayerCreditRepository playerCreditRepository;

	@ApiOperation(value = "재화 충전", httpMethod = "POST", notes = "플레이의 재화를 충전합니다.")
	@PostMapping("/credits/{playerId}")
	public ResponseEntity chargePlayerCredit(
		@NonNull @PathVariable Long playerId, @RequestBody List<CreditChargeReqDto> req) {

		playerId = playerCreditService.chargePlayerCredit(playerId, req);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(URI.create("/credits/" + playerId));
		return ResponseEntity
			.status(HttpStatus.FOUND)
			.headers(responseHeaders)
			.build();
	}

	@GetMapping("/credits/{playerId}")
	public ResponseEntity fetchPlayerCredit(@NonNull @PathVariable Long playerId) {
		List<PlayerCredit> playerCredits = playerCreditRepository.findByPlayerId(playerId);

		List<PlayerCreditDto> playerCreditDtos = new ArrayList<>();
		for (PlayerCredit pc : playerCredits) {
			PlayerCreditDto playerCreditDto = new PlayerCreditDto();
			playerCreditDto.setPlayerId(playerId);
			playerCreditDto.setCreditType(pc.getId().getCreditType());
			playerCreditDto.setCount(pc.getCount());

			playerCreditDtos.add(playerCreditDto);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return ResponseEntity
			.status(HttpStatus.OK)
			.headers(headers)
			.body(playerCreditDtos);
	}

}
