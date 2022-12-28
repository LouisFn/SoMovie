package plugin.util

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType

internal fun Project.getCommonExtension(): CommonExtension<*, *, *, *> =
    (
        extensions.findByType<LibraryExtension>()
            ?: extensions.findByType<ApplicationExtension>()
        ) as CommonExtension<*, *, *, *>
