package com.example.snakegame.ui.screen

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.snakegame.data.cache.GameCache
import com.example.snakegame.ui.componants.AppBar
import com.example.snakegame.ui.componants.AppButton
import com.example.snakegame.ui.componants.DisplayLarge
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(navController: NavHostController) {
    val dataStore = GameCache(LocalContext.current)
    var text by remember { mutableStateOf(TextFieldValue("")) }
    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current
    AppBar(
        title = stringResource(com.example.snakegame.R.string.title_settings),
        onBackClicked = { navController.popBackStack() }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = 16.dp,
                    start  = 16.dp,
                    end    = 16.dp
                )
                .border(width = 2.dp, color = MaterialTheme.colorScheme.onBackground),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DisplayLarge(
                modifier = Modifier.padding(
                    top = 64.dp,
                    bottom = 16.dp,
                    start  = 16.dp,
                    end    = 16.dp
                ),
                text = stringResource(id = com.example.snakegame.R.string.player_name),
                textAlign = TextAlign.Center
            )
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
            TextField(
                value = text,
                onValueChange = { text = it },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    cursorColor = MaterialTheme.colorScheme.onBackground,
                ),
                singleLine = true,
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth()
                    .padding(horizontal = 64.dp)
                    .border(width = 2.dp, color = MaterialTheme.colorScheme.onBackground)
            )
            AppButton(
                text = stringResource(com.example.snakegame.R.string.save), modifier = Modifier
                    .width(248.dp)
                    .padding(16.dp)
            ) {
                scope.launch {
                    dataStore.savePlayerName(text.text.trim())
                    Toast.makeText(context, com.example.snakegame.R.string.player_name_updated, Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }
            }
        }
    }
}