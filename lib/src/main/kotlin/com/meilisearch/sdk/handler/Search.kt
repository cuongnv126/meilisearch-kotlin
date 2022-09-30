package com.meilisearch.sdk.handler

import com.meilisearch.sdk.Config
import com.meilisearch.sdk.http.MeiliSearchHttpRequest
import com.meilisearch.sdk.json.decode
import com.meilisearch.sdk.model.SearchRequest
import com.meilisearch.sdk.model.SearchResult

internal class Search(config: Config) {
    private val request = MeiliSearchHttpRequest(config)
    private val jsonHandler = config.jsonHandlerFactory.newJsonHandler()

    /**
     * Performs a search on a given index with a given query
     *
     * @param uid Index identifier
     * @param query Query to search on index
     * @return search results, as raw data
     * @throws Exception Search Exception or Client Error
     */
    fun rawSearch(uid: String, query: String): String {
        val requestQuery = "/indexes/$uid/search"
        val searchRequest = SearchRequest(query)
        return request.post(requestQuery, searchRequest.getQuery())
    }

    /**
     * Performs a search on a given index with a given query
     *
     * @param uid Index identifier
     * @param searchRequest SearchRequest to search on index
     * @return search results, as raw data
     * @throws Exception Search Exception or Client Error
     */
    fun rawSearch(uid: String, searchRequest: SearchRequest): String {
        val requestQuery = "/indexes/$uid/search"
        return request.post(requestQuery, searchRequest.getQuery())
    }

    /**
     * Performs a search on a given index with a given query
     *
     * @param uid Index identifier
     * @param query Query to search on index
     * @return search results
     * @throws Exception Search Exception or Client Error
     */
    fun search(uid: String, query: String): SearchResult {
        return jsonHandler.decode(rawSearch(uid, query))
    }

    /**
     * Performs a search on a given index with a given query
     *
     * @param uid Index identifier
     * @param searchRequest SearchRequest to search on index
     * @return search results
     * @throws Exception Search Exception or Client Error
     */
    fun search(uid: String, searchRequest: SearchRequest): SearchResult {
        return jsonHandler.decode(rawSearch(uid, searchRequest))
    }
}
