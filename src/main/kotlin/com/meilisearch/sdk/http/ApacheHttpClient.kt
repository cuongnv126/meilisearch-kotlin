package com.meilisearch.sdk.http

import com.meilisearch.sdk.http.request.HttpRequest
import com.meilisearch.sdk.http.response.BasicHttpResponse
import com.meilisearch.sdk.http.response.HttpResponse
import java.util.Arrays
import java.util.concurrent.CancellationException
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException
import java.util.stream.Collectors
import org.apache.hc.client5.http.async.HttpAsyncClient
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse
import org.apache.hc.client5.http.async.methods.SimpleRequestProducer
import org.apache.hc.client5.http.async.methods.SimpleResponseConsumer
import org.apache.hc.client5.http.impl.async.HttpAsyncClients
import org.apache.hc.client5.http.protocol.HttpClientContext
import org.apache.hc.core5.concurrent.FutureCallback
import org.apache.hc.core5.http.ContentType
import org.apache.hc.core5.http.Header
import org.apache.hc.core5.reactor.IOReactorConfig
import org.apache.hc.core5.util.Timeout

class ApacheHttpClient : AbstractHttpClient {
    private val client: HttpAsyncClient

    constructor() {
        val ioReactorConfig = IOReactorConfig.custom().setSoTimeout(Timeout.ofSeconds(5)).build()
        client = HttpAsyncClients.custom().setIOReactorConfig(ioReactorConfig).build()
    }

    constructor(client: HttpAsyncClient) {
        this.client = client
    }

    private fun execute(request: HttpRequest<*>): HttpResponse<*> {
        val response = CompletableFuture<SimpleHttpResponse>()
        client.execute(
            SimpleRequestProducer.create(mapRequest(request)),
            SimpleResponseConsumer.create(),
            null,
            HttpClientContext.create(),
            getCallback(response)
        )
        return try {
            response.thenApply { response: SimpleHttpResponse -> mapResponse(response) }.get()
        } catch (e: CancellationException) {
            // todo: throw dedicated exception
            throw Exception(e)
        } catch (e: ExecutionException) {
            throw Exception(e)
        }
    }

    override fun get(request: HttpRequest<*>): HttpResponse<*> {
        return execute(request)
    }

    override fun post(request: HttpRequest<*>): HttpResponse<*> {
        return execute(request)
    }

    override fun put(request: HttpRequest<*>): HttpResponse<*> {
        return execute(request)
    }

    override fun delete(request: HttpRequest<*>): HttpResponse<*> {
        return execute(request)
    }

    private fun mapRequest(request: HttpRequest<*>): SimpleHttpRequest {
        val httpRequest = SimpleHttpRequest(request.method.name, request.path)

        if (request.hasContent()) {
            httpRequest.setBody(request.contentAsBytes, ContentType.APPLICATION_JSON)
        }

        request.headers?.forEach { (key, value) ->
            httpRequest.addHeader(key, value)
        }
        return httpRequest
    }

    private fun mapResponse(response: SimpleHttpResponse): HttpResponse<*> {
        return BasicHttpResponse(
            Arrays.stream(response.headers)
                .collect(
                    Collectors.toConcurrentMap(
                        { obj: Header -> obj.name },
                        { obj: Header -> obj.value })
                ),
            response.code,
            response.bodyText
        )
    }

    private fun getCallback(
        completableFuture: CompletableFuture<SimpleHttpResponse>
    ): FutureCallback<SimpleHttpResponse> {
        return object : FutureCallback<SimpleHttpResponse> {
            override fun completed(result: SimpleHttpResponse) {
                completableFuture.complete(result)
            }

            override fun failed(ex: Exception) {
                completableFuture.completeExceptionally(ex)
            }

            override fun cancelled() {
                completableFuture.cancel(false)
            }
        }
    }
}
