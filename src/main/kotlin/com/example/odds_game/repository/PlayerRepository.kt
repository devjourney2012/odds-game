package com.example.odds_game.repository

import com.example.odds_game.model.Player
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query


interface PlayerRepository : JpaRepository<Player, Long> {
    fun findByUsername(username: String): Player?

    @Query("SELECT p FROM Player p ORDER BY p.totalWinnings DESC")
    fun findTopPlayers(limit: Int): List<Player>
}
