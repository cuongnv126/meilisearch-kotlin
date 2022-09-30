package com.meilisearch.sdk.http.response

interface HttpResponse<B> {
    val headers: Map<String, String>?
    val statusCode: Int
    val content: B

    val contentAsString: String
    val contentAsBytes: ByteArray
}
