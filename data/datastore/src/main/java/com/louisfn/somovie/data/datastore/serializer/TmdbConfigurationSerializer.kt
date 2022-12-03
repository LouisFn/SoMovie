package com.louisfn.somovie.data.datastore.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.louisfn.somovie.common.extension.fromJson
import com.louisfn.somovie.common.extension.toJson
import com.louisfn.somovie.data.datastore.model.TmdbConfigurationData
import com.squareup.moshi.Moshi
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

internal class TmdbConfigurationSerializer @Inject constructor(
    private val moshi: Moshi
) : Serializer<TmdbConfigurationData> {

    override val defaultValue: TmdbConfigurationData = TmdbConfigurationData()

    override suspend fun readFrom(input: InputStream): TmdbConfigurationData =
        try {
            requireNotNull(moshi.fromJson(input))
        } catch (exception: Exception) {
            throw CorruptionException("Cannot read json.", exception)
        }

    override suspend fun writeTo(
        t: TmdbConfigurationData,
        output: OutputStream
    ) = moshi.toJson(output, t)
}
