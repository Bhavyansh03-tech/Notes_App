package com.example.notesapp.presentation.onboarding.model

import androidx.annotation.DrawableRes
import com.example.notesapp.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

// Onboarding pages list :->
val pages = listOf(
    Page(
        title = "Welcome to NotesApp",
        description = "Effortlessly capture your thoughts and ideas all in one place.",
        image = R.drawable.img
    ),
    Page(
        title = "Organize Your Notes",
        description = "Keep your notes neatly categorized and easy to find.",
        image = R.drawable.img_1
    ),
    Page(
        title = "Sync Across Devices",
        description = "Access your notes anytime, anywhere with seamless synchronization.",
        image = R.drawable.img_2
    )
)