package io.github.thegbguy.barabiseyapp.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import barabiseyapp.composeapp.generated.resources.Res
import barabiseyapp.composeapp.generated.resources.app_name
import barabiseyapp.composeapp.generated.resources.app_tag_line
import barabiseyapp.composeapp.generated.resources.ic_app_icon
import com.mmk.kmpauth.firebase.google.GoogleButtonUiContainerFirebase
import com.mmk.kmpauth.uihelper.google.GoogleSignInButton
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val onFirebaseResult: (Result<FirebaseUser?>) -> Unit = { result ->
        if (result.isSuccess) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("Hi ${result.getOrNull()?.displayName}, Welcome to the app!")
                onNavigateToHome()
            }
        } else {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

    LaunchedEffect(Unit) {
        Firebase.auth.currentUser?.let { user ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar("Logging in as ${user.displayName}!")
                onNavigateToHome()
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // App Logo
            Image(
                painter = painterResource(Res.drawable.ic_app_icon),
                contentDescription = "App Logo",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))

            // App Name
            Text(
                text = stringResource(Res.string.app_name),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            // Tagline
            Text(
                text = stringResource(Res.string.app_tag_line),
                fontSize = 16.sp,
                color = Color.Gray
            )

            AnimatedVisibility(visible = true) {
                GoogleButtonUiContainerFirebase(
                    modifier = Modifier.padding(top = 32.dp),
                    onResult = onFirebaseResult,
                    linkAccount = false
                ) {
                    GoogleSignInButton(
                        modifier = Modifier.fillMaxWidth().height(44.dp),
                        fontSize = 19.sp
                    ) { this.onClick() }
                }
            }
        }
    }
}
