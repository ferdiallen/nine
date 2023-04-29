package com.example.nineintelligence.data.network.apiservice

import com.example.nineintelligence.domain.models.StartTryoutResponse

interface StartTryout {
    suspend fun startTryout(slugname: String): StartTryoutResponse
}