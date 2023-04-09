package com.example.nineintelligence.domain.repository

import com.example.nineintelligence.BuildConfig
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.data.network.apiservice.SubmitAnswer
import com.example.nineintelligence.domain.models.SubmitModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post

class SubmitAnswerImpl(
    private val http:HttpClient,
    private val prefs: AuthPrefs
) :SubmitAnswer{
    override suspend fun submitAnswer(answer: List<String>,slugName:String): SubmitModel {
        return http.post("${BuildConfig.BASE_URL}tryouts/$slugName/submit"){
            bearerAuth(prefs.readToken()?: return@post)
        }.body()
    }
}