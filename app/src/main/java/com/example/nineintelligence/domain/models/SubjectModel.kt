package com.example.nineintelligence.domain.models

data class SubjectModel(
    val id: Int,
    val subjectName: String,
    val progress: Float
)

data class SubjectReadingModel(
    val title: String,
    val content: String
)