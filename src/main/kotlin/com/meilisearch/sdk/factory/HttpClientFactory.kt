package com.meilisearch.sdk.factory

import com.meilisearch.sdk.http.AbstractHttpClient
import com.meilisearch.sdk.http.DefaultHttpClient

/**
 * Created by cuongnv on Sep 29, 2022
 */

interface HttpClientFactory {
    fun newHttpClient(): AbstractHttpClient

    object Default : HttpClientFactory {
        override fun newHttpClient(): AbstractHttpClient {
            return DefaultHttpClient()
        }
    }
}
