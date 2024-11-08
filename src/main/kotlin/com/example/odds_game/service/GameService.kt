package com.example.odds_game.service

import com.example.odds_game.model.*
import com.example.odds_game.repository.PlayerRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class GameService(
    private val playerRepository: PlayerRepository
) {
    fun registerPlayer(player: Player): ResponseEntity<RegistrationResponse> {
        if (playerRepository.findByUsername(player.username) != null) {
            return ResponseEntity(RegistrationResponse.Error("Username ${player.username} already exists"), HttpStatus.CONFLICT)
        }
        return try {
            ResponseEntity(
                RegistrationResponse.Success(
                    playerRepository.save(player),
                    "Player created successfully"
                ), HttpStatus.CREATED
            )
        } catch (e: EntityNotFoundException) {
            ResponseEntity(RegistrationResponse.Error("Entity not found"), HttpStatus.NOT_FOUND)
        }
    }
}