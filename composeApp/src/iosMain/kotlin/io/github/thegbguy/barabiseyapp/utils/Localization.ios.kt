package io.github.thegbguy.barabiseyapp.utils

import platform.Foundation.NSUserDefaults

actual fun changeLanguage(lang: String) {
    NSUserDefaults.standardUserDefaults.setObject(arrayListOf(lang), "AppleLanguages")
}