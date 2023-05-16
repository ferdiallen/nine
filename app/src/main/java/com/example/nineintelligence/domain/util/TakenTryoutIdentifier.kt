package com.example.nineintelligence.domain.util

import com.example.nineintelligence.domain.models.HistoryModel
import com.example.nineintelligence.domain.models.TakenTryOutModel

object TakenTryoutIdentifier {
    fun List<HistoryModel>.wheterContaintsTakenTryout(data: List<TakenTryOutModel>): Int {
        val res = this.map { out ->
            val anotherRes = data.filter {
                it.tryoutDetails?.tryOutTitle != out.tryoutDetails?.tryOutTitle
            }
            anotherRes
        }
        return res.size
    }
}