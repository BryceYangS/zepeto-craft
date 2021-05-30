package com.zepeto.craft.controller;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zepeto.craft.service.ItemService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;

	@ApiOperation(value = "아이템 구매", httpMethod = "POST", notes = "플레이어의 재화를 충전합니다.")
	@PostMapping("/items/{itemId}/buy")
	public ResponseEntity buyItem(@PathVariable Long itemId, @RequestParam(name = "playerId") @NonNull Long playerId,
		@RequestParam(name = "count") @NonNull int count) {
		int totalCredit = itemService.buyItem(playerId, itemId, count);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(URI.create("/credits/" + playerId + "/total"));
		return ResponseEntity
			.status(HttpStatus.FOUND)
			.headers(responseHeaders)
			.build();
	}
}
