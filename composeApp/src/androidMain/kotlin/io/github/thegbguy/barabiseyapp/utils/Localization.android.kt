package io.github.thegbguy.barabiseyapp.utils

import java.util.Locale

actual fun changeLanguage(lang: String) {
    val locale = Locale(lang)
    Locale.setDefault(locale)
}