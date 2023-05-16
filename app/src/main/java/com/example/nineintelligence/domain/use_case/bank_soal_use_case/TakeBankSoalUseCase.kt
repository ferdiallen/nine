package com.example.nineintelligence.domain.use_case.bank_soal_use_case

import com.example.nineintelligence.data.network.apiservice.TakeBankSoal
import com.example.nineintelligence.domain.models.TakeTryOutModel
import com.example.nineintelligence.domain.util.Resource
import java.io.IOException

class TakeBankSoalUseCase(
    private val takeBankSoal: TakeBankSoal
) {
    suspend fun takeBankSoal(slugName: String): Resource<TakeTryOutModel> {
        return try {
            val res = takeBankSoal.takeBankSoal(slugName)
            Resource.Success(res)
        } catch (e: Exception) {
            Resource.Error(e.message)
        } catch (e: IOException) {
            Resource.Error(e.message)
        }
    }
}