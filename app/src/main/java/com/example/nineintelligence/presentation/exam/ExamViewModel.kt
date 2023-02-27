package com.example.nineintelligence.presentation.exam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExamViewModel : ViewModel() {
    private var _savedAnswer = MutableStateFlow<MutableList<Pair<Int, String>>?>(null)

    val savedAnswer = _savedAnswer.asStateFlow()

    fun saveAnswer(answer: String, indexQuestion: Int) = viewModelScope.launch {
        val oldData = _savedAnswer.value
        val newData = mutableListOf(Pair(indexQuestion, answer)).apply {
            if (oldData?.contains(this.first()) == true) {
               val detectedData = oldData.find {
                    it.first == this.first().first
                }?.second
                detectedData?.let {

                }
                remove(this.first())
                return@apply
            }
            addAll(oldData ?: emptyList())
        }
        _savedAnswer.tryEmit(
            newData
        )
    }
}