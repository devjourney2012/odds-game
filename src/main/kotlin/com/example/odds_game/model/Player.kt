package com.example.odds_game.model

import jakarta.persistence.*

@Entity
data class Player(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String,
    val surname: String,
    val username: String,
    var balance: Double = 1000.0,
    var totalWinnings: Double = 0.0
)