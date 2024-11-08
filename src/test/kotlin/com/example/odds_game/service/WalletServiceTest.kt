package com.example.odds_game.service

import com.example.odds_game.constant.TransactionType
import com.example.odds_game.model.Transaction
import com.example.odds_game.repository.TransactionRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus

class WalletServiceTest {

    private val transactionRepository: TransactionRepository = mock(TransactionRepository::class.java)
    private val walletService = WalletService(transactionRepository)

    @Test
    fun `getTransactions should return transactions for a player`() {
        val transactions = listOf(
            Transaction(id = 1, playerId = 1, amount = -100.0, transactionType = TransactionType.BET_PLACED),
            Transaction(id = 2, playerId = 1, amount = 200.0, transactionType = TransactionType.WINNING)
        )
        `when`(transactionRepository.findByPlayerId(1)).thenReturn(transactions)

        val response = walletService.getTransactions(1)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(transactions, response.body)
    }

    @Test
    fun `addTransaction should return saved transaction`() {
        val transaction = Transaction(playerId = 1, amount = 100.0, transactionType = TransactionType.WINNING)
        `when`(transactionRepository.save(transaction)).thenReturn(transaction)

        val response = walletService.addTransaction(transaction)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(transaction, response.body)
    }
}
