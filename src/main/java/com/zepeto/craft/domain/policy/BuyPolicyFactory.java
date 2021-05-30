package com.zepeto.craft.domain.policy;

import java.util.Map;

import com.zepeto.craft.domain.Grade;
import com.zepeto.craft.domain.Player;

public class BuyPolicyFactory {

	public static BuyPolicy getBuyPolicy(Map<String, BuyPolicy> buyPolicyMap, Player player) {
		Grade grade = player.getGrade();
		if (grade == Grade.VIP) {
			return buyPolicyMap.get("vipPolicy");
		}

		return buyPolicyMap.get("fixedDefaultPolicy");
	}
}
