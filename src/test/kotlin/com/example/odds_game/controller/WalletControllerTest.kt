package com.example.odds_game.controller

import com.example.odds_game.constant.TransactionType
import com.example.odds_game.model.*
import com.example.odds_game.service.GameService
import com.example.odds_game.service.LeaderboardService
import com.example.odds_game.service.WalletService
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
import java.time.Instant
import java.time.Instant.*
import java.time.LocalDateTime
import java.time.ZoneId

@ExtendWith(SpringExtension::class)
@SpringBootTest
class WalletControllerTest {

    @MockBean
    private lateinit var walletService: WalletService

    @Test
    fun `getTransactions should return list of players for given playerId`() {
        val playerId = 4L
        val transactions = listOf(
            Transaction(playerId = 4, amount = 1000.0, transactionType = TransactionType.DEPOSIT, timestamp = LocalDateTime.ofInstant(
                parse("2024-11-07T23:06:02.757201100Z"), ZoneId.systemDefault())
            ),
            Transaction(playerId = 4, amount = 1001.0, transactionType = TransactionType.WINNING, timestamp = LocalDateTime.ofInstant(
                parse("2024-11-07T23:06:03.757201100Z"), ZoneId.systemDefault())
            ))

        given(walletService.getTransactions(playerId)).willReturn(ResponseEntity.ok(transactions))

        val mockMvc = MockMvcBuilders.standaloneSetup(WalletController(walletService)).build()

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/wallet/transactions/4")
        ).andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].playerId").value(4))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount").value(1000.0))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].transactionType").value("DEPOSIT"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].playerId").value(4))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].amount").value(1001.0))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].transactionType").value("WINNING"))
    }
}