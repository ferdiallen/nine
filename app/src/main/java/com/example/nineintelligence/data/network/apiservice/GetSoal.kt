package com.example.nineintelligence.data.network.apiservice

import com.example.nineintelligence.domain.models.GetSoalModel
import com.example.nineintelligence.domain.util.DiscussionType

interface GetSoal {
    suspend fun getSoal(slugName: String,type:DiscussionType): List<GetSoalModel>
}