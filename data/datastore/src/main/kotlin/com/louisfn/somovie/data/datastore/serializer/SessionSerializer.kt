package com.louisfn.somovie.data.datastore.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.louisfn.somovie.core.common.extension.fromJson
import com.louisfn.somovie.core.common.extension.toJson
import com.louisfn.somovie.data.datastore.model.SessionData
import com.squareup.moshi.Moshi
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

internal class SessionSerializer @Inject constructor(
    private val moshi: Moshi,
) : Serializer<SessionData> {

    override val defaultValue: SessionData = SessionData()

    override suspend fun readFrom(input: InputStream): SessionData =
        try {
            requireNotNull(moshi.fromJson(input))
        } catch (exception: Exception) {
            throw CorruptionException("Cannot read json.", exception)
        }

    override suspend fun writeTo(
        t: SessionData,
        output: OutputStream,
    ) = moshi.toJson(output, t)
}
