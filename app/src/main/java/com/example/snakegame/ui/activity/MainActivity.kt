package com.example.snakegame.ui.activity

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.snakegame.domain.base.BaseActivity
import com.example.snakegame.domain.navigation.Screen
import com.example.snakegame.ui.screen.AboutScreen
import com.example.snakegame.ui.screen.DifficultyScreen
import com.example.snakegame.ui.screen.HighScoreScreen
import com.example.snakegame.ui.screen.MenuScreen
import com.example.snakegame.ui.screen.SettingScreen


class MainActivity : BaseActivity() {
    private lateinit var navController: NavHostController

    @Composable
    override fun Content() {
        navController = rememberNavController()
        SetupNavigation()
    }

    @Composable
    private fun SetupNavigation() {
        NavHost(navController = navController, startDestination = Screen.Menu.route) {
            composable(Screen.Menu.route) { MenuScreen(navController) }
            composable(Screen.About.route) { AboutScreen(navController) }
            composable(Screen.HighScores.route) { HighScoreScreen(navController) }
            composable(Screen.Settings.route) { SettingScreen(navController) }
            composable(Screen.Difficulty.route) { DifficultyScreen(navController) }
        }
    }
}

