package com.louisfn.somovie.data.network.adapter

import com.louisfn.somovie.data.network.response.MovieVideoResponse
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

internal object MovieVideoSiteAdapter {

    @FromJson
    fun fromJson(json: String): MovieVideoResponse.Site =
        MovieVideoResponse.Site.values().firstOrNull { it.json == json } ?: MovieVideoResponse.Site.UNKNOWN

    @ToJson
    fun toJson(type: MovieVideoResponse.Type): String =
        type.json
}
