package com.example.nineintelligence.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HistoryModel(
    @SerialName("to_hasil") val hasilTryout: SubmitResponse? = null,
    @SerialName("to_details") val tryoutDetails: ToDetails? = null
)

@Serializable
data class HistoryBankSoalModel(
    @SerialName("bs_hasil") val hasilTryout: BankSoalSubmitResponse? = null,
    @SerialName("bs_details") val tryoutDetails: BankSoalDetails? = null
)


@Serializable
data class HistoryBankSoalTryout(
    @SerialName("to_content") val tryoutContent: List<HistoryModel> = emptyList(),
    @SerialName("bs_content") val bankSoalContent: List<HistoryBankSoalModel> = emptyList()
)