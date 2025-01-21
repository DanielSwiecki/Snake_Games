package com.example.snakegame.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.snakegame.ui.activity.GameActivity
import com.example.snakegame.domain.game.Game
import com.example.snakegame.ui.componants.AppBar
import com.example.snakegame.ui.componants.Board
import com.example.snakegame.ui.componants.TitleLarge


@Composable
fun Snake(game: Game) {
    // Collect the game state
    val state = game.state.collectAsState(initial = null)
    val timeLeft = game.timeLeft.collectAsState(initial = 180)
    val activity = LocalContext.current as GameActivity

    // State for showing the confirmation dialog
    var showDialog by remember { mutableStateOf(false) }

    AppBar(
        title = "Score: ${(state.value?.snake?.size ?: 4) - 4}    Time: ${timeLeft.value}s",
        onBackClicked = {  showDialog = true  }) // Trigger dialog on back button click
    { contentPadding ->

        Column(
            modifier = Modifier.padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleLarge(text = "Mode: ${game.difficulty.capitalize()}")

            // Render the game board
            state.value?.let { Board(it) }
        }
    }
    // Confirmation dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Go Back to Menu?") },
            text = { Text(text = "Are you sure you want to go back to the menu? You will lose your game progress.") },
            confirmButton = {
                Button(onClick = {
                    showDialog = false
                    activity.finish() // Close the current activity to return to MenuScreen
                }) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text(text = "No")
                }
            }
        )
    }
}




