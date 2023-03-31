package com.example.nineintelligence.domain.repository

import com.example.nineintelligence.BuildConfig
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.data.network.apiservice.TakeTryout
import com.example.nineintelligence.domain.models.TakeTryOutModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class TakeTryoutImpl(
    private val http: HttpClient,
    private val authPrefs: AuthPrefs
) : TakeTryout {
    override suspend fun takeTryOut(slugName: String): TakeTryOutModel {
        return http.post("${BuildConfig.BASE_URL}tryouts/take/$slugName") {
            contentType(ContentType.Application.Json)
            setBody(TakeTryOutModel(0))
            bearerAuth(authPrefs.readToken() ?: return@post)
        }.body()
    }
}