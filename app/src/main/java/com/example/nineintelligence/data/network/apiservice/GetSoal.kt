package com.example.nineintelligence.data.network.apiservice

import com.example.nineintelligence.domain.models.GetSoalModel

interface GetSoal {
    suspend fun getSoal(slugName: String): List<GetSoalModel>
}