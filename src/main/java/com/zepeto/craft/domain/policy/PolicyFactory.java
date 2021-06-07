package com.zepeto.craft.domain.policy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zepeto.craft.domain.Grade;
import com.zepeto.craft.domain.Player;

@Configuration
public class PolicyFactory {

	public BuyPolicy getBuyPolicy(Player player) {
		Grade grade = player.getGrade();
		if (grade == Grade.VIP) {
			return vipPolicy();
		}
		return fixedDefaultPolicy();
	}

	@Bean
	public BuyPolicy vipPolicy() {
		return new VipPolicy();
	}

	@Bean
	public BuyPolicy fixedDefaultPolicy() {
		return new FixedDefaultPolicy();
	}
}
