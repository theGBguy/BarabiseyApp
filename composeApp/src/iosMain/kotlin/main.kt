import androidx.compose.ui.window.ComposeUIViewController
import io.github.thegbguy.barabiseyapp.BarabiseyCommunityApp
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { BarabiseyCommunityApp() }
