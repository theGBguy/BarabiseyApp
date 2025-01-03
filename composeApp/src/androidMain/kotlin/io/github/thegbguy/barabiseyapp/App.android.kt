package io.github.thegbguy.barabiseyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mmk.kmpnotifier.permission.permissionUtil

class AppActivity : ComponentActivity() {
    private val permissionUtil by permissionUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        permissionUtil.askNotificationPermission()
        setContent { BarabiseyCommunityApp() }
    }
}

@Preview
@Composable
fun AppPreview() {
    BarabiseyCommunityApp()
}
