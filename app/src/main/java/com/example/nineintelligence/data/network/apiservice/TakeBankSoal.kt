package com.example.nineintelligence.data.network.apiservice

import com.example.nineintelligence.domain.models.TakeTryOutModel

interface TakeBankSoal {
    suspend fun takeBankSoal(slugName:String) : TakeTryOutModel
}