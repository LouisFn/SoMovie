package com.louisfn.somovie.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun DefaultTopAppBar(
    modifier: Modifier = Modifier,
    text: String? = null,
    backgroundColor: Color = MaterialTheme.colors.primarySurface,
    backIcon: ImageVector = Icons.Filled.ArrowBack,
    actions: @Composable RowScope.() -> Unit = {},
    navigateUp: (() -> Unit)? = null,
) {
    DefaultTopAppBar(
        modifier = modifier,
        text = text,
        navigationIcon = {
            if (navigateUp != null) {
                TopAppBarBackIcon(backIcon = backIcon, navigateUp = navigateUp)
            }
        },
        actions = actions,
        backgroundColor = backgroundColor,
    )
}

@Composable
fun DefaultTopAppBar(
    modifier: Modifier = Modifier,
    text: String? = null,
    backgroundColor: Color = MaterialTheme.colors.primarySurface,
    actions: @Composable RowScope.() -> Unit = {},
    navigationIcon: @Composable (() -> Unit)? = null,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            if (text != null) {
                Text(text = text)
            }
        },
        actions = actions,
        navigationIcon = navigationIcon,
        backgroundColor = backgroundColor,
        elevation = 0.dp,
    )
}

@Composable
private fun TopAppBarBackIcon(backIcon: ImageVector, navigateUp: () -> Unit) {
    IconButton(onClick = { navigateUp() }) {
        Icon(
            imageVector = backIcon,
            contentDescription = "",
        )
    }
}
