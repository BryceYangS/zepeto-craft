package com.zepeto.craft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zepeto.craft.domain.PlayerCredit;

@Repository
public interface PlayerCreditRepository extends JpaRepository<PlayerCredit, Long> {

	@Query("SELECT COALESCE(SUM(pc.count), 0) FROM PlayerCredit pc left join pc.player p WHERE p.id = :playerId")
	int sumCountByPlayerId(Long playerId);

	List<PlayerCredit> findByPlayerId(Long playerId);
}
