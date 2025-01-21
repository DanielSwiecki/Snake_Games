package com.example.snakegame.data.model

// Game state data class
data class State(
    val food: Pair<Int, Int>,
    val snake: List<Pair<Int, Int>>,
    var maxTiles: Pair<Int, Int> = Pair(22, 22), // Default values
    val obstacles: List<Pair<Int, Int>> = emptyList() // Add obstacles
)