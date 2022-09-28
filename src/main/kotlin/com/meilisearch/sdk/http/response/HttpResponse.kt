package com.meilisearch.sdk.http.response

interface HttpResponse<B> {
    val headers: Map<String, String>?
    val statusCode: Int
    fun hasContent(): Boolean
    val content: B
    val contentAsBytes: ByteArray
}
