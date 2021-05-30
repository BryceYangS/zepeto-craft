package com.zepeto.craft.domain.policy;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zepeto.craft.domain.Grade;
import com.zepeto.craft.domain.Player;

@Configuration
public class PolicyFactory {

	@Bean
	public BuyPolicy vipPolicy() {
		return new VipPolicy();
	}

	@Bean
	public BuyPolicy fixedDefaultPolicy() {
		return new FixedDefaultPolicy();
	}

	public BuyPolicy getBuyPolicy(Map<String, BuyPolicy> buyPolicyMap, Player player) {
		Grade grade = player.getGrade();
		if (grade == Grade.VIP) {
			return buyPolicyMap.get("vipPolicy");
		}

		return buyPolicyMap.get("fixedDefaultPolicy");
	}
}
