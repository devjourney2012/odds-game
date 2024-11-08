package com.example.odds_game.model

import com.example.odds_game.constant.TransactionType
import java.time.LocalDateTime
import jakarta.persistence.*

@Entity
data class Transaction(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    val playerId: Long,
    val amount: Double,
    val transactionType: TransactionType,
    val timestamp: LocalDateTime = LocalDateTime.now()
)