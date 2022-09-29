package com.meilisearch.sdk

import com.meilisearch.sdk.shared.SharedObject

class Result<T>(
    val results: Array<T>? = null
) {
    override fun toString(): String {
        return SharedObject.gson.toJson(this)
    }
}
