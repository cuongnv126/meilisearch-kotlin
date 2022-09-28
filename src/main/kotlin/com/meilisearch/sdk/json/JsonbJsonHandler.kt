package com.meilisearch.sdk.json

import jakarta.json.bind.Jsonb
import jakarta.json.bind.JsonbBuilder
import jakarta.json.bind.JsonbConfig
import jakarta.json.bind.JsonbException

class JsonbJsonHandler : JsonHandler {
    private val mapper: Jsonb

    /**
     * this constructor uses a default ObjectMapper with enabled 'FAIL_ON_UNKNOWN_PROPERTIES'
     * feature.
     */
    constructor() {
        val config = JsonbConfig().withNullValues(false)
        mapper = JsonbBuilder.create(config)
    }

    /** @param mapper ObjectMapper
     */
    constructor(mapper: Jsonb) {
        this.mapper = mapper
    }

    /** {@inheritDoc}  */
    @Throws(Exception::class)
    override fun encode(o: Any): String? {
        return if (o.javaClass == String::class.java) {
            o as String?
        } else try {
            mapper.toJson(o)
        } catch (e: JsonbException) {
            // todo: use dedicated exception
            throw RuntimeException("Error while serializing: ", e)
        }
    }

    /** {@inheritDoc}  */
    @Throws(Exception::class)
    override fun <T> decode(o: Any?, targetClass: Class<*>, vararg parameters: Class<*>?): T {
        if (o == null) {
            // todo: use dedicated exception
            throw RuntimeException("String to deserialize is null")
        }
        return if (targetClass == String::class.java) {
            o as T
        } else mapper.fromJson(o as String?, targetClass) as T
    }
}
