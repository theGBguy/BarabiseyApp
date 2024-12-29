package io.github.thegbguy.barabiseyapp.reminders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import barabiseyapp.composeapp.generated.resources.Res
import barabiseyapp.composeapp.generated.resources.no_reminders
import barabiseyapp.composeapp.generated.resources.reminders
import io.github.thegbguy.barabiseyapp.home.BottomNavigation
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersScreen(
    onHomeClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val reminders = remember {
        reminders.toMutableStateList()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.reminders),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Add Reminder action */ }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Reminder"
                )
            }
        },
        bottomBar = {
            BottomNavigation(
                selectedIndex = 1,
                onHomeClick = onHomeClick,
                onRemindersClick = {},
                onSettingsClick = onSettingsClick
            )
        }
    ) { paddingValues ->
        ReminderList(
            reminders = reminders,
            modifier = Modifier.padding(paddingValues),
            onDeleteReminder = {
                reminders.removeAt(it)
            }
        )
    }
}

@Composable
fun ReminderList(
    reminders: List<Reminder>,
    modifier: Modifier = Modifier,
    onDeleteReminder: (Int) -> Unit
) {
    if (reminders.isEmpty()) {
        // Empty State
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(Res.string.no_reminders),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(reminders.size) { index ->
                ReminderCard(
                    reminder = reminders[index],
                    onDelete = { onDeleteReminder(index) }
                )
            }
        }
    }
}

@Composable
fun ReminderCard(reminder: Reminder, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = reminder.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = reminder.time, fontSize = 14.sp, color = Color.Gray)
            }
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Reminder",
                    tint = Color.Red
                )
            }
        }
    }
}

internal val reminders = listOf(
    Reminder(
        id = "1",
        title = "Reminder 1",
        time = "10:00 AM"
    ),
    Reminder(
        id = "2",
        title = "Reminder 2",
        time = "2:30 PM"
    ),
    Reminder(
        id = "3",
        title = "Reminder 3",
        time = "9:45 AM"
    ),
    Reminder(
        id = "4",
        title = "Reminder 4",
        time = "11:15 AM"
    )
)
