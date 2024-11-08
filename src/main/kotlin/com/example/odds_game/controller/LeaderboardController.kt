package com.example.odds_game.controller

import com.example.odds_game.model.Player
import com.example.odds_game.service.LeaderboardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/leaderboard")
class LeaderboardController(private val leaderboardService: LeaderboardService) {

    @GetMapping
    fun getTopPlayers(@RequestParam(defaultValue = "10") limit: Int): ResponseEntity<List<Player>> {
        return leaderboardService.getTopPlayers(limit)
    }
}
