package io.github.thegbguy.barabiseyapp.utils

import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider

object AppInitializer {
    fun onApplicationStart() {
        onApplicationStartPlatformSpecific()
        GoogleAuthProvider.create(credentials = GoogleAuthCredentials(serverId = "522253599310-b5e020hmnrgtktkifikhaov0vi89i0np.apps.googleusercontent.com"))
    }
}