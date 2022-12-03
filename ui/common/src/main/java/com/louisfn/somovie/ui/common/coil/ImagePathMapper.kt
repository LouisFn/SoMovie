package com.louisfn.somovie.ui.common.coil

import coil.size.Dimension
import coil.size.Size
import com.louisfn.somovie.common.extension.takeAs
import com.louisfn.somovie.domain.model.ImagePath
import com.louisfn.somovie.domain.util.ImageUrlProvider
import javax.inject.Inject

class ImagePathMapper @Inject constructor(
    private val imageUrlProvider: ImageUrlProvider
) : Mapper<ImagePath, String> {

    override suspend fun map(data: ImagePath, size: Size): String? =
        size.width.takeAs<Dimension.Pixels>()?.let {
            imageUrlProvider.get(data, it.px)
        }
}
