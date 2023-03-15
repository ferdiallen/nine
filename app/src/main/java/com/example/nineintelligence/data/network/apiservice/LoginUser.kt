package com.example.nineintelligence.data.network.apiservice

import com.example.nineintelligence.domain.models.LoginModel

interface LoginUser {
    suspend fun loginUser(name: String, password: String): LoginModel?
}