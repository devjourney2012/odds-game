package com.example.odds_game.service

import com.example.odds_game.model.Transaction
import com.example.odds_game.repository.TransactionRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class WalletService(private val transactionRepository: TransactionRepository) {

    fun getTransactions(playerId: Long): ResponseEntity<List<Transaction>> {
        return ResponseEntity.ok(transactionRepository.findByPlayerId(playerId))
    }

    fun addTransaction(transaction: Transaction): ResponseEntity<Transaction> {
        return ResponseEntity.ok(transactionRepository.save(transaction))
    }
}
