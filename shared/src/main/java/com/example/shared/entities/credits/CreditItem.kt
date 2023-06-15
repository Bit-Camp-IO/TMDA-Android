package com.example.shared.entities.credits

interface CreditItem {
    val id: Int
    val role: String
    val name: String
    val profilePath: String?
}