package com.meilisearch.sdk.http.response

import java.nio.charset.StandardCharsets

class BasicHttpResponse(
    override val headers: Map<String, String>,
    override val statusCode: Int,
    override val content: String?
) : HttpResponse<String?> {

    override val contentAsString: String = content.orEmpty()
    override val contentAsBytes: ByteArray get() = content!!.toByteArray(StandardCharsets.UTF_8)
}
