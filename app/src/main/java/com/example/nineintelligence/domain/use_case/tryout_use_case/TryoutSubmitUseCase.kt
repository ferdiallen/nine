package com.example.nineintelligence.domain.use_case.tryout_use_case

import com.example.nineintelligence.data.network.apiservice.SubmitAnswer
import com.example.nineintelligence.domain.models.SubmitModel
import com.example.nineintelligence.domain.util.Resource

class TryoutSubmitUseCase(
    private val submitAnswer: SubmitAnswer
) {
    suspend fun submitAnswer(data: List<SubmitModel.UserAnswerData>, slugName: String): String {
        val res = submitAnswer.submitAnswer(SubmitModel(data), slugName)
    }
}