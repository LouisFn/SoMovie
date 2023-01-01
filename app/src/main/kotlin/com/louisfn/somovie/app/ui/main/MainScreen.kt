package com.louisfn.somovie.app.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.louisfn.somovie.core.logger.Logger
import com.louisfn.somovie.feature.home.container.HomeDestination
import com.louisfn.somovie.feature.navigation.MainNavHost
import com.louisfn.somovie.ui.common.error.ErrorsLayout
import com.louisfn.somovie.ui.common.extension.collectAsStateLifecycleAware
import com.louisfn.somovie.ui.common.extension.pxToDp
import com.louisfn.somovie.ui.common.extension.top
import com.louisfn.somovie.ui.component.ColumnSpacer
import com.louisfn.somovie.ui.component.IndeterminateProgressIndicator
import com.louisfn.somovie.ui.common.R as commonR

@Composable
internal fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    Logger.d("Navigation - MainScreen")

    val uiState by viewModel.uiState.collectAsStateLifecycleAware()
    val errors by viewModel.errors.collectAsStateLifecycleAware()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        when (uiState) {
            is MainUiState.Loading ->
                AppLoader()
            is MainUiState.Content ->
                MainNavHost(
                    startDestination = HomeDestination,
                    modifier = Modifier.fillMaxSize()
                )
        }

        ErrorsLayout(
            errors = errors,
            modifier = Modifier.padding(top = WindowInsets.statusBars.top.pxToDp())
        )
    }
}

@Composable
private fun AppLoader() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(id = commonR.string.app_name),
            style = MaterialTheme.typography.h2
        )
        ColumnSpacer(space = 32.dp)
        IndeterminateProgressIndicator()
    }
}
