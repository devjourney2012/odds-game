package com.example.odds_game.controller

import com.example.odds_game.model.*
import com.example.odds_game.service.GameService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class GameController(private val gameService: GameService) {

    @PostMapping("/register")
    fun registerPlayer(@RequestBody player: Player): ResponseEntity<RegistrationResponse> {
        return gameService.registerPlayer(player)
    }

    @PostMapping("/bet")
    fun placeBet(@RequestBody bet: Bet): ResponseEntity<BetResponse> {
        return gameService.placeBet(bet)
    }

    @GetMapping("/bets/player/{playerId}")
    fun getBetsByPlayerId(@PathVariable playerId: Long): ResponseEntity<List<Bet>> {
        return gameService.getBetsByPlayerId(playerId)
    }

}