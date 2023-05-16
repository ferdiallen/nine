package com.example.nineintelligence.domain.repository

import com.example.nineintelligence.BuildConfig
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.data.network.apiservice.TakeBankSoal
import com.example.nineintelligence.domain.models.TakeTryOutModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class TakeBankSoalImpl(
    private val http: HttpClient,
    private val prefs: AuthPrefs
) : TakeBankSoal {
    override suspend fun takeBankSoal(slugName: String): TakeTryOutModel {
        return http.post("${BuildConfig.BASE_URL}banksoal/take/$slugName") {
            bearerAuth(prefs.readTokenNonBlocking() ?: return@post)
            contentType(ContentType.Application.Json)
            setBody(TakeTryOutModel(1))
        }.body()
    }
}