package io.github.thegbguy.barabiseyapp.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import barabiseyapp.composeapp.generated.resources.Res
import barabiseyapp.composeapp.generated.resources.language
import barabiseyapp.composeapp.generated.resources.settings
import barabiseyapp.composeapp.generated.resources.theme
import io.github.thegbguy.barabiseyapp.home.BottomNavigation
import io.github.thegbguy.barabiseyapp.theme.LocalThemeIsDark
import io.github.thegbguy.barabiseyapp.utils.Language
import io.github.thegbguy.barabiseyapp.utils.LocalLanguage
import io.github.thegbguy.barabiseyapp.utils.changeLanguage
import io.github.thegbguy.barabiseyapp.utils.languageState
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onRemindersClick: () -> Unit,
    onHomeClick: () -> Unit,
    onBackPressed: () -> Unit
) {
    var isDark by LocalThemeIsDark.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.settings),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigation(
                selectedIndex = 2,
                onHomeClick = onHomeClick,
                onRemindersClick = onRemindersClick,
                onSettingsClick = {}
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Theme Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.theme),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Switch(
                    checked = isDark,
                    onCheckedChange = {
                        isDark = !isDark
                    }
                )
            }

            // Language Selection
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(Res.string.language),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                LanguageDropdown(
                    modifier = Modifier.wrapContentWidth()
                )
            }
        }
    }
}

@Composable
fun LanguageDropdown(
    modifier: Modifier = Modifier
) {
    val currentLanguage = LocalLanguage.current
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        TextButton(
            onClick = { expanded = true }
        ) {
            Text(text = currentLanguage.name, fontSize = 16.sp)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listOf(Language.English, Language.Nepali).forEach { language ->
                key(language.name) {
                    DropdownMenuItem(
                        text = { Text(language.name) },
                        onClick = {
                            expanded = false
                            languageState.value = language
                            changeLanguage(language.isoFormat)
                        }
                    )
                }
            }
        }
    }
}
