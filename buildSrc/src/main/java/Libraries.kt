@file:Suppress(
    "SpellCheckingInspection",
    "unused",
    "MemberVisibilityCanBePrivate",
    "StringLiteralDuplication",
    "MissingPackageDeclaration"
)

object Libraries {

    object Versions {
        // Android
        const val coreKtx = "1.9.0"
        const val compose = "1.3.1"
        const val composeCompiler = "1.3.2"
        const val composeActivity = "1.6.1"
        const val room = "2.4.3"
        const val hilt = "2.44.2"
        const val hiltAndroidX = "1.0.0"
        const val hiltCompose = "1.0.0"
        const val navigationCompose = "2.5.3"
        const val annotation = "1.5.0"
        const val paging = "3.1.1"
        const val pagingCompose = "1.0.0-alpha17"
        const val accompanist = "0.28.0"
        const val constraintLayout = "1.0.1"
        const val datastore = "1.0.0"
        const val lifecyle = "2.5.1"
        const val webkit = "1.5.0"

        // Test
        const val jUnit = "4.13.2"
        const val kotest = "5.5.4"
        const val androidTest = "1.5.0"
        const val androidJunit = "1.1.4"
        const val faker = "1.12.0"

        // Others
        const val coroutines = "1.6.4"
        const val retrofit = "2.9.0"
        const val moshi = "1.14.0"
        const val timber = "5.0.1"
        const val leakcanary = "2.10"
        const val coil = "2.2.2"
        const val okHttpLoggingInterceptor = "4.10.0"
        const val flipper = "0.176.0"
        const val soloader = "0.10.5"
    }

    object Compose {
        const val ui = "androidx.compose.ui:ui:${Versions.compose}"
        const val material = "androidx.compose.material:material:${Versions.compose}"
        const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
        const val icons = "androidx.compose.material:material-icons-extended:${Versions.compose}"
        const val test = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
        const val testManifest = "androidx.compose.ui:ui-test-manifest:${Versions.compose}"
    }

    object Accompanist {
        const val systemuicontroller =
            "com.google.accompanist:accompanist-systemuicontroller:${Versions.accompanist}"
        const val insets = "com.google.accompanist:accompanist-insets:${Versions.accompanist}"
        const val insetsui = "com.google.accompanist:accompanist-insets-ui:${Versions.accompanist}"
        const val pager = "com.google.accompanist:accompanist-pager:${Versions.accompanist}"
        const val pagerIndicators =
            "com.google.accompanist:accompanist-pager-indicators:${Versions.accompanist}"
        const val flowLayout =
            "com.google.accompanist:accompanist-flowlayout:${Versions.accompanist}"
        const val webView = "com.google.accompanist:accompanist-webview:${Versions.accompanist}"
        const val navigationAnimation =
            "com.google.accompanist:accompanist-navigation-animation:${Versions.accompanist}"
    }

    object Coroutines {
        const val android =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    }

    object Room {
        const val runtime = "androidx.room:room-runtime:${Versions.room}"
        const val compiler = "androidx.room:room-compiler:${Versions.room}"
        const val ktx = "androidx.room:room-ktx:${Versions.room}"
        const val paging = "androidx.room:room-paging:${Versions.room}"
        const val test = "androidx.room:room-testing:${Versions.room}"
    }

    object Hilt {
        const val android = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val core = "com.google.dagger:hilt-core:${Versions.hilt}"
        const val compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
        const val compose = "androidx.hilt:hilt-navigation-compose:${Versions.hiltCompose}"
        const val test = "com.google.dagger:hilt-android-testing:${Versions.hilt}"
    }

    object Retrofit {
        const val core = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    }

    object Coil {
        const val core = "io.coil-kt:coil:${Versions.coil}"
        const val compose = "io.coil-kt:coil-compose:${Versions.coil}"
    }

    object Flipper {
        const val core = "com.facebook.flipper:flipper:${Versions.flipper}"
        const val soloader = "com.facebook.soloader:soloader:${Versions.soloader}"
        const val network = "com.facebook.flipper:flipper-network-plugin:${Versions.flipper}"
        const val noop = "com.facebook.flipper:flipper-noop:${Versions.flipper}"
    }

    object Paging {
        const val common = "androidx.paging:paging-common:${Versions.paging}"
        const val runtime = "androidx.paging:paging-runtime:${Versions.paging}"
        const val compose = "androidx.paging:paging-compose:${Versions.pagingCompose}"
    }

    object Datastore {
        const val typed = "androidx.datastore:datastore:${Versions.datastore}"
        const val preferences = "androidx.datastore:datastore-preferences:${Versions.datastore}"
    }

    object Lifecycle {
        const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecyle}"
        const val process = "androidx.lifecycle:lifecycle-process:${Versions.lifecyle}"
        const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecyle}"
        const val viewmodelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecyle}"
    }

    object Moshi {
        const val kotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
        const val codegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    }

    const val androidCoreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val navigation = "androidx.navigation:navigation-compose:${Versions.navigationCompose}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout-compose:${Versions.constraintLayout}"
    const val activity = "androidx.activity:activity-compose:${Versions.composeActivity}"
    const val annotation = "androidx.annotation:annotation:${Versions.annotation}"
    const val webkit = "androidx.webkit:webkit:${Versions.webkit}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val leakcanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakcanary}"
    const val okHttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpLoggingInterceptor}"

    // Test

    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val faker = "io.github.serpro69:kotlin-faker:${Versions.faker}"

    object Kotest {
        const val core = "io.kotest:kotest-assertions-core:${Versions.kotest}"
    }

    object AndroidTest {
        const val core = "androidx.test:core-ktx:${Versions.androidTest}"
        const val runner = "androidx.test:runner:${Versions.androidTest}"
        const val junit = "androidx.test.ext:junit-ktx:${Versions.androidJunit}"
    }
}
