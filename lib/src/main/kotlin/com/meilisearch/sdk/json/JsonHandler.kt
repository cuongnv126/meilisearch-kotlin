package com.meilisearch.sdk.json

interface JsonHandler {
    /**
     * @param source the Object to serialize
     * @return the serialized Object `o`
     * @throws Exception wrapped exceptions of the used json library
     */
    fun encode(source: Any?): String?

    /**
     * @param encoded Object to deserialize, most of the time this is a string
     * @param targetClass return type
     * @param parameters in case the return type is a generic class, this is a list of types to use
     * with that generic.
     * @param <T> Abstract type to deserialize
     * @return the deserialized object
     * @throws Exception wrapped exceptions of the used json library
    </T> */
    fun <T> decode(encoded: String?, targetClass: Class<*>, vararg parameters: Class<*>?): T
}
