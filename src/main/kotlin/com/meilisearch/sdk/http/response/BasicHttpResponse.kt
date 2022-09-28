package com.meilisearch.sdk.http.response

open class BasicHttpResponse(
    override val headers: Map<String, String>,
    override val statusCode: Int,
    override val content: String?
) : HttpResponse<String?> {

    override fun hasContent(): Boolean {
        return content != null
    }

    override val contentAsBytes: ByteArray
        get() = content!!.toByteArray()
}
