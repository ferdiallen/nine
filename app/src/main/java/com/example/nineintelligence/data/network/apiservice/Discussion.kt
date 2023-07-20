package com.example.nineintelligence.data.network.apiservice

import com.example.nineintelligence.domain.models.DiscussModel
import com.example.nineintelligence.domain.util.DiscussionType

interface Discussion {
    suspend fun discussionResult(slugname: String,discussionType: DiscussionType): List<DiscussModel>
}