package com.example.odds_game.service

import com.example.odds_game.model.*
import com.example.odds_game.repository.BetRepository
import com.example.odds_game.repository.PlayerRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus

class GameServiceTest {

    private val playerRepository: PlayerRepository = mock(PlayerRepository::class.java)
    private val betRepository: BetRepository = mock(BetRepository::class.java)
    private val walletService: WalletService = mock(WalletService::class.java)
    private val gameService = GameService(playerRepository, betRepository, walletService)

    @Test
    fun `registerPlayer should return success when player is registered`() {
        val player = Player(name = "John", surname = "Doe", username = "johndoe")
        `when`(playerRepository.findByUsername(player.username)).thenReturn(null)
        `when`(playerRepository.save(player)).thenReturn(player)

        val response = gameService.registerPlayer(player)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals("Player created successfully", (response.body as RegistrationResponse.Success).message)
    }

    @Test
    fun `placeBet should return error when player is not found`() {
        val bet = Bet(playerId = 1, amount = 100.0, numberBet = 5)

        `when`(playerRepository.findById(bet.playerId)).thenReturn(java.util.Optional.empty())

        val response = gameService.placeBet(bet)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertEquals("Player not found", (response.body as BetResponse.Error).message)
    }

    @Test
    fun `placeBet should return error when insufficient balance`() {
        val player = Player(id = 1, name = "John", surname = "Doe", username = "johndoe", balance = 50.0)
        val bet = Bet(playerId = player.id!!, amount = 100.0, numberBet = 5)

        `when`(playerRepository.findById(bet.playerId)).thenReturn(java.util.Optional.of(player))

        val response = gameService.placeBet(bet)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals("Insufficient balance to place the bet", (response.body as BetResponse.Error).message)
    }

}
