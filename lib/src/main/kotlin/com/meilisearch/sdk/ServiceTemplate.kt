package com.meilisearch.sdk

import com.meilisearch.sdk.exceptions.MeiliSearchRuntimeException
import com.meilisearch.sdk.http.AbstractHttpClient
import com.meilisearch.sdk.http.factory.RequestFactory
import com.meilisearch.sdk.http.request.HttpRequest
import com.meilisearch.sdk.json.JsonHandler

/**
 * A ServiceTemplate combines the HttpClient implementation with the JsonProcessor implementation
 * and specifies how both work together.
 */
interface ServiceTemplate {
    /** @return the wrapped HttpClient implementation
     */
    val client: AbstractHttpClient?

    /** @return the wrapped JsonProcessor implementation
     */
    val processor: JsonHandler

    /** @return the wrapped JsonProcessor implementation
     */
    val requestFactory: RequestFactory?

    /**
     * Executes the given request and deserializes the response
     *
     * @param request the HttpRequest to execute
     * @param targetClass the Type of Object to deserialize
     * @param parameter in case targetClass is a generic, parameter contains the specific types for
     * the generic
     * @param <T> type of targetClass or [com.meilisearch.sdk.http.response.HttpResponse] when
     * targetClass is null
     * @return the deserialized response of type targetClass or the [     ] if targetClass is null
     * @throws MeiliSearchRuntimeException as a wrapper of API or JSON exceptions
    </T> */
    fun <T> execute(request: HttpRequest<*>, targetClass: Class<*>?, vararg parameter: Class<*>): T?
}
