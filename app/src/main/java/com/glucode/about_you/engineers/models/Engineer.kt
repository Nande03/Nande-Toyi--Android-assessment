package com.glucode.about_you.engineers.models

import android.net.Uri

data class Engineer(
    val name: String,
    val role: String,
    val imageUrl: Uri?,
    val quickStats: QuickStats,
    val questions: List<Question>,
)