package com.example.nineintelligence.data.network.apiservice

import com.example.nineintelligence.domain.models.TakeTryOutModel

interface TakeTryout {
    suspend fun takeTryOut(slugName: String): TakeTryOutModel
}