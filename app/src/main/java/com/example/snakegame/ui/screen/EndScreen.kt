package com.example.snakegame.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.snakegame.ui.activity.GameActivity
import com.example.snakegame.ui.componants.AppBar
import com.example.snakegame.ui.componants.AppButton
import com.example.snakegame.ui.componants.DisplayLarge
import com.example.snakegame.ui.componants.TitleLarge


@Composable
fun EndScreen(score: Int, onTryAgain: () -> Unit) {
    val activity = LocalContext.current as GameActivity
    AppBar(title = "", onBackClicked = { activity.finish() }) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DisplayLarge(
                modifier = Modifier.padding(8.dp),
                text = stringResource(id = com.example.snakegame.R.string.game_over),
                textAlign = TextAlign.Center
            )
            TitleLarge(
                modifier = Modifier.padding(8.dp),
                text = stringResource(id = com.example.snakegame.R.string.your_score, score),
            )
            AppButton(text = stringResource(id = com.example.snakegame.R.string.try_again))
            { onTryAgain.invoke()  } // Restart the game via the provided callback
            AppButton(
                text = "Back to Menu"
            ) {
                // Navigate back to the MenuScreen
                activity.startActivity(
                    android.content.Intent(activity, com.example.snakegame.ui.activity.MainActivity::class.java)
                        .addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP or android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                )
                activity.finish() // Close the current activity
            }
        }

    }
}
