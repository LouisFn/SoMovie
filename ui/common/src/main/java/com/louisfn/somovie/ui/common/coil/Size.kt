package com.louisfn.somovie.ui.common.coil

import android.util.Size
import coil.size.Dimension
import com.louisfn.somovie.common.extension.safeLet
import com.louisfn.somovie.common.extension.takeAs
import coil.size.Size as CoilSize

fun CoilSize.toSize() = safeLet(
    width.takeAs<Dimension.Pixels>(),
    height.takeAs<Dimension.Pixels>()
) { width, height ->
    Size(width.px, height.px)
}
