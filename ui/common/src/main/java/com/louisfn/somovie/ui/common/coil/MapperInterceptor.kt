package com.louisfn.somovie.ui.common.coil

import coil.intercept.Interceptor
import coil.request.ImageResult
import coil.size.Size
import kotlin.reflect.KClass
import kotlin.reflect.full.isSuperclassOf

/**
 * We should use a custom interceptor to map com.louisfn.somovie.common.getData in a background thread.
 * We can't use [coil.map.Mapper.map] because it's call on main thread
 */
class MapperInterceptor(private val mappers: List<Pair<Mapper<out Any, out Any>, KClass<out Any>>>) : Interceptor {

    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        val newRequest =
            mapData(chain.request.data, chain.size)
                ?.let { chain.request.newBuilder().data(it).build() }
                ?: chain.request

        return chain.proceed(newRequest)
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun mapData(data: Any, size: Size): Any? {
        mappers.forEach { (mapper, type) ->
            if (type.isSuperclassOf(data::class)) {
                return (mapper as Mapper<Any, *>).map(data, size)
            }
        }
        return null
    }
}

fun interface Mapper<T : Any, V : Any> {

    suspend fun map(data: T, size: Size): V?
}
