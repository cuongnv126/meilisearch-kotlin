package com.meilisearch.sdk.json

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

interface JsonHandler {
    /**
     * @param source the Object to serialize
     * @return the serialized Object `o`
     * @throws Exception wrapped exceptions of the used json library
     */
    fun encode(source: Any?): String?

    /**
     * @param encoded Object to deserialize, most of the time this is a string
     * @param type return type
     * @param <T> Abstract type to deserialize
     * @return the deserialized object
     * @throws Exception wrapped exceptions of the used json library
    </T> */
    fun <T> decode(encoded: String?, type: Type): T
}

inline fun <reified T> JsonHandler.decode(encoded: String?): T {
    return decode(encoded, object : TypeToken<T>() {}.type)
}
