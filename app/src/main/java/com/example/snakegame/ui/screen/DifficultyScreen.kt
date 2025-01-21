package com.example.snakegame.ui.screen

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.snakegame.domain.navigation.Screen
import com.example.snakegame.ui.activity.GameActivity
import com.example.snakegame.ui.componants.AppButton
import com.example.snakegame.ui.componants.TitleLarge

@Composable
fun DifficultyScreen(navController: NavHostController) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TitleLarge(text = "Select Difficulty")

        Spacer(modifier = Modifier.height(32.dp))

        // Normal Difficulty Button
        AppButton(
            modifier = Modifier.width(248.dp),
            text = "Normal"
        ) {
            val intent = Intent(context, GameActivity::class.java)
            intent.putExtra("difficulty", "normal") // Pass "normal" difficulty
            context.startActivity(intent)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Extreme Difficulty Button
        AppButton(
            modifier = Modifier.width(248.dp),
            text = "Extreme"
        ) {
            val intent = Intent(context, GameActivity::class.java)
            intent.putExtra("difficulty", "extreme") // Pass "extreme" difficulty
            context.startActivity(intent)
        }
    }
}
