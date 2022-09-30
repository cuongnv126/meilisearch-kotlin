package com.meilisearch.sdk.http

import com.meilisearch.sdk.Config
import com.meilisearch.sdk.coroutine.waitOn
import com.meilisearch.sdk.exceptions.MeiliSearchApiException
import com.meilisearch.sdk.http.factory.BasicRequestFactory
import com.meilisearch.sdk.http.request.HttpMethod
import com.meilisearch.sdk.json.decode

internal class MeiliSearchHttpRequest(config: Config) {
    private val jsonHandler = config.jsonHandlerFactory.newJsonHandler()
    private val client = config.httpClientFactory.newHttpClient()
    private val dispatcher = config.dispatcher
    private val requestFactory = BasicRequestFactory(config, jsonHandler)

    /**
     * Gets a document at the specified path with a given parameter
     *
     * @param api Path to document
     * @return document that was requested
     * @throws Exception if the client has an error
     * @throws MeiliSearchApiException if the response is an error
     */
    fun get(api: String) = waitOn(dispatcher) {
        val httpResponse = client.get(
            requestFactory.create(HttpMethod.GET, api, null, null)
        )
        if (httpResponse.statusCode >= 400) {
            throw MeiliSearchApiException(jsonHandler.decode(httpResponse.contentAsString))
        }
        return@waitOn httpResponse.contentAsString
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
    fun post(api: String, body: String?) = waitOn(dispatcher) {
        val httpResponse = client.post(requestFactory.create(HttpMethod.POST, api, null, body))
        if (httpResponse.statusCode >= 400) {
            throw MeiliSearchApiException(jsonHandler.decode(httpResponse.contentAsString))
        }
        return@waitOn httpResponse.contentAsString
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
    fun put(api: String, body: String?) = waitOn(dispatcher) {
        val httpResponse = client.put(requestFactory.create(HttpMethod.PUT, api, null, body))
        if (httpResponse.statusCode >= 400) {
            throw MeiliSearchApiException(jsonHandler.decode(httpResponse.contentAsString))
        }
        return@waitOn httpResponse.contentAsString
    }

    /**
     * Deletes the specified resource
     *
     * @param api Path to the requested resource
     * @return deleted resource
     * @throws Exception if the client has an error
     * @throws MeiliSearchApiException if the response is an error
     */
    fun delete(api: String) = waitOn(dispatcher) {
        val httpResponse = client.put(requestFactory.create(HttpMethod.DELETE, api, null, null))
        if (httpResponse.statusCode >= 400) {
            throw MeiliSearchApiException(jsonHandler.decode(httpResponse.contentAsString))
        }
        return@waitOn httpResponse.contentAsString
    }
}
