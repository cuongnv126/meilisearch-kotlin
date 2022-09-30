package com.meilisearch.sdk.http.factory

import com.meilisearch.sdk.http.request.HttpMethod
import com.meilisearch.sdk.http.request.HttpRequest

interface RequestFactory {
    fun <T> create(
        method: HttpMethod,
        path: String,
        headers: Map<String, String>?,
        content: T?
    ): HttpRequest<*>
}
