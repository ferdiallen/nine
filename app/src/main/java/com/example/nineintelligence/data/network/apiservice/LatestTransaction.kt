package com.example.nineintelligence.data.network.apiservice

import com.example.nineintelligence.domain.models.LatestPaymentResponse

interface LatestTransaction {
    suspend fun latestTransaction(): LatestPaymentResponse
}