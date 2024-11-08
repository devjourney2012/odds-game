package com.example.odds_game.model

sealed class RegistrationResponse {
    data class Success(val player: Player, val message: String) : RegistrationResponse()
    data class Error(val message: String) : RegistrationResponse()
}
