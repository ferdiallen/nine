package com.example.nineintelligence.data.network.apiservice

import com.example.nineintelligence.domain.models.TakenTryOutModel

interface TakenTryOut {
    suspend fun getTryOutTakenList(): List<TakenTryOutModel>
}