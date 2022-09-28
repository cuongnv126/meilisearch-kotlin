package com.meilisearch.sdk

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

    /**
     * @return Bearer API key String
     */
    val bearerApiKey: String = "Bearer $apiKey"
}
