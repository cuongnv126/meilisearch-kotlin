package com.meilisearch.sdk.http

import com.meilisearch.sdk.http.request.HttpRequest
import com.meilisearch.sdk.http.response.HttpResponse

interface HttpClient<T : HttpRequest<*>, R : HttpResponse<*>> {
    suspend fun get(request: T): R
    suspend fun post(request: T): R
    suspend fun put(request: T): R
    suspend fun delete(request: T): R
    suspend fun patch(request: T): R
}
