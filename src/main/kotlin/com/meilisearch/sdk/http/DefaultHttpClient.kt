package com.meilisearch.sdk.http

import com.meilisearch.sdk.Config
import com.meilisearch.sdk.http.request.HttpRequest
import com.meilisearch.sdk.http.response.BasicHttpResponse
import com.meilisearch.sdk.http.response.HttpResponse
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Collectors

class DefaultHttpClient(config: Config) : AbstractHttpClient(config) {
    /**
     * Create and get a validated HTTP connection to url with method and API key
     *
     * @param url URL to connect to
     * @param method HTTP method to use for the connection
     * @param apiKey API Key to use for the connection
     * @return Validated connection (otherwise, will throw a [IOException])
     * @throws IOException if unable to establish connection
     */
    @Throws(IOException::class)
    private fun getConnection(url: URL?, method: String, apiKey: String): HttpURLConnection {
        if (url == null || "" == method) throw IOException("Unable to open an HttpURLConnection with no URL or method")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = method
        connection.setRequestProperty("Content-Type", "application/json")

        // Use API key header only if one is provided
        if ("" != apiKey) {
            connection.setRequestProperty("Authorization", config.bearerApiKey)
        }
        return connection
    }

    @Throws(IOException::class)
    private fun execute(request: HttpRequest<*>): HttpResponse<*> {
        val url = URL(config.hostUrl + request.path)
        val connection = getConnection(url, request.method.name, config.apiKey)
        if (request.hasContent()) {
            connection.doOutput = true
            connection.outputStream.write(request.contentAsBytes)
        }
        return if (connection.responseCode >= 400) {
            BasicHttpResponse(
                emptyMap(),
                connection.responseCode,
                BufferedReader(InputStreamReader(connection.errorStream))
                    .lines()
                    .collect(Collectors.joining("\n"))
            )
        } else BasicHttpResponse(
            emptyMap(),
            connection.responseCode,
            BufferedReader(InputStreamReader(connection.inputStream))
                .lines()
                .collect(Collectors.joining("\n"))
        )
    }

    @Throws(Exception::class)
    override fun get(request: HttpRequest<*>): HttpResponse<*> {
        return execute(request)
    }

    @Throws(Exception::class)
    override fun post(request: HttpRequest<*>): HttpResponse<*> {
        return execute(request)
    }

    @Throws(Exception::class)
    override fun put(request: HttpRequest<*>): HttpResponse<*> {
        return execute(request)
    }

    @Throws(Exception::class)
    override fun delete(request: HttpRequest<*>): HttpResponse<*> {
        return execute(request)
    }
}
