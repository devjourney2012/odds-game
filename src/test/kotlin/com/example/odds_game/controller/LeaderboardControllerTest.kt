package com.example.odds_game.controller

import com.example.odds_game.model.Bet
import com.example.odds_game.model.BetResponse
import com.example.odds_game.model.Player
import com.example.odds_game.model.RegistrationResponse
import com.example.odds_game.service.GameService
import com.example.odds_game.service.LeaderboardService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito.any
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@ExtendWith(SpringExtension::class)
@SpringBootTest
class LeaderboardControllerTest {

    @MockBean
    private lateinit var leaderboardService: LeaderboardService

    @Test
    fun `getTopPlayers should return list of players for given limit number`() {
        val limit = 10
        val topPlayers = listOf(Player(name = "John", surname = "Doe", username = "johndoe", balance = 8000.0, totalWinnings = 5000.0), Player(name = "Justin", surname = "Ruiz", username = "justinruiz", balance = 7999.9, totalWinnings = 4999.8))

        given(leaderboardService.getTopPlayers(limit)).willReturn(ResponseEntity.ok(topPlayers))

        val mockMvc = MockMvcBuilders.standaloneSetup(LeaderboardController(leaderboardService)).build()

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/leaderboard")
        ).andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("John"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].surname").value("Doe"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("johndoe"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].balance").value(8000.0))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].totalWinnings").value(5000.0))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Justin"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].surname").value("Ruiz"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].username").value("justinruiz"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].balance").value(7999.9))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].totalWinnings").value(4999.8))
    }
}