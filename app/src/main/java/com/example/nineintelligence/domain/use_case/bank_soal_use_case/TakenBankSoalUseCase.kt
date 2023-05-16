package com.example.nineintelligence.domain.use_case.bank_soal_use_case

import com.example.nineintelligence.data.network.apiservice.TakenBankSoal
import com.example.nineintelligence.domain.util.Resource
import java.io.IOException

class TakenBankSoalUseCase(
    private val takenBankSoal: TakenBankSoal
) {
    suspend fun getListTakenBankSoal(): Resource<List<com.example.nineintelligence.domain.models.TakenBankSoal>> {
        return try {
            val res = takenBankSoal.takenBankSoalList()
            Resource.Success(res)
        } catch (e: Exception) {
            Resource.Error(e.message)
        } catch (e: IOException){
            Resource.Error(e.message)
        }
    }
}