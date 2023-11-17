package com.louisfn.somovie.core.imageurlprovider

import androidx.annotation.AnyThread
import androidx.annotation.WorkerThread
import com.louisfn.somovie.core.common.annotation.ApplicationScope
import com.louisfn.somovie.core.common.annotation.DefaultDispatcher
import com.louisfn.somovie.data.repository.TmdbConfigurationRepository
import com.louisfn.somovie.domain.model.BackdropPath
import com.louisfn.somovie.domain.model.ImagePath
import com.louisfn.somovie.domain.model.LogoPath
import com.louisfn.somovie.domain.model.PosterPath
import com.louisfn.somovie.domain.model.ProfilePath
import com.louisfn.somovie.domain.model.TmdbConfiguration
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.invoke
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageUrlProvider @Inject constructor(
    configurationRepository: TmdbConfigurationRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    @ApplicationScope private val applicationScope: CoroutineScope,
) {

    private var currentConfig =
        configurationRepository
            .tmdbConfigurationChanges()
            .shareIn(
                scope = applicationScope,
                started = SharingStarted.Eagerly,
                replay = 1,
            )

    @AnyThread
    suspend fun get(path: ImagePath, requestedWidth: Int?): String? = defaultDispatcher {
        val configImages = currentConfig.firstOrNull()?.images ?: return@defaultDispatcher null

        "${configImages.secureBaseUrl}${
            getSizeSegment(
                path,
                requestedWidth,
                configImages,
            )
        }${path.value}"
    }

    @WorkerThread
    private fun getSizeSegment(
        path: ImagePath,
        requestedWidth: Int?,
        configImages: TmdbConfiguration.Images,
    ): String {
        requestedWidth ?: return ORIGINAL_SIZE

        val sizesAvailable = when (path) {
            is LogoPath -> configImages.logoSizes
            is BackdropPath -> configImages.backdropSizes
            is ProfilePath -> configImages.profileSizes
            is PosterPath -> configImages.posterSizes
            else -> throw IllegalArgumentException("${path::class} is not managed")
        }

        sizesAvailable
            .mapNotNull { widthAsString ->
                SIZE_REGEX.matchEntire(widthAsString)?.groupValues?.get(1)?.toInt()
                    ?.let { widthAsInt ->
                        widthAsString to widthAsInt
                    }
            }
            .sortedBy { it.second }
            .forEach { (widthAsString, widthAsInt) ->
                if (widthAsInt >= requestedWidth) {
                    return widthAsString
                }
            }

        return ORIGINAL_SIZE
    }

    companion object {
        private val SIZE_REGEX = Regex("""w(\d+)""")
        private const val ORIGINAL_SIZE = "original"
    }
}
