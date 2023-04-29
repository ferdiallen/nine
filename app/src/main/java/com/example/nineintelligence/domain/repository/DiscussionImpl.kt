package com.example.nineintelligence.domain.repository

import com.example.nineintelligence.BuildConfig
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.data.network.apiservice.Discussion
import com.example.nineintelligence.domain.models.DiscussModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get

class DiscussionImpl(
    private val http: HttpClient,
    private val authPrefs: AuthPrefs
) : Discussion {
    override suspend fun discussionResult(slugname: String): List<DiscussModel> {
        return http.get("${BuildConfig.BASE_URL}tryouts/$slugname/pembahasan") {
            bearerAuth(authPrefs.readToken() ?: return@get)
        }.body()
    }
}