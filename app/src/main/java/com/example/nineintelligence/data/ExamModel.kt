package com.example.nineintelligence.data

data class ExamModel(
    val tryoutId: Int,
    val type: Int,
    val mapel: Int,
    val soalText: String,
    val answer:List<String>,
    val correctAnswer: String
)
