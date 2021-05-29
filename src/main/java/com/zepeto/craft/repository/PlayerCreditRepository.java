package com.zepeto.craft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zepeto.craft.domain.PlayerCredit;
import com.zepeto.craft.domain.PlayerCreditId;

@Repository
public interface PlayerCreditRepository extends JpaRepository<PlayerCredit, PlayerCreditId> {
	public List<PlayerCredit> findByPlayerId(Long playerId);
}
