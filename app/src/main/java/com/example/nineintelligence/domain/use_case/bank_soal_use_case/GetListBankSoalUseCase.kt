package com.example.nineintelligence.domain.use_case.bank_soal_use_case

import com.example.nineintelligence.data.network.apiservice.GetBankSoalList
import com.example.nineintelligence.domain.models.BankSoalModel
import com.example.nineintelligence.domain.util.Resource
import java.io.IOException

class GetListBankSoalUseCase(
    private val getBankSoalList: GetBankSoalList
) {
    suspend fun getBankSoalList(): Resource<List<BankSoalModel>> {
        return try {
            Resource.Success(getBankSoalList.getBankSoalList())
        } catch (e: Exception) {
            Resource.Error(e.message)
        } catch (e: IOException) {
            Resource.Error(e.message)
        }
    }
}