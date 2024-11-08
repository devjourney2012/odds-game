package com.example.odds_game.controller

import com.example.odds_game.model.Bet
import com.example.odds_game.model.BetResponse
import com.example.odds_game.model.Player
import com.example.odds_game.model.RegistrationResponse
import com.example.odds_game.service.GameService
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
class GameControllerTest {

    @MockBean
    private lateinit var gameService: GameService

    @Test
    fun `registerPlayer should return created status when player is registered successfully`() {
        val newPlayer = Player(name = "John", surname = "Doe", username = "johndoe")

        given(gameService.registerPlayer(newPlayer)).willReturn(
            ResponseEntity(RegistrationResponse.Success(newPlayer, "Player created successfully"),
                HttpStatus.CREATED)
        )

        val mockMvc = MockMvcBuilders.standaloneSetup(GameController(gameService)).build()

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/register")
                .contentType("application/json")
                .content("{ \"name\": \"John\", \"surname\": \"Doe\", \"username\": \"johndoe\" }")
        ).andExpect(status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Player created successfully"))
    }

    @Test
    fun `placeBet should return ok status when bet is placed successfully`() {
        val bet = Bet(playerId = 1, amount = 50.0, numberBet = 3, timestamp = "2024-11-07T23:06:02.757201100Z")

        given(gameService.placeBet(bet)).willReturn(
            ResponseEntity(BetResponse.Success(bet, "Bet placed with result"), HttpStatus.OK)
        )

        val mockMvc = MockMvcBuilders.standaloneSetup(GameController(gameService)).build()

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/bet")
                .contentType("application/json")
                .content("{ \"playerId\": 1, \"amount\": 50, \"numberBet\": 3, \"timestamp\": \"2024-11-07T23:06:02.757201100Z\" }")
        ).andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Bet placed with result"))
    }

    @Test
    fun `getBetsByPlayerId should return list of bets for given player`() {
        val bets = listOf(Bet(playerId = 1, amount = 50.0, numberBet = 3))

        given(gameService.getBetsByPlayerId(1)).willReturn(ResponseEntity.ok(bets))

        val mockMvc = MockMvcBuilders.standaloneSetup(GameController(gameService)).build()

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/bets/player/1")
        ).andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount").value(50.0))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].numberBet").value(3))
    }
}