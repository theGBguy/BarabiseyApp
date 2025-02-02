package io.github.thegbguy.barabiseyapp.reminders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import barabiseyapp.composeapp.generated.resources.Res
import barabiseyapp.composeapp.generated.resources.no_reminders
import barabiseyapp.composeapp.generated.resources.reminders
import io.github.thegbguy.barabiseyapp.home.BottomNavigation
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersScreen(
    onHomeClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val viewModel = koinInject<RemindersViewModel>()
    val reminders by viewModel.reminders.collectAsStateWithLifecycle(emptyList())

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
            FloatingActionButton(onClick = { viewModel.insertReminder() }) {
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
            onDeleteReminder = { reminderId ->
                viewModel.deleteReminder(reminderId)
            }
        )
    }
}

@Composable
fun ReminderList(
    reminders: List<Reminder>,
    modifier: Modifier = Modifier,
    onDeleteReminder: (Long) -> Unit
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
            items(reminders) { reminder ->
                ReminderCard(
                    reminder = reminder,
                    onDelete = { reminder.id.toLongOrNull()?.let { onDeleteReminder(it) } }
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
                if (reminder.details != null) {
                    Text(text = reminder.details, fontSize = 14.sp, color = Color.Gray)
                }
                Text(text = reminder.dateTime, fontSize = 14.sp, color = Color.Gray)
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