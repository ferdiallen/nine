package com.example.nineintelligence.domain.repository

import com.example.nineintelligence.BuildConfig
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.data.network.apiservice.StartTryout
import com.example.nineintelligence.domain.models.StartTryoutResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class StartTryoutImpl(
    private val http: HttpClient,
    private val authPrefs: AuthPrefs
) : StartTryout {
    override suspend fun startTryout(slugname: String): StartTryoutResponse {
        val res =
            Json.decodeFromString<StartTryoutResponse>(http.post("${BuildConfig.BASE_URL}tryouts/$slugname/start") {
                bearerAuth(authPrefs.readToken() ?: return@post)
            }.body())
        return res
    }
}