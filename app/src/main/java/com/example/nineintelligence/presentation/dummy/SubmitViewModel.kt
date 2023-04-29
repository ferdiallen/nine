package com.example.nineintelligence.presentation.dummy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nineintelligence.domain.models.SubmitModel
import com.example.nineintelligence.domain.models.SubmitResponse
import com.example.nineintelligence.domain.models.UserAnswerData
import com.example.nineintelligence.domain.use_case.tryout_use_case.TryoutSubmitUseCase
import com.example.nineintelligence.domain.util.Resource
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class SubmitViewModel(
    private val submitUseCase: TryoutSubmitUseCase
) : ViewModel() {

    init {
        submitData()
    }

    private fun submitData() = viewModelScope.launch {
        val dataModel = SubmitModel(
            listOf(UserAnswerData(23, "Mengerti"))
        )
        submitUseCase.submitAnswer(dataModel.userAnswers, "tryout-testing-1").let {
            when (it) {
                is Resource.Success -> {
                    val decoder = Json.decodeFromString<SubmitResponse>(it.data ?: "")
                    println(decoder.score)
                }

                is Resource.Error -> {

                }

                else -> {

                }
            }
        }
    }
}