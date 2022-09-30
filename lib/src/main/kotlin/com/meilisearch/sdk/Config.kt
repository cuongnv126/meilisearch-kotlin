package com.meilisearch.sdk

import com.meilisearch.sdk.extension.BuilderExtension.self
import com.meilisearch.sdk.factory.HttpClientFactory
import com.meilisearch.sdk.factory.JsonHandlerFactory
import java.util.concurrent.Executors
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher

/**
 * Creates a configuration with an API key
 *
 * @param hostUrl URL of the Meilisearch instance
 * @param masterKey API key to pass to the header of requests sent to Meilisearch
 */
class Config private constructor(
    internal val hostUrl: String,
    internal val masterKey: String,
    internal val dispatcher: CoroutineDispatcher,
    internal val jsonHandlerFactory: JsonHandlerFactory,
    internal val httpClientFactory: HttpClientFactory
) {
    internal val bearerMasterKey: String = "Bearer $masterKey"

    class Builder(
        private val hostUrl: String = "",
        private val masterKey: String = ""
    ) {
        private var dispatcher: CoroutineDispatcher? = null
        private var jsonHandlerFactory: JsonHandlerFactory? = null
        private var httpClientFactory: HttpClientFactory? = null

        fun dispatcher(dispatcher: CoroutineDispatcher) = self {
            this.dispatcher = dispatcher
        }

        fun jsonHandlerFactory(jsonHandlerFactory: JsonHandlerFactory) = self {
            this.jsonHandlerFactory = jsonHandlerFactory
        }

        fun httpClientFactory(httpClientFactory: HttpClientFactory) = self {
            this.httpClientFactory = httpClientFactory
        }

        fun build(): Config {
            return Config(
                hostUrl = hostUrl,
                masterKey = masterKey,
                dispatcher = dispatcher ?: Executors.newScheduledThreadPool(2).asCoroutineDispatcher(),
                jsonHandlerFactory = jsonHandlerFactory ?: JsonHandlerFactory.Default,
                httpClientFactory = httpClientFactory ?: HttpClientFactory.Default
            )
        }
    }
}
