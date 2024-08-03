package com.example.notesapp.presentation.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.notesapp.presentation.onboarding.model.Page
import com.example.notesapp.presentation.onboarding.model.pages
import com.example.notesapp.util.Dimens.MEDIUM_PADDING2

@Composable
fun OnboardingPage(
    modifier: Modifier = Modifier,
    page: Page
) {

    Column(
        modifier = modifier
    ) {
        // Onboarding Page Image :->
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.6f),
            painter = painterResource(id = page.image),
            colorFilter = if (isSystemInDarkTheme() && page == pages[1]) ColorFilter.tint(Color.White) else null,
            contentDescription = page.title,
        )

        Spacer(modifier = Modifier.height(35.dp))

        // Onboarding Page Title :->
        Text(
            text = page.title,
            modifier = Modifier.padding(horizontal = MEDIUM_PADDING2),
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Onboarding Page Description :->
        Text(
            text = page.description,
            modifier = Modifier.padding(horizontal = MEDIUM_PADDING2),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }

}