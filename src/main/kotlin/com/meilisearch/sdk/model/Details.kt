package com.meilisearch.sdk.model

class Details(
    val receivedDocuments: Long = 0,
    val indexedDocuments: Long = 0,
    val deletedDocuments: Long = 0,
    val primaryKey: String? = null,
    val rankingRules: Array<String>? = null,
    val searchableAttributes: Array<String>? = null,
    val displayedAttributes: Array<String>? = null,
    val filterableAttributes: Array<String>? = null,
    val sortableAttributes: Array<String>? = null,
    val stopWords: Array<String>? = null,
    val synonyms: Map<String, Array<String>>? = null,
    val distinctAttribute: String? = null,
    val typoTolerance: TypoTolerance? = null
)
