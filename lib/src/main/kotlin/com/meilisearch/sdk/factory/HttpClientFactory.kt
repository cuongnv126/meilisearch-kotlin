package com.meilisearch.sdk.factory

import com.meilisearch.sdk.http.AbstractHttpClient
import com.meilisearch.sdk.http.OkHttp3Client

/**
 * Created by cuongnv on Sep 29, 2022
 */

interface HttpClientFactory {
    fun newHttpClient(): AbstractHttpClient

    object Default : HttpClientFactory {
        private val httpClient = OkHttp3Client()

        override fun newHttpClient(): AbstractHttpClient {
            return httpClient
        }
    }
}
