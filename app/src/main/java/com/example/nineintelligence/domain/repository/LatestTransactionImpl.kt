package com.example.nineintelligence.domain.repository

import com.example.nineintelligence.BuildConfig
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.data.network.apiservice.LatestTransaction
import com.example.nineintelligence.domain.models.LatestPaymentResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType

class LatestTransactionImpl(
    private val http: HttpClient,
    private val authPrefs: AuthPrefs
) : LatestTransaction {
    override suspend fun latestTransaction(): LatestPaymentResponse {
        return http.get("${BuildConfig.BASE_URL}payment/latest") {
            bearerAuth(authPrefs.readTokenNonBlocking() ?: return@get)
            contentType(ContentType.Application.Json)
        }.body()
    }
}