package com.example.odds_game.model

import jakarta.persistence.*
import java.time.Instant
import java.time.format.DateTimeFormatter

@Entity
data class Bet(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val playerId: Long,
    val amount: Double,
    val numberBet: Int,
    var resultNumber: Int? = 0,
    var winnings: Double? = 0.0,
    val timestamp: String = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
)

