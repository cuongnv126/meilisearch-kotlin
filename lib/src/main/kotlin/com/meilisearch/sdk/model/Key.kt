package com.meilisearch.sdk.model

import com.google.gson.GsonBuilder
import java.util.Date

class Key(
    val description: String? = null,
    val key: String = "",
    val actions: Array<String>? = null,
    val indexes: Array<String>? = null,
    val expiresAt: Date? = null,
    val createdAt: Date? = null,
    val updatedAt: Date? = null,
) {

    /**
     * Method to return the JSON String of the Key
     *
     * @return JSON string of the Key object
     */
    override fun toString(): String {
        val builder = GsonBuilder()
        val gson = builder.serializeNulls().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create()
        return gson.toJson(this)
    }
}
