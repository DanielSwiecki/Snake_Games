package com.example.snakegame.domain.navigation


sealed class Screen(val route: String) {
    object Menu : Screen("menu_screen")
    object HighScores : Screen("high_scores_screen")
    object Settings : Screen("settings_screen")
    object About : Screen("about_screen")
    object NewGame : Screen("new_game_screen")
    object Difficulty: Screen("difficulty_screen")
}
