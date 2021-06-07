package com.zepeto.craft.domain.policy;

import java.util.List;

import com.zepeto.craft.domain.CreditType;
import com.zepeto.craft.domain.PlayerCredit;

public interface BuyPolicy {

	int ZERO = 0;

	void apply(int price, List<PlayerCredit> playerCredits);

	int calculate(int price, CreditType creditType, List<PlayerCredit> playerCredits);
}