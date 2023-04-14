package com.example.nineintelligence.presentation.exam

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nineintelligence.domain.models.GetSoalModel
import com.example.nineintelligence.domain.models.SubmitModel
import com.example.nineintelligence.domain.models.SubmitResponse
import com.example.nineintelligence.domain.use_case.exam_use_case.GetSoalUseCase
import com.example.nineintelligence.domain.use_case.tryout_use_case.TryoutSubmitUseCase
import com.example.nineintelligence.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class ExamViewModel(
    private val getSoal: GetSoalUseCase,
    private val submitUseCase: TryoutSubmitUseCase
) : ViewModel() {
    private var _listQuestion = MutableStateFlow<List<GetSoalModel>>(emptyList())
    val listQuestion = _listQuestion.asStateFlow()
    private var _resultSubmit = MutableStateFlow<SubmitResponse?>(null)
    val resultSubmit = _resultSubmit.asStateFlow()
    private var _savedAnswerStateFlow = MutableStateFlow<List<Pair<Int,
            SubmitModel.UserAnswerData>>?>(null)
    val savedAnswerStateFlow = _savedAnswerStateFlow.asStateFlow()

    private val _savedTime = MutableStateFlow(0.seconds)
    val savedTime = _savedTime.asStateFlow()

    suspend fun retrieveSoalList(slugName: String) {
        when (val res = getSoal.getSoal(slugName)) {
            is Resource.Success -> {
                _listQuestion.update {
                    res.data ?: emptyList()
                }
            }

            is Resource.Error -> {

            }

            is Resource.Loading -> {

            }

            is Resource.Empty -> {

            }
        }
    }

    fun saveAnswer(answer: SubmitModel, slugName: String) = viewModelScope.launch(Dispatchers.IO) {
        val res = submitUseCase.submitAnswer(answer.userAnswers, slugName)
        when (res) {
            is Resource.Success -> {
                println(res.data)
                val decoder = Json.decodeFromString<SubmitResponse>(res.data ?: return@launch)
                _resultSubmit.update {
                    decoder
                }
            }

            is Resource.Error -> {

            }

            is Resource.Empty -> {

            }

            is Resource.Loading -> {

            }
        }
    }

    fun setLengthTime(time: Int) {
        _savedTime.update {
            time.minutes
        }
        runTimer()
    }

    private fun runTimer() = viewModelScope.launch(Dispatchers.IO) {
        _savedTime.collectLatest {
            while (it != 0.seconds) {
                countDownTime()
            }
        }
    }

    private suspend fun countDownTime() {
        _savedTime.update {
            delay(1000L)
            it - 1.seconds
        }
    }

    fun stateFlowMethodSaveAnswer(indexQuestion: Int, answer: SubmitModel.UserAnswerData) =
        viewModelScope.launch {
            _savedAnswerStateFlow.update {
                val currentArray = it?.toMutableList()
                val newArray = Pair(indexQuestion, answer)
                return@update if (it == null) {
                    listOf(newArray)
                } else {
                    if (it.contains(it.find { out ->
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

            }
        }

}