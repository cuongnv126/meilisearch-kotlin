package com.meilisearch.sdk

import com.meilisearch.sdk.exceptions.ApiError
import com.meilisearch.sdk.exceptions.MeiliSearchApiException
import com.meilisearch.sdk.http.AbstractHttpClient
import com.meilisearch.sdk.http.DefaultHttpClient
import com.meilisearch.sdk.http.factory.BasicRequestFactory
import com.meilisearch.sdk.http.factory.RequestFactory
import com.meilisearch.sdk.http.request.HttpMethod
import com.meilisearch.sdk.json.GsonJsonHandler

class MeiliSearchHttpRequest {
    private val client: AbstractHttpClient
    private val factory: RequestFactory
    private val jsonHandler: GsonJsonHandler

    /**
     * Constructor for the MeiliSearchHttpRequest
     *
     * @param config Meilisearch configuration
     */
    constructor(config: Config) {
        client = DefaultHttpClient(config)
        jsonHandler = GsonJsonHandler()
        factory = BasicRequestFactory(jsonHandler)
    }

    /**
     * Constructor for the MeiliSearchHttpRequest
     *
     * @param client HttpClient for making calls to server
     * @param factory RequestFactory for generating calls to server
     */
    constructor(client: AbstractHttpClient, factory: RequestFactory) {
        this.client = client
        this.factory = factory
        jsonHandler = GsonJsonHandler()
    }

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
            factory.create(
                HttpMethod.GET,
                api + param,
                emptyMap(),
                null
            )
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
        val httpResponse = client.post(
            factory.create(HttpMethod.POST, api, emptyMap(), body)
        )
        if (httpResponse.statusCode >= 400) {
            throw MeiliSearchApiException(
                jsonHandler.decode(httpResponse.content, ApiError::class.java)
            )
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
        val httpResponse = client.put(factory.create(HttpMethod.PUT, api, emptyMap(), body))
        if (httpResponse.statusCode >= 400) {
            throw MeiliSearchApiException(
                jsonHandler.decode(httpResponse.content, ApiError::class.java)
            )
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
        val httpResponse = client.put(
            factory.create<Any?>(HttpMethod.DELETE, api, emptyMap(), null)
        )
        if (httpResponse.statusCode >= 400) {
            throw MeiliSearchApiException(
                jsonHandler.decode(httpResponse.content, ApiError::class.java)
            )
        }
        return String(httpResponse.contentAsBytes)
    }
}
