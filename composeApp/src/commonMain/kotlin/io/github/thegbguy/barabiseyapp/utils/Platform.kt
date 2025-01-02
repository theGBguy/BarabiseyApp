package io.github.thegbguy.barabiseyapp.utils

import coil3.PlatformContext

expect fun onApplicationStartPlatformSpecific()

expect fun share(context: PlatformContext, text: String)