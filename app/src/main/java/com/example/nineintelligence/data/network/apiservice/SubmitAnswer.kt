package com.example.nineintelligence.data.network.apiservice

import com.example.nineintelligence.domain.models.SubmitModel
import com.example.nineintelligence.domain.util.ExamType

interface SubmitAnswer {
    suspend fun submitAnswer(answer: SubmitModel,slugName:String,type:ExamType): String
}