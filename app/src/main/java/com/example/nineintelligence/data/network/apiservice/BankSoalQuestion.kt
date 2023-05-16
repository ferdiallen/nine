package com.example.nineintelligence.data.network.apiservice

import com.example.nineintelligence.domain.models.BankSoalExamModel

interface BankSoalQuestion {
    suspend fun getListQuestion(slugname: String): List<BankSoalExamModel>
}