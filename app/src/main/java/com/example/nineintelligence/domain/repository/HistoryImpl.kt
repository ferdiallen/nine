package com.example.nineintelligence.domain.repository

import com.example.nineintelligence.BuildConfig
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.data.network.apiservice.History
import com.example.nineintelligence.domain.models.HistoryModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType

class HistoryImpl(
    private val http: HttpClient,
    private val authPrefs: AuthPrefs
) : History {
    override suspend fun getHistory(): List<HistoryModel> {
        return http.get("${BuildConfig.BASE_URL}users/history") {
            contentType(ContentType.Application.Json)
            bearerAuth(authPrefs.readToken() ?: return@get)
        }.body()
    }
}