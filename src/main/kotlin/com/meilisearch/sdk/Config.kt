package com.meilisearch.sdk

import com.meilisearch.sdk.factory.HttpClientFactory
import com.meilisearch.sdk.factory.JsonHandlerFactory

/**
 * Creates a configuration with an API key
 *
 * @param hostUrl URL of the Meilisearch instance
 * @param apiKey API key to pass to the header of requests sent to Meilisearch
 */
class Config @JvmOverloads constructor(
    val hostUrl: String,
    val apiKey: String = ""
) {
    val bearerApiKey: String = "Bearer $apiKey"

    internal var jsonHandlerFactory: JsonHandlerFactory = JsonHandlerFactory.Default
        private set

    internal var httpClientFactory: HttpClientFactory = HttpClientFactory.Default
        private set

    fun jsonHandlerFactory(jsonHandlerFactory: JsonHandlerFactory): Config {
        this.jsonHandlerFactory = jsonHandlerFactory
        return this
    }

    fun httpClientFactory(httpClientFactory: HttpClientFactory): Config {
        this.httpClientFactory = httpClientFactory
        return this
    }
}
