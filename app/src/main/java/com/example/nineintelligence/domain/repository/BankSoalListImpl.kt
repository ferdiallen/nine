package com.example.nineintelligence.domain.repository

import com.example.nineintelligence.BuildConfig
import com.example.nineintelligence.data.network.apiservice.GetBankSoalList
import com.example.nineintelligence.domain.models.BankSoalModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType

class BankSoalListImpl(
    private val http: HttpClient
) : GetBankSoalList {
    override suspend fun getBankSoalList(): List<BankSoalModel> {
        return http.get("${BuildConfig.BASE_URL}banksoal") {
            contentType(ContentType.Application.Json)
        }.body()
    }
}