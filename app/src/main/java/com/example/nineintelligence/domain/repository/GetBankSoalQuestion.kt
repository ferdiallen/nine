package com.example.nineintelligence.domain.repository

import com.example.nineintelligence.BuildConfig
import com.example.nineintelligence.data.network.apiservice.BankSoalQuestion
import com.example.nineintelligence.domain.models.BankSoalExamModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType

class GetBankSoalQuestion(
    private val http: HttpClient
) : BankSoalQuestion {
    override suspend fun getListQuestion(slugname: String): List<BankSoalExamModel> {
        return http.get("${BuildConfig.BASE_URL}banksoal/$slugname/soal") {
            contentType(ContentType.Application.Json)
        }.body()
    }
}