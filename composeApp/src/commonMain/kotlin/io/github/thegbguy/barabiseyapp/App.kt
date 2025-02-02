package io.github.thegbguy.barabiseyapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import io.github.thegbguy.barabiseyapp.event.Event
import io.github.thegbguy.barabiseyapp.event.EventDetailsScreen
import io.github.thegbguy.barabiseyapp.home.HomeScreen
import io.github.thegbguy.barabiseyapp.login.LoginScreen
import io.github.thegbguy.barabiseyapp.reminders.RemindersScreen
import io.github.thegbguy.barabiseyapp.settings.SettingsScreen
import io.github.thegbguy.barabiseyapp.splash.SplashScreen
import io.github.thegbguy.barabiseyapp.theme.AppTheme
import io.github.thegbguy.barabiseyapp.utils.AppInitializer
import io.github.thegbguy.barabiseyapp.utils.serializableType
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
data object SplashScreen

@Serializable
data object LoginScreen

@Serializable
data object HomeScreen

@Serializable
data class EventDetailScreen(val event: Event) {
    companion object {
        val typeMap = mapOf(typeOf<Event>() to serializableType<Event>())

        fun from(savedStateHandle: SavedStateHandle) =
            savedStateHandle.toRoute<EventDetailScreen>(typeMap)

        fun from(navBackStackEntry: NavBackStackEntry) =
            navBackStackEntry.toRoute<EventDetailScreen>()
    }
}

@Serializable
data object RemindersScreen

@Serializable
data object SettingsScreen

@Composable
internal fun BarabiseyCommunityApp() {
    val navController = rememberNavController()

    AppTheme {
        NavHost(
            navController = navController,
            startDestination = SplashScreen
        ) {
            composable<SplashScreen> {
                SplashScreen {
                    navController.navigate(LoginScreen)
                }
            }
            composable<LoginScreen> {
                LoginScreen {
                    navController.navigate(HomeScreen)
                }
            }
            composable<HomeScreen> {
                HomeScreen(
                    onEventClick = { event ->
                        navController.navigate(EventDetailScreen(event))
                    },
                    onRemindersClick = {
                        navController.navigate(RemindersScreen)
                    },
                    onSettingsClick = {
                        navController.navigate(SettingsScreen)
                    }
                )
            }
            composable<EventDetailScreen>(
                typeMap = EventDetailScreen.typeMap
            ) { backstackEntry ->
                val eventDetailScreen = EventDetailScreen.from(backstackEntry)
                EventDetailsScreen(
                    event = eventDetailScreen.event,
                    onBackPressed = {
                        navController.navigateUp()
                    }
                )
            }
            composable<RemindersScreen> {
                RemindersScreen(
                    onHomeClick = {
                        navController.navigate(HomeScreen)
                    },
                    onSettingsClick = {
                        navController.navigate(SettingsScreen)
                    }
                )
            }
            composable<SettingsScreen> {
                SettingsScreen(
                    onRemindersClick = {
                        navController.navigate(RemindersScreen)
                    },
                    onHomeClick = {
                        navController.navigate(HomeScreen)
                    }
                )
            }
        }
    }
}