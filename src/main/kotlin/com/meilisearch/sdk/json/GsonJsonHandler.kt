package com.meilisearch.sdk.json

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.meilisearch.sdk.shared.SharedObject

class GsonJsonHandler(
    private val gson: Gson = SharedObject.gson
) : JsonHandler {

    override fun encode(o: Any?): String? {
        if (o == null) return null

        return if (o.javaClass == String::class.java) {
            o as String
        } else try {
            gson.toJson(o)
        } catch (e: Exception) {
            // todo: use dedicated exception
            throw RuntimeException("Error while serializing: ", e)
        }
    }

    override fun <T> decode(o: Any?, targetClass: Class<*>, vararg parameters: Class<*>?): T {
        if (o == null) {
            // todo: use dedicated exception
            throw RuntimeException("String to deserialize is null")
        }
        return if (targetClass == String::class.java) {
            o as T
        } else try {
            if (parameters.isEmpty()) {
                gson.fromJson(o as String?, targetClass) as T
            } else {
                val parameterized = TypeToken.getParameterized(targetClass, *parameters)
                gson.fromJson(o as String?, parameterized.type)
            }
        } catch (e: Exception) {
            // todo: use dedicated exception
            throw RuntimeException("Error while deserializing: ", e)
        }
    }
}
