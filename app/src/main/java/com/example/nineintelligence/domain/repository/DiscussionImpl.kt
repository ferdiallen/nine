package com.example.nineintelligence.domain.repository

import com.example.nineintelligence.BuildConfig
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.data.network.apiservice.Discussion
import com.example.nineintelligence.domain.models.DiscussModel
import com.example.nineintelligence.domain.util.DiscussionType
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get

class DiscussionImpl(
    private val http: HttpClient,
    private val authPrefs: AuthPrefs
) : Discussion {
    override suspend fun discussionResult(
        slugname: String,
        discussionType: DiscussionType
    ): List<DiscussModel> {
        return http.get(
            "${BuildConfig.BASE_URL}${
                if (discussionType.name ==
                    DiscussionType.EXAM_DISCUSSION.name
                ) "tryouts" else "banksoal"
            }/$slugname/pembahasan"
        ) {
            bearerAuth(authPrefs.readToken() ?: return@get)
        }.body()
    }
}