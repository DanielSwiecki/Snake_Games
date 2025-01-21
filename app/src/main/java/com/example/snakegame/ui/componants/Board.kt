package com.example.snakegame.ui.componants

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.snakegame.data.model.State
import com.example.snakegame.ui.theme.DarkGreen
import com.example.snakegame.ui.theme.Shapes

@Composable
fun Board(state: State) {
    Box(
        Modifier.fillMaxSize(), // Outer box to occupy the entire screen
        contentAlignment = Alignment.Center // Center the game board
    ) {
        BoxWithConstraints(
            Modifier
                .fillMaxWidth(0.95f) // Use 95% of the screen width
                .fillMaxHeight(0.85f) // Use 75% of the screen height
                .border(4.dp, DarkGreen) // Add the border
        ) {
            // Calculate tile size based on the smaller dimension
            val tileSize = (maxWidth / state.maxTiles.first).coerceAtMost(maxHeight / state.maxTiles.second)

            // Update state to provide max tile counts dynamically
            LaunchedEffect(maxWidth, maxHeight, tileSize) {
                state.maxTiles = Pair(
                    (maxWidth / tileSize).toInt(),
                    (maxHeight / tileSize).toInt()
                )
            }

            // Render the food
            Box(
                Modifier
                    .offset(
                        x = tileSize * state.food.first,
                        y = tileSize * state.food.second
                    )
                    .size(tileSize)
                    .background(Color.Red, CircleShape)
            )

            // Render obstacles
            state.obstacles.forEach {
                Box(
                    modifier = Modifier
                        .offset(
                            x = tileSize * it.first,
                            y = tileSize * it.second
                        )
                        .size(tileSize)
                        .background(Color.Blue, CircleShape)
                )
            }

            // Render the snake
            state.snake.forEach {
                Box(
                    modifier = Modifier
                        .offset(
                            x = tileSize * it.first,
                            y = tileSize * it.second
                        )
                        .size(tileSize)
                        .background(DarkGreen, Shapes.small)
                )
            }
        }
    }
}