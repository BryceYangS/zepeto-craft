package com.zepeto.craft.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;
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

	@ApiOperation(value = "재화 충전", httpMethod = "POST", notes = "플레이어의 재화를 충전합니다.")
	@PostMapping("/credits/{playerId}")
	public ResponseEntity depositPlayerCredit(
		@NonNull @PathVariable Long playerId, @RequestBody List<CreditChargeReqDto> req) {

		playerId = playerCreditService.deposit(playerId, req);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(URI.create("/credits/" + playerId + "/total"));
		return ResponseEntity
			.status(HttpStatus.FOUND)
			.headers(responseHeaders)
			.build();
	}

	@ApiOperation(value = "재화 조회", httpMethod = "GET", notes = "플레이어의 재화를 조회합니다.")
	@GetMapping("/credits/{playerId}")
	public ResponseEntity fetchPlayerCredit(@NonNull @PathVariable Long playerId) {
		List<PlayerCredit> playerCredits = playerCreditRepository.findByPlayerId(playerId);

		List<PlayerCreditDto> playerCreditDtos = makePlayerCreditDtos(playerId, playerCredits);

		HttpHeaders headers = makeApplicationJsonHeader();
		return ResponseEntity
			.status(HttpStatus.OK)
			.headers(headers)
			.body(playerCreditDtos);
	}

	@ApiOperation(value = "재화 총량 조회", httpMethod = "GET", notes = "플레이어의 재화를 총량을 조회합니다.")
	@GetMapping("/credits/{playerId}/total")
	public ResponseEntity fetchTotalPlayerCredit(@NonNull @PathVariable Long playerId) {
		int total = playerCreditRepository.sumCountByPlayerId(playerId);

		HttpHeaders headers = makeApplicationJsonHeader();
		return ResponseEntity
			.status(HttpStatus.OK)
			.headers(headers)
			.body(Collections.singletonMap("totalCredit", total));
	}

	@NotNull
	private List<PlayerCreditDto> makePlayerCreditDtos(Long playerId, List<PlayerCredit> playerCredits) {
		List<PlayerCreditDto> playerCreditDtos = new ArrayList<>();
		for (PlayerCredit pc : playerCredits) {
			PlayerCreditDto playerCreditDto = new PlayerCreditDto();
			playerCreditDto.setPlayerId(playerId);
			playerCreditDto.setCreditType(pc.getCreditType());
			playerCreditDto.setCount(pc.getCount());

			playerCreditDtos.add(playerCreditDto);
		}
		return playerCreditDtos;
	}

	@NotNull
	private HttpHeaders makeApplicationJsonHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

}
