package com.example.nineintelligence.presentation.subject

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SubjectViewModel : ViewModel() {
    var mainTitleText by mutableStateOf("")
        private set
    var subTitleText by mutableStateOf("")
        private set

    fun setMainTitle(text:String){
        mainTitleText = text
    }
    fun setSubTitle(text:String){
        subTitleText = text
    }
}