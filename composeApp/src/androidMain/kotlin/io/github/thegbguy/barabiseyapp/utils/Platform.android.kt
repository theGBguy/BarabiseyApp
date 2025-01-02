package io.github.thegbguy.barabiseyapp.utils

import android.content.Intent
import coil3.PlatformContext


actual fun onApplicationStartPlatformSpecific() {
}

actual fun share(context: PlatformContext, text: String) {
    val sendIntent = Intent()
    sendIntent.setAction(Intent.ACTION_SEND)
    sendIntent.putExtra(Intent.EXTRA_TEXT, text)
    sendIntent.setType("text/plain")

    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}