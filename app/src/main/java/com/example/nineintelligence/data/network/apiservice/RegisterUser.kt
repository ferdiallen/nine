package com.example.nineintelligence.data.network.apiservice

import com.example.nineintelligence.domain.models.UserModel

interface RegisterUser {
    suspend fun registerUser(
        name: String,
        email: String,
        password: String,
        phoneNumber: String
    ): UserModel?
}