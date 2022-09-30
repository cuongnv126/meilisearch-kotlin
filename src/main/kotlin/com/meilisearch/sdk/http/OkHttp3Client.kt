package com.meilisearch.sdk.http

import com.meilisearch.sdk.http.request.HttpMethod
import com.meilisearch.sdk.http.request.HttpRequest
import com.meilisearch.sdk.http.response.BasicHttpResponse
import com.meilisearch.sdk.http.response.HttpResponse
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

class OkHttp3Client(
    private val client: OkHttpClient = OkHttpClient()
) : AbstractHttpClient() {

    private fun getBodyFromRequest(request: HttpRequest<*>): RequestBody {
        return if (request.hasContent()) request.contentAsBytes.toRequestBody(JSON) else EMPTY_REQUEST_BODY
    }

    private fun buildRequest(request: HttpRequest<*>): Request {
        val builder = Request.Builder()
            .url(request.path)

        request.headers?.forEach { (key, value) ->
            builder.addHeader(key, value)
        }

        when (request.method) {
            HttpMethod.GET -> builder.get()
            HttpMethod.POST -> builder.post(getBodyFromRequest(request))
            HttpMethod.PUT -> builder.put(getBodyFromRequest(request))
            HttpMethod.DELETE -> if (request.hasContent()) builder.delete(getBodyFromRequest(request)) else builder.delete()
            HttpMethod.PATCH -> builder.patch(getBodyFromRequest(request))
            else -> throw IllegalStateException("Unexpected value: " + request.method)
        }

        return builder.build()
    }

    private fun buildResponse(response: Response): HttpResponse<*> {
        var body: String? = null
        val responseBody = response.body
        if (responseBody != null) body = responseBody.string()
        return BasicHttpResponse(
            parseHeaders(response.headers.toMultimap()), response.code, body
        )
    }

    private fun parseHeaders(headers: Map<String, List<String>>): Map<String, String> {
        val headerMap = HashMap<String, String>()
        for ((key, value) in headers) {
            headerMap[key] = java.lang.String.join("; ", value)
        }
        return headerMap
    }

    override fun get(request: HttpRequest<*>): HttpResponse<*> {
        val okRequest = buildRequest(request)
        val execute = client.newCall(okRequest).execute()
        return buildResponse(execute)
    }

    override fun post(request: HttpRequest<*>): HttpResponse<*> {
        val okRequest = buildRequest(request)
        val execute = client.newCall(okRequest).execute()
        return buildResponse(execute)
    }

    override fun put(request: HttpRequest<*>): HttpResponse<*> {
        val okRequest = buildRequest(request)
        val execute = client.newCall(okRequest).execute()
        return buildResponse(execute)
    }

    override fun delete(request: HttpRequest<*>): HttpResponse<*> {
        val okRequest = buildRequest(request)
        val execute = client.newCall(okRequest).execute()
        return buildResponse(execute)
    }

    override fun patch(request: HttpRequest<*>): HttpResponse<*> {
        val okRequest = buildRequest(request)
        val execute = client.newCall(okRequest).execute()
        return buildResponse(execute)
    }

    companion object {
        private val JSON: MediaType = "application/json; charset=utf-8".toMediaType()
        private val EMPTY_REQUEST_BODY: RequestBody = "".toByteArray().toRequestBody(JSON)
    }
}
