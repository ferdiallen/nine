package com.example.nineintelligence.domain.repository

import com.example.nineintelligence.BuildConfig
import com.example.nineintelligence.data.network.apiservice.GetSoal
import com.example.nineintelligence.domain.models.GetSoalModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType

class GetSoalImpl(private val http: HttpClient) : GetSoal {
    override suspend fun getSoal(slugName: String): List<GetSoalModel> {
        return http.get("${BuildConfig.BASE_URL}tryouts/$slugName/soal") {
            contentType(ContentType.Application.Json)
        }.body()
    }
}