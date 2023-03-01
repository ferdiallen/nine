package com.example.nineintelligence.presentation.exam

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExamViewModel : ViewModel() {
    private var savedAnswer = mutableStateListOf<Pair<Int, String>>()

    private var _savedAnswerStateFlow = MutableStateFlow<List<Pair<Int, String>>?>(null)
    val savedAnswerStateFlow = _savedAnswerStateFlow.asStateFlow()

    fun saveAnswer(answer: String, indexQuestion: Int) {
        if (savedAnswer.contains(Pair(indexQuestion, answer))) {
            savedAnswer.remove(Pair(indexQuestion, answer))
            return
        } else if (savedAnswer.isNotEmpty()) {
            val find = savedAnswer.find {
                it.first == indexQuestion
            }
            if (find != null) {
                savedAnswer.remove(find)
                savedAnswer.add(Pair(indexQuestion, answer))
            } else {
                savedAnswer.add(Pair(indexQuestion, answer))
            }
            return
        }
        savedAnswer.add(
            Pair(indexQuestion, answer)
        )
    }

    fun stateFlowMethodSaveAnswer(indexQuestion: Int, answer: String) = viewModelScope.launch {
        _savedAnswerStateFlow.update {
            val currentArray = it?.toMutableList()
            val newArray = Pair(indexQuestion, answer)
            return@update if (it == null) {
                listOf(newArray)
            } else {
                if (it.contains(newArray)) {
                    currentArray?.remove(newArray)
                    currentArray?.toList()
                } else if (it.contains(it.find { out ->
                        out.first == indexQuestion
                    })) {
                    currentArray?.remove(it.find { out ->
                        out.first == indexQuestion
                    })
                    currentArray?.add(newArray)
                    currentArray?.toList()
                } else {
                    listOf(newArray).plus(currentArray ?: emptyList())
                }
            }
        }
        _savedAnswerStateFlow.value?.forEach {
            println(it.second)
        }
    }

}