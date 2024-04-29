package com.glucode.about_you.engineers.models

import android.net.Uri

data class Engineer(
    val name: String,
    val role: String = "",
    val imageUrl: Uri? = null,
    val quickStats: QuickStats = QuickStats(0,0,0),
    val questions: List<Question> = emptyList(),
)