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
    
}