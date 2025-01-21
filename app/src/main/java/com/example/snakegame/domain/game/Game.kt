package com.example.snakegame.domain.game

import com.example.snakegame.data.model.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.Random

class Game(private val scope: CoroutineScope ,
           val difficulty: String,
           private val onTimeOut: () -> Unit) {

    private val mutex = Mutex()
    private val mutableState = MutableStateFlow(
        State(food = Pair(5, 5), snake = listOf(Pair(7, 7)),obstacles = emptyList())
    )
    val state: Flow<State> = mutableState
    private var remainingTime = if (difficulty == "extreme") 5 else 5 // Extreme: 4 minutes, Normal: 3 minutes
    val timeLeft = MutableStateFlow(remainingTime)
    val score = MutableStateFlow(0)

    var move = Pair(1, 0)
        set(value) {
            scope.launch {
                mutex.withLock {
                    field = value
                }
            }
        }


    init {
        scope.launch {
            var snakeLength = 4
            val isExtreme = difficulty == "extreme"
            // Generate initial obstacles
            if (isExtreme) {
                mutableState.update { currentState ->
                    currentState.copy(obstacles = generateObstacles(currentState.maxTiles.first, currentState.maxTiles.second))
                }
            }
            // Timer logic
            launch {
                while (remainingTime > 0) {
                    delay(1000) // Decrement time every second
                    remainingTime--
                    timeLeft.value = remainingTime
                }
                onTimeOut() // Trigger game over when time runs out
            }
            // Game loop
            while (true) {
                delay(150)
                mutableState.update { currentState ->
                    val maxX = currentState.maxTiles.first
                    val maxY = currentState.maxTiles.second

                    val newPosition = currentState.snake.first().let { pos ->
                        mutex.withLock {
                            Pair(
                                (pos.first + move.first + maxX) % maxX,
                                (pos.second + move.second + maxY) % maxY
                            )
                        }
                    }

                    // Check if snake eats red food
                    val newFood = if (newPosition == currentState.food) {
                        snakeLength++
                        score.value++ // Increment score
                        Pair(Random().nextInt(maxX), Random().nextInt(maxY))
                    } else {
                        currentState.food
                    }

                    // Check if snake hits blue obstacle
                    val newObstacles = currentState.obstacles.map { obstacle ->
                        if (newPosition == obstacle) {
                            snakeLength = maxOf(1, snakeLength - 1) // Shrink snake
                            Pair(Random().nextInt(maxX), Random().nextInt(maxY)) // Relocate obstacle
                        } else {
                            obstacle
                        }
                    }

                    // Check if snake collides with itself
                    if (currentState.snake.contains(newPosition)) {
                        snakeLength = 4
                        score.value = 0 // Reset score
                    }


                    // Update game state
                    currentState.copy(
                        food = newFood,
                        obstacles = newObstacles,
                        snake = listOf(newPosition) + currentState.snake.take(snakeLength - 1)
                    )
                }
            }
        }
    }

    private fun generateObstacles(maxX: Int, maxY: Int): List<Pair<Int, Int>> {
        val obstacles = mutableListOf<Pair<Int, Int>>()
        while (obstacles.size < 2) {
            val obstacle = Pair(Random().nextInt(maxX), Random().nextInt(maxY))
            if (obstacle != mutableState.value.food && !mutableState.value.snake.contains(obstacle)) {
                obstacles.add(obstacle)
            }
        }
        return obstacles
    }
}