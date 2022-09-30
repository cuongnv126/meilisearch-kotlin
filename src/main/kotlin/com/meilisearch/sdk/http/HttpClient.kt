package com.meilisearch.sdk.http

import com.meilisearch.sdk.http.request.HttpRequest
import com.meilisearch.sdk.http.response.HttpResponse

interface HttpClient<T : HttpRequest<*>, R : HttpResponse<*>> {
    fun get(request: T): R
    fun post(request: T): R
    fun put(request: T): R
    fun delete(request: T): R
    fun patch(request: T): R
}
