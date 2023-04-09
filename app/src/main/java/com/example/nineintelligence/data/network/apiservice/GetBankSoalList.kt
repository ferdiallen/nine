package com.example.nineintelligence.data.network.apiservice

import com.example.nineintelligence.domain.models.BankSoalModel

interface GetBankSoalList {
    suspend fun getBankSoalList():List<BankSoalModel>
}