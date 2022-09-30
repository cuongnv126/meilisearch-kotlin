package com.meilisearch.sdk.http.request

interface HttpRequest<T> {
    val method: HttpMethod
    val path: String
    val headers: Map<String, String>?
    val content: T

    fun hasContent(): Boolean
    val contentAsBytes: ByteArray
}
