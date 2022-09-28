package com.meilisearch.sdk

import com.google.gson.Gson

class Result<T>(
    val results: Array<T>? = null
) {
    /**
     * Method to return the JSON String of the Result
     *
     * @return JSON string of the Result object
     */
    override fun toString(): String {
        return gsonResult.toJson(this)
    }

    companion object {
        private val gsonResult = Gson()
    }
}
