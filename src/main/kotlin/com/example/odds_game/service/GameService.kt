package com.example.odds_game.service

import com.example.odds_game.model.*
import com.example.odds_game.repository.BetRepository
import com.example.odds_game.repository.PlayerRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import kotlin.math.abs
import kotlin.random.Random

@Service
class GameService(
    private val playerRepository: PlayerRepository,
    private val betRepository: BetRepository
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

    fun placeBet(bet: Bet): ResponseEntity<BetResponse> {
        val player = playerRepository.findById(bet.playerId).orElse(null) ?: return ResponseEntity(BetResponse.Error("Player not found"), HttpStatus.NOT_FOUND)
        if (player.balance < bet.amount) {
            return ResponseEntity(BetResponse.Error("Insufficient balance to place the bet"), HttpStatus.BAD_REQUEST)
        }

        player.balance -= bet.amount

        val resultNumber = Random.nextInt(1, 11)

        val winnings = calculateWinnings(bet.numberBet, resultNumber, bet.amount)

        if (winnings > 0) {
            player.totalWinnings += winnings
        }

        bet.resultNumber = resultNumber
        bet.winnings = winnings


        player.balance += winnings

        try {
            playerRepository.save(player)
            return ResponseEntity.ok(BetResponse.Success(betRepository.save(bet), "Bets placed with $resultNumber result"))
        } catch (e: Exception) {
            return ResponseEntity(BetResponse.Error("Entity not found."), HttpStatus.NOT_FOUND)
        }

    }

    private fun calculateWinnings(bet: Int, result: Int, amount: Double): Double {
        return when {
            bet == result -> amount * 10
            abs(bet - result) == 1 -> amount * 5
            abs(bet - result) == 2 -> amount / 2
            else -> 0.0
        }
    }

    fun getBetsByPlayerId(playerId: Long): ResponseEntity<List<Bet>> {
        return ResponseEntity.ok(betRepository.findByPlayerId(playerId))
    }
}