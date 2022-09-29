package com.meilisearch.sdk.http

import com.meilisearch.sdk.Config
import com.meilisearch.sdk.exceptions.ApiError
import com.meilisearch.sdk.exceptions.MeiliSearchApiException
import com.meilisearch.sdk.http.factory.BasicRequestFactory
import com.meilisearch.sdk.http.request.HttpMethod

internal class MeiliSearchHttpRequest(config: Config) {
    private val jsonHandler = config.jsonHandlerFactory.newJsonHandler()
    private val client = config.httpClientFactory.newHttpClient()
    private val requestFactory = BasicRequestFactory(config, jsonHandler)

    /**
     * Gets a document at the specified path
     *
     * @param api Path to document
     * @return document that was requested
     * @throws Exception if the client has an error
     * @throws MeiliSearchApiException if the response is an error
     */
    fun get(api: String): String {
        return this.get(api, "")
    }

    /**
     * Gets a document at the specified path with a given parameter
     *
     * @param api Path to document
     * @param param Parameter to be passed
     * @return document that was requested
     * @throws Exception if the client has an error
     * @throws MeiliSearchApiException if the response is an error
     */
    fun get(api: String, param: String): String {
        val httpResponse = client.get(
            requestFactory.create(HttpMethod.GET, api + param, null, null)
        )
        if (httpResponse.statusCode >= 400) {
            throw MeiliSearchApiException(
                jsonHandler.decode(httpResponse.content, ApiError::class.java)
            )
        }
        return String(httpResponse.contentAsBytes)
    }

    /**
     * Adds a document to the specified path
     *
     * @param api Path to server
     * @param body Query for search
     * @return results of the search
     * @throws Exception if the client has an error
     * @throws MeiliSearchApiException if the response is an error
     */
    fun post(api: String, body: String?): String {
        val httpResponse = client.post(requestFactory.create(HttpMethod.POST, api, null, body))
        if (httpResponse.statusCode >= 400) {
            throw MeiliSearchApiException(jsonHandler.decode(httpResponse.content, ApiError::class.java))
        }
        return String(httpResponse.contentAsBytes)
    }

    /**
     * Replaces the requested resource with new data
     *
     * @param api Path to the requested resource
     * @param body Replacement data for the requested resource
     * @return updated resource
     * @throws Exception if the client has an error
     * @throws MeiliSearchApiException if the response is an error
     */
    fun put(api: String, body: String?): String {
        val httpResponse = client.put(requestFactory.create(HttpMethod.PUT, api, null, body))
        if (httpResponse.statusCode >= 400) {
            throw MeiliSearchApiException(jsonHandler.decode(httpResponse.content, ApiError::class.java))
        }
        return String(httpResponse.contentAsBytes)
    }

    /**
     * Deletes the specified resource
     *
     * @param api Path to the requested resource
     * @return deleted resource
     * @throws Exception if the client has an error
     * @throws MeiliSearchApiException if the response is an error
     */
    fun delete(api: String): String {
        val httpResponse = client.put(requestFactory.create(HttpMethod.DELETE, api, null, null))
        if (httpResponse.statusCode >= 400) {
            throw MeiliSearchApiException(jsonHandler.decode(httpResponse.content, ApiError::class.java))
        }
        return String(httpResponse.contentAsBytes)
    }
}
