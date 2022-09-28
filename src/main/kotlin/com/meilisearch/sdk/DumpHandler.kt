package com.meilisearch.sdk

import com.google.gson.Gson

class DumpHandler(config: Config) {
    private val meiliSearchHttpRequest = MeiliSearchHttpRequest(config)

    /**
     * Creates a dump Refer https://docs.meilisearch.com/reference/api/dump.html#create-a-dump
     *
     * @return Dump object with Meilisearch API response
     * @throws Exception if an error occurs
     */
    fun createDump(): Dump {
        return Gson().fromJson(meiliSearchHttpRequest.post("/dumps", ""), Dump::class.java)
    }

    /**
     * Gets dump status Refer https://docs.meilisearch.com/reference/api/dump.html#get-dump-status
     *
     * @param uid Unique identifier for correspondent dump
     * @return Meilisearch API response with dump status and dump uid
     * @throws Exception if an error occurs
     */
    fun getDumpStatus(uid: String): String {
        return meiliSearchHttpRequest.get("/dumps/$uid/status")
    }
}
