package com.example.nineintelligence.data.network.apiservice

import com.example.nineintelligence.domain.models.DiscussModel

interface Discussion {
    suspend fun discussionResult(slugname: String): List<DiscussModel>
}