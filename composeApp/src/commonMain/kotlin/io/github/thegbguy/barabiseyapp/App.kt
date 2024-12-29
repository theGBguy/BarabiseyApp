package io.github.thegbguy.barabiseyapp

import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import io.github.thegbguy.barabiseyapp.event.Event
import io.github.thegbguy.barabiseyapp.event.EventDetailsScreen
import io.github.thegbguy.barabiseyapp.home.HomeScreen
import io.github.thegbguy.barabiseyapp.reminders.RemindersScreen
import io.github.thegbguy.barabiseyapp.settings.SettingsScreen
import io.github.thegbguy.barabiseyapp.splash.SplashScreen
import io.github.thegbguy.barabiseyapp.theme.AppTheme
import io.github.thegbguy.barabiseyapp.utils.serializableType
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
data object Splash

@Serializable
data object Home

@Serializable
data class EventDetail(val event: Event) {
    companion object {
        val typeMap = mapOf(typeOf<Event>() to serializableType<Event>())

        fun from(savedStateHandle: SavedStateHandle) =
            savedStateHandle.toRoute<EventDetail>(typeMap)

        fun from(navBackStackEntry: NavBackStackEntry) =
            navBackStackEntry.toRoute<EventDetail>()
    }
}

@Serializable
data object Reminders

@Serializable
data object Settings

@Composable
internal fun BarabiseyCommunityApp() {
    val navController = rememberNavController()

    AppTheme {
        NavHost(
            navController = navController,
            startDestination = Splash
        ) {
            composable<Splash> {
                SplashScreen {
                    navController.navigate(Home)
                }
            }
            composable<Home> {
                HomeScreen(
                    onEventClick = { event ->
                        navController.navigate(EventDetail(event))
                    },
                    onRemindersClick = {
                        navController.navigate(Reminders)
                    },
                    onSettingsClick = {
                        navController.navigate(Settings)
                    }
                )
            }
            composable<EventDetail>(
                typeMap = EventDetail.typeMap
            ) { backstackEntry ->
                val eventDetail = EventDetail.from(backstackEntry)
                EventDetailsScreen(
                    event = eventDetail.event,
                    onBackPressed = {
                        navController.navigateUp()
                    }
                )
            }
            composable<Reminders> {
                RemindersScreen(
                    onHomeClick = {
                        navController.navigate(Home)
                    },
                    onSettingsClick = {
                        navController.navigate(Settings)
                    }
                )
            }
            composable<Settings> {
                SettingsScreen(
                    onRemindersClick = {
                        navController.navigate(Reminders)
                    },
                    onHomeClick = {
                        navController.navigate(Home)
                    },
                    onBackPressed = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}