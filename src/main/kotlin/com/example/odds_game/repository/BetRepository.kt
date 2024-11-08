package com.example.odds_game.repository

import com.example.odds_game.model.Bet
import org.springframework.data.jpa.repository.JpaRepository

interface BetRepository : JpaRepository<Bet, Long> {
    fun findByPlayerId(playerId: Long): List<Bet>
}