package com.louisfn.somovie.app.initializer

import android.app.Application
import androidx.annotation.AnyThread
import coil.Coil
import coil.ImageLoader
import com.louisfn.somovie.domain.model.ImagePath
import com.louisfn.somovie.ui.common.coil.ImagePathMapper
import com.louisfn.somovie.ui.common.coil.MapperInterceptor
import javax.inject.Inject

internal class CoilInitializer @Inject constructor(
    private val imagePathMapper: ImagePathMapper
) : AppInitializer {

    override fun onCreate(application: Application) {
        Coil.setImageLoader { createImageLoader(application) }
    }

    @AnyThread
    private fun createImageLoader(application: Application) =
        ImageLoader.Builder(application)
            .components {
                add(
                    MapperInterceptor(
                        listOf(
                            imagePathMapper to ImagePath::class
                        )
                    )
                )
            }
            .build()
}
