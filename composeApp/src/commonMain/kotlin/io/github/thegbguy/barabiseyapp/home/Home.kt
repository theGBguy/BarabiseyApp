package io.github.thegbguy.barabiseyapp.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import barabiseyapp.composeapp.generated.resources.Res
import barabiseyapp.composeapp.generated.resources.app_name
import barabiseyapp.composeapp.generated.resources.home
import barabiseyapp.composeapp.generated.resources.reminders
import barabiseyapp.composeapp.generated.resources.settings
import io.github.thegbguy.barabiseyapp.event.Event
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeScreen(
    onEventClick: (Event) -> Unit,
    onRemindersClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Scaffold(
        topBar = { HomeTopBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Refresh action */ }) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh"
                )
            }
        },
        bottomBar = {
            BottomNavigation(
                onHomeClick = {},
                onRemindersClick = onRemindersClick,
                onSettingsClick = onSettingsClick
            )
        }
    ) { paddingValues ->
        HomeContent(
            modifier = Modifier.padding(paddingValues),
            onEventClick = onEventClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(Res.string.app_name),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        actions = {
            IconButton(onClick = { /* Profile action */ }) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile"
                )
            }
        }
    )
}

@Composable
fun HomeContent(modifier: Modifier = Modifier, onEventClick: (Event) -> Unit) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(eventList, key = { it.time }) { event ->
            EventCard(
                title = event.title,
                description = event.description,
                onClick = {
                    onEventClick(event)
                }
            )
        }
    }
}

@Composable
fun EventCard(title: String, description: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = description, fontSize = 14.sp, color = Color.Gray)
        }
    }
}

@Composable
fun BottomNavigation(
    selectedIndex: Int = 0,
    onHomeClick: () -> Unit,
    onRemindersClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") },
            label = { Text(stringResource(Res.string.home)) },
            selected = selectedIndex == 0,
            onClick = onHomeClick
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Reminders"
                )
            },
            label = { Text(stringResource(Res.string.reminders)) },
            selected = selectedIndex == 1,
            onClick = onRemindersClick
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text(stringResource(Res.string.settings)) },
            selected = selectedIndex == 2,
            onClick = onSettingsClick
        )
    }
}

internal val eventList = listOf(
    Event(
        title = "Event 1",
        description = "Description for Event 1",
        date = "2023-09-15",
        time = "10:00 AM",
        location = "Location 1"
    ),
    Event(
        title = "Event 2",
        description = "Description for Event 2",
        date = "2023-09-16",
        time = "2:30 PM",
        location = "Location 2"
    ),
    Event(
        title = "Event 3",
        description = "Description for Event 3",
        date = "2023-09-17",
        time = "9:45 AM",
        location = "Location 3"
    ),
    Event(
        title = "Event 4",
        description = "Description for Event 4",
        date = "2023-09-18",
        time = "11:15 AM",
        location = "Location 4"
    )
)
