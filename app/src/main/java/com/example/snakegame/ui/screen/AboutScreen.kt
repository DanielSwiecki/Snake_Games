package com.example.snakegame.ui.screen

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.snakegame.domain.base.REPO_URL
import com.example.snakegame.ui.componants.AppBar
import com.example.snakegame.ui.componants.AppButton
import com.example.snakegame.ui.componants.BodyLarge
import com.example.snakegame.ui.componants.DisplayLarge


@Composable
fun AboutScreen(navController: NavHostController) {
    val context = LocalContext.current
    val builder = remember { CustomTabsIntent.Builder() }
    val customTabsIntent = remember { builder.build() }
    AppBar(
        title = stringResource(id = com.example.snakegame.R.string.title_about),
        onBackClicked = { navController.popBackStack() }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                )
                .border(width = 2.dp, color = MaterialTheme.colorScheme.onBackground),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DisplayLarge(text = "Snake")
            DisplayLarge(text = "Game")

            BodyLarge(
                modifier = Modifier.padding(16.dp),
                text = stringResource(id = com.example.snakegame.R.string.about_game),
                textAlign = TextAlign.Justify
            )
            AppButton(
                modifier = Modifier.width(248.dp),
                text = stringResource(id = com.example.snakegame.R.string.source_code)
            ) { customTabsIntent.launchUrl(context, Uri.parse(REPO_URL)) }
        }
    }
}
