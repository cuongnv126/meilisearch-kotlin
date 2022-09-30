package com.meilisearch.sdk.handler

import com.meilisearch.sdk.Config
import com.meilisearch.sdk.http.MeiliSearchHttpRequest
import com.meilisearch.sdk.model.Dump

internal class DumpHandler(config: Config) {
    private val request = MeiliSearchHttpRequest(config)
    private val jsonHandler = config.jsonHandlerFactory.newJsonHandler()

    /**
     * Creates a dump Refer https://docs.meilisearch.com/reference/api/dump.html#create-a-dump
     *
     * @return Dump object with Meilisearch API response
     * @throws Exception if an error occurs
     */
    fun createDump(): Dump {
        return jsonHandler.decode(request.post("/dumps", ""), Dump::class.java)
    }

    /**
     * Gets dump status Refer https://docs.meilisearch.com/reference/api/dump.html#get-dump-status
     *
     * @param uid Unique identifier for correspondent dump
     * @return Meilisearch API response with dump status and dump uid
     * @throws Exception if an error occurs
     */
    fun getDumpStatus(uid: String): String {
        return request.get("/dumps/$uid/status")
    }
}
