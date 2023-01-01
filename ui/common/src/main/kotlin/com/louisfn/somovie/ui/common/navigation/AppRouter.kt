package com.louisfn.somovie.ui.common.navigation

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.widget.Toast
import androidx.annotation.UiThread
import com.louisfn.somovie.ui.common.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppRouter @Inject constructor(
    @ApplicationContext private val context: Context
) {

    @UiThread
    fun shareText(text: String) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }

        val chooser = Intent.createChooser(intent, null)
        chooser.addFlags(FLAG_ACTIVITY_NEW_TASK)

        try {
            context.startActivity(chooser)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, R.string.external_app_activity_not_found, Toast.LENGTH_LONG).show()
        }
    }

    @UiThread
    fun openYoutubeVideo(key: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$key"))
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)

        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            openBrowser(Uri.parse("http://www.youtube.com/watch?v=$key"))
        }
    }

    @UiThread
    fun openBrowser(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)

        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, R.string.external_app_activity_not_found, Toast.LENGTH_LONG).show()
        }
    }

    @UiThread
    fun relaunchApp() = with(context) {
        packageManager.getLaunchIntentForPackage(packageName)
            ?.apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK) }
            ?.let(::startActivity)

        Runtime.getRuntime().exit(0)
    }
}
