@file:Suppress("UNCHECKED_CAST")

package com.meilisearch.sdk.json

import com.google.gson.Gson
import com.meilisearch.sdk.exceptions.MeiliSearchRuntimeException
import com.meilisearch.sdk.shared.SharedObject
import java.lang.reflect.Type

class GsonJsonHandler(
    private val gson: Gson = SharedObject.gson
) : JsonHandler {

    override fun encode(source: Any?): String? {
        if (source == null) return null

        return if (source.javaClass == String::class.java) {
            source as String
        } else try {
            gson.toJson(source)
        } catch (th: Throwable) {
            throw MeiliSearchRuntimeException(th)
        }
    }

    override fun <T> decode(encoded: String?, type: Type): T {
        if (encoded == null) {
            throw RuntimeException("String to deserialize is null")
        }

        if (type.typeName == String::class.java.typeName) {
            return encoded as T
        }

        return gson.fromJson(encoded, type)
    }
}
