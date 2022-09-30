package com.meilisearch.sdk.factory

import com.meilisearch.sdk.http.AbstractHttpClient
import com.meilisearch.sdk.http.DefaultHttpClient

/**
 * Created by cuongnv on Sep 29, 2022
 */

interface HttpClientFactory {
    fun newHttpClient(): AbstractHttpClient

    object Default : HttpClientFactory {
        private val httpClient = DefaultHttpClient()

        override fun newHttpClient(): AbstractHttpClient {
            return httpClient
        }
    }
}
