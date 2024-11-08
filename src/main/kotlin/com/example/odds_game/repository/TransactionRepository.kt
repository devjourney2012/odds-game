package com.example.odds_game.repository

import com.example.odds_game.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TransactionRepository : JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.playerId = :playerId ORDER BY t.timestamp DESC")
    fun findByPlayerId(@Param("playerId") playerId: Long): List<Transaction>
}
