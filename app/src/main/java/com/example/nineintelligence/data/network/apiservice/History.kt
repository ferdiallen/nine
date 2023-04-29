package com.example.nineintelligence.data.network.apiservice

import com.example.nineintelligence.domain.models.HistoryModel

interface History {
    suspend fun getHistory(): List<HistoryModel>
}