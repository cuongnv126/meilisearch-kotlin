package com.meilisearch.sdk.http

import com.meilisearch.sdk.http.request.HttpRequest
import com.meilisearch.sdk.http.response.BasicHttpResponse
import com.meilisearch.sdk.http.response.HttpResponse
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Collectors

class DefaultHttpClient : AbstractHttpClient() {
    /**
     * Create and get a validated HTTP connection to url with method and API key
     *
     * @param url URL to connect to
     * @param method HTTP method to use for the connection
     * @param apiKey API Key to use for the connection
     * @return Validated connection (otherwise, will throw a [IOException])
     * @throws IOException if unable to establish connection
     */
    private fun getConnection(url: URL?, method: String, headers: Map<String, String>?): HttpURLConnection {
        if (url == null || "" == method) throw IOException("Unable to open an HttpURLConnection with no URL or method")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = method
        connection.setRequestProperty("Content-Type", "application/json")

        // Use API key header only if one is provided
        headers?.forEach { (key, value) ->
            connection.setRequestProperty(key, value)
        }

        return connection
    }

    private fun execute(request: HttpRequest<*>): HttpResponse<*> {
        val url = URL(request.path)
        val connection = getConnection(url, request.method.name, request.headers)
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
}
