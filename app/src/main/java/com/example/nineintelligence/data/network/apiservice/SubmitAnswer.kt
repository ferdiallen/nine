package com.example.nineintelligence.data.network.apiservice

import com.example.nineintelligence.domain.models.SubmitModel

interface SubmitAnswer {
    suspend fun submitAnswer(answer: List<String>,slugName:String): SubmitModel
}