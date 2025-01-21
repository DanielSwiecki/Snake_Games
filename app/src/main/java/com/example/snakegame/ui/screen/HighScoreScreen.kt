package com.example.snakegame.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.snakegame.data.cache.GameCache
import com.example.snakegame.domain.base.TOP_10
import com.example.snakegame.data.model.HighScore
import com.example.snakegame.ui.componants.AppBar
import com.example.snakegame.ui.componants.TitleLarge

@Composable
fun HighScoreScreen(navController: NavHostController) {
    val dataStore = GameCache(LocalContext.current)
    val highScores =
        dataStore.getHighScores.collectAsState(initial = listOf()).value.sortedByDescending { it.score }
            .take(TOP_10)
    AppBar(
        title = stringResource(com.example.snakegame.R.string.high_score),
        onBackClicked = { navController.popBackStack() }) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = contentPadding.calculateTopPadding(),
                    bottom = 16.dp,
                    start  = 16.dp,
                    end    = 16.dp
                )
                .border(width = 2.dp, color = MaterialTheme.colorScheme.onBackground),
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                TitleLarge(
                    text = stringResource(com.example.snakegame.R.string.player_name),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                TitleLarge(
                    text = stringResource(com.example.snakegame.R.string.score),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(items = highScores) {
                    HighScoreItem(it)
                }
            }
        }
    }

}

@Composable
private fun HighScoreItem(highScore: HighScore) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        TitleLarge(
            text = highScore.playerName,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        TitleLarge(
            text = highScore.score.toString(),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
    }
}