package com.example.nineintelligence.data.network.apiservice

import com.example.nineintelligence.domain.models.SubmitModel

interface SubmitAnswer {
    suspend fun submitAnswer(answer: SubmitModel,slugName:String): SubmitModel
}