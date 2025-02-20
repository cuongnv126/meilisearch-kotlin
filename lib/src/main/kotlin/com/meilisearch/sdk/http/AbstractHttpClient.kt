package com.meilisearch.sdk.http

import com.meilisearch.sdk.http.request.HttpRequest
import com.meilisearch.sdk.http.response.HttpResponse

abstract class AbstractHttpClient : HttpClient<HttpRequest<*>, HttpResponse<*>>
