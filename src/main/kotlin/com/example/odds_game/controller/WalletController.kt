package com.example.odds_game.controller

import com.example.odds_game.model.Transaction
import com.example.odds_game.service.WalletService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/wallet")
class WalletController(private val walletService: WalletService) {

    @GetMapping("/transactions/{playerId}")
    fun getTransactions(@PathVariable playerId: Long): ResponseEntity<List<Transaction>> {
        return walletService.getTransactions(playerId)
    }
}
