package com.meilisearch.sdk

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

/**
 * Wrapper around MeilisearchHttpRequest class to use for MeiliSearch keys
 *
 *
 * Refer https://docs.meilisearch.com/reference/api/keys.html
 */
class KeysHandler(config: Config) {
    private val meiliSearchHttpRequest = MeiliSearchHttpRequest(config)
    private val gson = GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create()

    /**
     * Retrieves the Key with the specified uid
     *
     * @param uid Identifier of the requested Key
     * @return Key instance
     * @throws Exception if client request causes an error
     */
    fun getKey(uid: String): Key {
        val urlPath = "/keys/$uid"
        return gson.fromJson(meiliSearchHttpRequest.get(urlPath), Key::class.java)
    }

    /**
     * Retrieves Keys from the client
     *
     * @return List of key instance
     * @throws Exception if client request causes an error
     */
    fun getKeys(): Array<Key>? {
        val urlPath = "/keys"
        val result = gson.fromJson<Result<Key>>(
            meiliSearchHttpRequest.get(urlPath),
            object : TypeToken<Result<Key?>?>() {}.type
        )
        return result.results
    }

    /**
     * Creates a key
     *
     * @param options Key containing the options
     * @return Key Instance
     * @throws Exception if client request causes an error
     */
    fun createKey(options: Key): Key {
        val urlPath = "/keys"
        return gson.fromJson(
            meiliSearchHttpRequest.post(urlPath, options.toString()), Key::class.java
        )
    }

    /**
     * Deletes a key
     *
     * @param key String containing the key
     * @throws Exception if client request causes an error
     */
    fun deleteKey(key: String) {
        val urlPath = "/keys/$key"
        meiliSearchHttpRequest.delete(urlPath)
    }
}
