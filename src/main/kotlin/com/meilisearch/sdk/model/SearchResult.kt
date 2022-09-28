package com.meilisearch.sdk.model

import java.io.Serializable

/**
 * Result of `search` API Refer https://docs.meilisearch.com/references/search.html
 * */
class SearchResult(
    val hits: ArrayList<HashMap<String, Any>>? = null,
    val offset: Int = 0,
    val limit: Int = 0,
    val nbHits: Long = 0,
    val exhaustiveNbHits: Boolean = false,
    val facetsDistribution: Any? = null,
    val exhaustiveFacetsCount: Boolean = false,
    val processingTimeMs: Long = 0,
    val query: String? = null,
) : Serializable