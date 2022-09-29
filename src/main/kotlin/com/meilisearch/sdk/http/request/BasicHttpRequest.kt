package com.meilisearch.sdk.http.request

import java.nio.charset.StandardCharsets

class BasicHttpRequest(
    override val method: HttpMethod,
    override val path: String,
    override val headers: Map<String, String>?,
    override val content: String?
) : HttpRequest<String?> {

    override fun hasContent(): Boolean {
        return content != null
    }

    override val contentAsBytes: ByteArray
        get() = content!!.toByteArray(StandardCharsets.UTF_8)
}
