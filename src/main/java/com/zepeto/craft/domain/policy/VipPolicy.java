package com.zepeto.craft.domain.policy;

import java.util.List;
import java.util.Objects;

import com.zepeto.craft.domain.CreditType;
import com.zepeto.craft.domain.PlayerCredit;

public class VipPolicy implements BuyPolicy {

	@Override
	public void apply(int price, List<PlayerCredit> playerCredits) {
		int rest = calculate(price, CreditType.FREE, playerCredits);
		calculate(rest, CreditType.PAID, playerCredits);
	}

	@Override
	public int calculate(int price, CreditType creditType, List<PlayerCredit> playerCredits) {
		if (price == 0) {
			return 0;
		}

		PlayerCredit credit = playerCredits.stream()
			.filter(pc -> pc.getId().getCreditType() == creditType)
			.findFirst()
			.orElse(null);

		if (!Objects.isNull(credit)) {
			int creditCount = credit.getCount();

			if (price <= creditCount) {
				credit.minusCount(price);
				return ZERO;
			}

			credit.minusCount(creditCount);
			price -= creditCount;
		}
		return price;
	}
}
