package com.example.nineintelligence.domain.use_case.tryout_use_case

import com.example.nineintelligence.data.network.apiservice.SubmitAnswer
import com.example.nineintelligence.domain.models.SubmitModel
import com.example.nineintelligence.domain.models.UserAnswerData
import com.example.nineintelligence.domain.util.Resource
import java.io.IOException

class TryoutSubmitUseCase(
    private val submitAnswer: SubmitAnswer
) {
    suspend fun submitAnswer(
        data: List<UserAnswerData>,
        slugName: String
    ): Resource<String> {
        return try {
            val res = submitAnswer.submitAnswer(SubmitModel(data), slugName)
            Resource.Success(res)
        }catch (e:Exception){
            println(e.message)
            println(e.printStackTrace())
            Resource.Error(e.message)
        }catch (e:IOException){
            println(e.message)
            Resource.Error(e.message)
        }
    }
}