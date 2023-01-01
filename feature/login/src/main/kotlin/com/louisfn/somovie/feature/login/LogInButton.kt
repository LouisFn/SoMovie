package com.louisfn.somovie.feature.login

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.louisfn.somovie.ui.common.R
import com.louisfn.somovie.ui.component.DefaultImage

@Composable
fun LogInButton(logInManager: LogInManager, modifier: Modifier = Modifier) {
    LogInButton(modifier, onClick = { logInManager.start() })
}

@Composable
fun LogInButton(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Button(modifier = modifier, onClick = onClick) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(id = R.string.account_log_in),
                fontSize = 16.sp,
                style = MaterialTheme.typography.button
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultImage(
                modifier = Modifier.height(24.dp),
                painter = painterResource(id = R.drawable.ic_tmdb)
            )
        }
    }
}
