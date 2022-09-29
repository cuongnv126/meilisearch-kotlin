package com.meilisearch.sdk.handler

import com.google.gson.JsonObject
import com.meilisearch.sdk.Config
import com.meilisearch.sdk.http.MeiliSearchHttpRequest

internal class IndexesHandler(config: Config) {
    private val request = MeiliSearchHttpRequest(config)

    /**
     * Creates an index with a unique identifier
     *
     * @param uid Unique identifier to create the index with
     * @param primaryKey Field to use as the primary key for documents in that index
     * @return Meilisearch API response
     * @throws Exception if an error occurs
     */
    /**
     * Creates an index with a unique identifier
     *
     * @param uid Unique identifier to create the index with
     * @return Meilisearch API response
     * @throws Exception if an error occurs
     */
    @JvmOverloads
    fun create(uid: String?, primaryKey: String? = null): String {
        val params = JsonObject()
        params.addProperty("uid", uid)
        if (primaryKey != null) {
            params.addProperty("primaryKey", primaryKey)
        }
        return request.post("/indexes", params.toString())
    }

    /**
     * Gets an index from its unique identifier
     *
     * @param uid Unique identifier of the index to get
     * @return Meilisearch API response
     * @throws Exception if an error occurs
     */
    fun get(uid: String): String {
        val requestQuery = "/indexes/$uid"
        return request.get(requestQuery)
    }

    /**
     * Gets all indexes in the current Meilisearch instance
     *
     * @return Meilisearch API response
     * @throws Exception if an error occurs
     */
    fun getAll() = request.get("/indexes")

    /**
     * Updates the primary key of an index in the Meilisearch instance
     *
     * @param uid Unique identifier of the index to update
     * @param primaryKey New primary key field to use for documents in that index
     * @return Meilisearch API response
     * @throws Exception if an error occurs
     */
    fun updatePrimaryKey(uid: String, primaryKey: String?): String {
        val jsonObject = JsonObject()
        jsonObject.addProperty("primaryKey", primaryKey)
        val requestQuery = "/indexes/$uid"
        return request.put(requestQuery, jsonObject.toString())
    }

    /**
     * Deletes an index in the Meilisearch instance
     *
     * @param uid Unique identifier of the index to delete
     * @return Meilisearch API response
     * @throws Exception if an error occurs
     */
    fun delete(uid: String): String {
        val requestQuery = "/indexes/$uid"
        return request.delete(requestQuery)
    }
}
