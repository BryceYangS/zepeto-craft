package com.zepeto.craft.domain;

public enum CreditType {
	PAID, FREE;

	public boolean matches(String type) {
		return name().equals(type);
	}
}
