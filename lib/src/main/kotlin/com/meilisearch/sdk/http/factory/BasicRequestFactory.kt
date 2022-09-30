package com.meilisearch.sdk.http.factory

import com.meilisearch.sdk.Config
import com.meilisearch.sdk.exceptions.MeiliSearchRuntimeException
import com.meilisearch.sdk.http.request.BasicHttpRequest
import com.meilisearch.sdk.http.request.HttpMethod
import com.meilisearch.sdk.http.request.HttpRequest
import com.meilisearch.sdk.json.JsonHandler

class BasicRequestFactory(
    private val config: Config,
    private val jsonHandler: JsonHandler
) : RequestFactory {

    override fun <T> create(
        method: HttpMethod,
        path: String,
        headers: Map<String, String>?,
        content: T?
    ): HttpRequest<*> {
        return try {
            val url = config.hostUrl + path

            val authenticHeaders = authHeader(headers)

            BasicHttpRequest(
                method,
                url,
                authenticHeaders,
                content?.let { jsonHandler.encode(it) }
            )
        } catch (e: Exception) {
            throw MeiliSearchRuntimeException(e)
        }
    }

    private fun authHeader(headers: Map<String, String>?): Map<String, String>? {
        if (config.masterKey.isNotEmpty()) {
            return (headers ?: emptyMap()) + mapOf(
                HEADER_AUTH to config.bearerMasterKey
            )
        }
        return headers
    }

    companion object {
        private const val HEADER_AUTH = "Authorization"
    }
}
