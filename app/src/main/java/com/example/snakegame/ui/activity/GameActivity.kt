package com.example.snakegame.ui.activity

import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.snakegame.ui.screen.Snake
import com.example.snakegame.domain.game.Game
import kotlinx.coroutines.launch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import com.example.snakegame.data.cache.GameCache
import com.example.snakegame.domain.base.BaseActivity
import com.example.snakegame.ui.screen.EndScreen
import com.example.snakegame.data.model.HighScore

class GameActivity : BaseActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private lateinit var game: Game
    private lateinit var dataStore: GameCache
    private var isPlaying = mutableStateOf(true)
    private lateinit var highScores: List<HighScore>
    private lateinit var playerName: String
    private var difficulty: String = "normal" // Default value to prevent uninitialized access

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve difficulty from Intent
        difficulty = intent.getStringExtra("difficulty") ?: "normal"

        // Lock orientation to portrait
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Initialize game
        game = Game(
            scope = lifecycleScope,
            difficulty = difficulty,
            onTimeOut = {
                lifecycleScope.launch {
                    isPlaying.value = false
                    saveHighScore()
                }
            }
        )

        // Initialize accelerometer
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val x = event.values[0]
            val y = event.values[1]

            val threshold = 3.0f
            val normalizedX = x
            val normalizedY = y - 6

            val newDirection = when {
                normalizedY > threshold && game.move != Pair(0, 1) -> Pair(0, -1)
                normalizedY < -threshold && game.move != Pair(0, -1) -> Pair(0, 1)
                normalizedX > threshold && game.move != Pair(1, 0) -> Pair(-1, 0)
                normalizedX < -threshold && game.move != Pair(-1, 0) -> Pair(1, 0)
                else -> game.move
            }

            if (newDirection != game.move) {
                lifecycleScope.launch {
                    game.move = newDirection
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No-op
    }

    private fun restartGame() {
        isPlaying.value = true
        game = Game(
            scope = lifecycleScope,
            difficulty = difficulty,
            onTimeOut = {
                lifecycleScope.launch {
                    isPlaying.value = false
                    saveHighScore()
                }
            }
        )
    }

    @Composable
    override fun Content() {
        dataStore = GameCache(applicationContext)
        playerName = dataStore.getPlayerName.collectAsState(initial = "Player1").value
        highScores = dataStore.getHighScores.collectAsState(initial = listOf()).value

        if (isPlaying.value) {
            Snake(game = game)
        } else {
            EndScreen(score = (game.score.collectAsState(initial = 0).value)) {
                restartGame()
            }
        }
    }

    private suspend fun saveHighScore() {
        val updatedScores = highScores.toMutableList()
        updatedScores.add(HighScore(playerName, game.score.value, difficulty))
        updatedScores.sortByDescending { it.score }
        dataStore.saveHighScore(updatedScores.take(10))
    }
}
