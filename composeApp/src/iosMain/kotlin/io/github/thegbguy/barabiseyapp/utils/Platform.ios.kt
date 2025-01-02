package io.github.thegbguy.barabiseyapp.utils

import coil3.PlatformContext
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication

actual fun onApplicationStartPlatformSpecific() {
}

actual fun share(context: PlatformContext, text: String) {
    val activityViewController =
        UIActivityViewController(activityItems = listOf(text), applicationActivities = null)
    val currentViewController = UIApplication.sharedApplication().keyWindow?.rootViewController
    currentViewController?.presentViewController(
        viewControllerToPresent = activityViewController,
        animated = true,
        completion = null
    )
}