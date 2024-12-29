package io.github.thegbguy.barabiseyapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.staticCompositionLocalOf

sealed class Language(val name: String, val isoFormat: String) {
    data object English : Language("English", "en")
    data object Nepali : Language("नेपाली", "ne")
}

val languageState = mutableStateOf<Language>(Language.English)
val LocalLanguage = staticCompositionLocalOf { languageState.value }

@Composable
fun LocalizedApp(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalLanguage provides languageState.value,
        content = content
    )
}

expect fun changeLanguage(lang: String)