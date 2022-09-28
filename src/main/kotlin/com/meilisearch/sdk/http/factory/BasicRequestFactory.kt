package com.meilisearch.sdk.http.factory

import com.meilisearch.sdk.exceptions.MeiliSearchRuntimeException
import com.meilisearch.sdk.http.request.BasicHttpRequest
import com.meilisearch.sdk.http.request.HttpMethod
import com.meilisearch.sdk.http.request.HttpRequest
import com.meilisearch.sdk.json.JsonHandler

class BasicRequestFactory(
    private val jsonHandler: JsonHandler
) : RequestFactory {

    override fun <T> create(
        method: HttpMethod,
        path: String,
        headers: Map<String?, String?>?,
        content: T?
    ): HttpRequest<*> {
        return try {
            BasicHttpRequest(
                method,
                path,
                headers,
                content?.let { jsonHandler.encode(it) }
            )
        } catch (e: Exception) {
            throw MeiliSearchRuntimeException(e)
        }
    }
}
