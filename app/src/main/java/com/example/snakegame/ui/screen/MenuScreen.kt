package com.example.snakegame.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.snakegame.domain.extension.launchActivity
import com.example.snakegame.domain.navigation.Screen
import com.example.snakegame.ui.activity.GameActivity
import com.example.snakegame.ui.componants.AppButton
import com.example.snakegame.ui.componants.DisplayLarge


@Composable
fun MenuScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .border(width = 2.dp, color = MaterialTheme.colorScheme.onBackground),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val context = LocalContext.current
        DisplayLarge(text = "Snake")
        DisplayLarge(text = "Game")

//        // Navigate to GameScreen
//        AppButton(
//            modifier = Modifier
//                .width(248.dp)
//                .padding(top = 16.dp),
//            text = stringResource(id = com.example.snakegame.R.string.new_game)
//        ) { context.launchActivity<GameActivity>() }
        AppButton(
            modifier = Modifier
                .width(248.dp)
                .padding(top = 16.dp),
            text = stringResource(id = com.example.snakegame.R.string.new_game)
        ) {
            navController.navigate(Screen.Difficulty.route)
        }


        // High Scores Button
        AppButton(
            modifier = Modifier.width(248.dp),
            text = stringResource(id = com.example.snakegame.R.string.high_score)
        ) {
            navController.navigate(Screen.HighScores.route)
        }

        // Settings Button
        AppButton(
            modifier = Modifier.width(248.dp),
            text = stringResource(id = com.example.snakegame.R.string.settings)
        ) {
            navController.navigate(Screen.Settings.route)
        }

        // Navigate to AboutScreen
        AppButton(
            modifier = Modifier.width(248.dp),
            text = stringResource(id = com.example.snakegame.R.string.about)
        ) { navController.navigate(Screen.About.route) }
    }
}



