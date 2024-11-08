package com.example.odds_game.service

import com.example.odds_game.model.Player
import com.example.odds_game.repository.PlayerRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class LeaderboardService(private val playerRepository: PlayerRepository) {

    fun getTopPlayers(limit: Int): ResponseEntity<List<Player>> {
        return ResponseEntity.ok(playerRepository.findTopPlayers(limit))
    }
}
