package com.example.nineintelligence.domain.use_case.exam_use_case

import com.example.nineintelligence.data.network.apiservice.BankSoalQuestion
import com.example.nineintelligence.domain.models.BankSoalExamModel
import com.example.nineintelligence.domain.util.Resource
import java.io.IOException

class BankSoalGetSoalUseCase(
    private val bankSoalQuestion: BankSoalQuestion
) {
    suspend fun getBankSoalQuestion(slugname: String): Resource<List<BankSoalExamModel>> {
        return try {
            val res = bankSoalQuestion.getListQuestion(slugname)
            Resource.Success(res)
        } catch (e: Exception) {
            Resource.Error(e.message)
        } catch (e: IOException) {
            Resource.Error(e.message)
        }
    }
}