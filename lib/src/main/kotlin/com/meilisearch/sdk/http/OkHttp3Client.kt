package com.meilisearch.sdk.http

import com.meilisearch.sdk.coroutine.await
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

    private fun HttpRequest<*>.toRequestBody(): RequestBody {
        return if (this.hasContent()) this.contentAsBytes.toRequestBody(JSON) else EMPTY_REQUEST_BODY
    }

    private fun HttpRequest<*>.toRequest(): Request {
        val builder = Request.Builder()
            .url(this.path)

        this.headers?.forEach { (key, value) ->
            builder.addHeader(key, value)
        }

        when (this.method) {
            HttpMethod.GET -> builder.get()
            HttpMethod.POST -> builder.post(toRequestBody())
            HttpMethod.PUT -> builder.put(toRequestBody())
            HttpMethod.DELETE -> if (hasContent()) builder.delete(toRequestBody()) else builder.delete()
            HttpMethod.PATCH -> builder.patch(toRequestBody())
            else -> throw IllegalStateException("Unexpected value: $method")
        }

        return builder.build()
    }

    private fun parseResponse(response: Response): HttpResponse<*> {
        return BasicHttpResponse(
            parseHeaders(response.headers.toMultimap()),
            response.code,
            response.body?.string()
        )
    }

    private fun parseHeaders(headers: Map<String, List<String>>): Map<String, String> {
        val headerMap = HashMap<String, String>()
        for ((key, value) in headers) {
            headerMap[key] = value.joinToString("; ")
        }
        return headerMap
    }

    override suspend fun get(request: HttpRequest<*>): HttpResponse<*> {
        val execute = client.newCall(request.toRequest()).await()
        return parseResponse(execute)
    }

    override suspend fun post(request: HttpRequest<*>): HttpResponse<*> {
        val execute = client.newCall(request.toRequest()).await()
        return parseResponse(execute)
    }

    override suspend fun put(request: HttpRequest<*>): HttpResponse<*> {
        val execute = client.newCall(request.toRequest()).await()
        return parseResponse(execute)
    }

    override suspend fun delete(request: HttpRequest<*>): HttpResponse<*> {
        val execute = client.newCall(request.toRequest()).await()
        return parseResponse(execute)
    }

    override suspend fun patch(request: HttpRequest<*>): HttpResponse<*> {
        val execute = client.newCall(request.toRequest()).await()
        return parseResponse(execute)
    }

    companion object {
        private val JSON: MediaType = "application/json; charset=utf-8".toMediaType()
        private val EMPTY_REQUEST_BODY: RequestBody = "".toByteArray().toRequestBody(JSON)
    }
}
