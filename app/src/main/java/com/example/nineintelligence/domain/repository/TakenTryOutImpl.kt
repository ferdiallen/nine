package com.example.nineintelligence.domain.repository

import com.example.nineintelligence.BuildConfig
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.data.network.apiservice.TakenTryOut
import com.example.nineintelligence.domain.models.TakenTryOutModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class TakenTryOutImpl(
    private val http: HttpClient,
    private val authPrefs: AuthPrefs
) : TakenTryOut {
    override suspend fun getTryOutTakenList(): List<TakenTryOutModel> {
        val decodedFromString =
            Json.decodeFromString<List<TakenTryOutModel>>(http.get(
                "${BuildConfig.BASE_URL}tryouts/taken") {
                contentType(ContentType.Application.Json)
                bearerAuth(authPrefs.readToken() ?: return@get)
            }.body())
        return decodedFromString
    }
}