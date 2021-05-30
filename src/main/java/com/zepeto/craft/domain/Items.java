package com.zepeto.craft.domain;

import java.util.HashMap;
import java.util.Map;

import org.springframework.lang.Nullable;

import lombok.Getter;

@Getter
public enum Items {
	MILK(1L, 2), COFFEE(2L, 3), COKE(3L, 2), WATER(4L, 1);

	private static final Map<Long, Items> mappings = new HashMap<>();

	static {
		for (Items item : values()) {
			mappings.put(item.getId(), item);
		}
	}

	private Long id;
	private int price;

	Items(long id, int price) {
		this.id = id;
		this.price = price;
	}

	@Nullable
	public static Items resolve(@Nullable Long itemId) {
		return (itemId != null ? mappings.get(itemId) : null);
	}

}
