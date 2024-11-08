package com.example.odds_game.model

sealed class BetResponse {
    data class Success(val bet: Bet, val message: String) : BetResponse()
    data class Error(val message: String) : BetResponse()
}
