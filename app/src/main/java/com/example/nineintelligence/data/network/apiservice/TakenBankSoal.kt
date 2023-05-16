package com.example.nineintelligence.data.network.apiservice

import com.example.nineintelligence.domain.models.TakenBankSoal

interface TakenBankSoal {
    suspend fun takenBankSoalList():List<TakenBankSoal>
}