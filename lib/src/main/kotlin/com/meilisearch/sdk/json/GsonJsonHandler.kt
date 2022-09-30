@file:Suppress("UNCHECKED_CAST")

package com.meilisearch.sdk.json

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.meilisearch.sdk.exceptions.MeiliSearchRuntimeException
import com.meilisearch.sdk.shared.SharedObject

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

    override fun <T> decode(encoded: String?, targetClass: Class<*>, vararg parameters: Class<*>?): T {
        if (encoded == null) {
            throw RuntimeException("String to deserialize is null")
        }

        return if (targetClass == String::class.java) {
            encoded as T
        } else try {
            if (parameters.isEmpty()) {
                gson.fromJson(encoded as String?, targetClass) as T
            } else {
                val parameterized = TypeToken.getParameterized(targetClass, *parameters)
                gson.fromJson(encoded as String?, parameterized.type)
            }
        } catch (th: Throwable) {
            throw MeiliSearchRuntimeException(th)
        }
    }
}
