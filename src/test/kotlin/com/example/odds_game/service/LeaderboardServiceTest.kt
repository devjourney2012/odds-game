package com.example.odds_game.service

import com.example.odds_game.model.Player
import com.example.odds_game.repository.PlayerRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus

class LeaderboardServiceTest {

    private val playerRepository: PlayerRepository = mock(PlayerRepository::class.java)
    private val leaderboardService = LeaderboardService(playerRepository)

    @Test
    fun `getTopPlayers should return top players`() {
        val players = listOf(
            Player(id = 1, name = "Alice", surname = "Smith", username = "alice", totalWinnings = 500.0),
            Player(id = 2, name = "Bob", surname = "Johnson", username = "bob", totalWinnings = 300.0)
        )
        `when`(playerRepository.findTopPlayers(10)).thenReturn(players)

        val response = leaderboardService.getTopPlayers(10)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(players, response.body)
    }
}
