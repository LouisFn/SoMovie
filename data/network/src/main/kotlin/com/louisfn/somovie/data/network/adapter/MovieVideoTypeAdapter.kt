package com.louisfn.somovie.data.network.adapter

import com.louisfn.somovie.data.network.response.MovieVideoResponse
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

internal object MovieVideoTypeAdapter {

    @FromJson
    fun fromJson(json: String): MovieVideoResponse.Type =
        MovieVideoResponse.Type.values().first { it.json == json }

    @ToJson
    fun toJson(type: MovieVideoResponse.Type): String =
        type.json
}
