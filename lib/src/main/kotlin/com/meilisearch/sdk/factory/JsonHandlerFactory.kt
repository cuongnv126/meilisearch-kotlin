package com.meilisearch.sdk.factory

import com.meilisearch.sdk.json.GsonJsonHandler
import com.meilisearch.sdk.json.JsonHandler

/**
 * Created by cuongnv on Sep 29, 2022
 */

interface JsonHandlerFactory {
    fun newJsonHandler(): JsonHandler

    object Default : JsonHandlerFactory {
        private val gsonJsonHandler = GsonJsonHandler()

        override fun newJsonHandler(): JsonHandler {
            return gsonJsonHandler
        }
    }
}
