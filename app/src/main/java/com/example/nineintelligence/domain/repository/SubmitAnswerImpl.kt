package com.example.nineintelligence.domain.repository

import com.example.nineintelligence.BuildConfig
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.data.network.apiservice.SubmitAnswer
import com.example.nineintelligence.domain.models.SubmitModel
import com.example.nineintelligence.domain.util.ExamType
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class SubmitAnswerImpl(
    private val http: HttpClient,
    private val prefs: AuthPrefs
) : SubmitAnswer {
    override suspend fun submitAnswer(
        answer: SubmitModel,
        slugName: String,
        type: ExamType
    ): String {
        val res =
            if (type == ExamType.TAKE_EXAMS) http.post(
                "${BuildConfig.BASE_URL}tryouts/" +
                        "$slugName/submit"
            ) {
                bearerAuth(prefs.readTokenNonBlocking() ?: return@post)
                contentType(ContentType.Application.Json)
                setBody(answer)
            }.body() else http.post(
                "${BuildConfig.BASE_URL}banksoal/" +
                        "$slugName/submit"
            ) {
                bearerAuth(prefs.readTokenNonBlocking() ?: return@post)
                contentType(ContentType.Application.Json)
                setBody(answer)
            }.body<String>()
        return res
    }
}