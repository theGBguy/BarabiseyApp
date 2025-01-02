package io.github.thegbguy.barabiseyapp.home

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import barabiseyapp.composeapp.generated.resources.Res
import barabiseyapp.composeapp.generated.resources.app_name
import barabiseyapp.composeapp.generated.resources.home
import barabiseyapp.composeapp.generated.resources.reminders
import barabiseyapp.composeapp.generated.resources.settings
import io.github.thegbguy.barabiseyapp.event.Event
import io.github.thegbguy.barabiseyapp.firebase.getEvents
import io.github.thegbguy.barabiseyapp.utils.getFormattedNepaliDate
import io.github.thegbguy.barabiseyapp.utils.getFormattedTime
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeScreen(
    onEventClick: (Event) -> Unit,
    onRemindersClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val events by getEvents().collectAsStateWithLifecycle(emptyList())

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
            events = events,
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    events: List<Event>,
    onEventClick: (Event) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        stickyHeader {
            NepaliDateCard()
        }

        items(events, key = { it.title }) { event ->
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

@Composable
fun NepaliDateCard() {
    val currentDate = getFormattedNepaliDate()
    var currentTime by remember {
        mutableStateOf(getFormattedTime())
    }

    LaunchedEffect(Unit) {
        while (isActive) {
            delay(1000L)
            currentTime = getFormattedTime()
        }
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = currentDate,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = currentTime,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}