package com.meilisearch.sdk

import com.meilisearch.sdk.json.GsonJsonHandler
import com.meilisearch.sdk.model.SearchResult

class Search(config: Config) {
    private val meiliSearchHttpRequest = MeiliSearchHttpRequest(config)
    private val jsonGson = GsonJsonHandler()

    /**
     * Performs a search on a given index with a given query
     *
     * @param uid Index identifier
     * @param q Query to search on index
     * @return search results, as raw data
     * @throws Exception Search Exception or Client Error
     */
    fun rawSearch(uid: String?, q: String?): String {
        val requestQuery = "/indexes/$uid/search"
        val sr = SearchRequest().q(q)
        return meiliSearchHttpRequest.post(requestQuery, sr.getQuery())
    }

    /**
     * Performs a search on a given index with a given query
     *
     * @param q Query string
     * @param offset Number of documents to skip
     * @param limit Maximum number of documents returned
     * @param attributesToRetrieve Attributes to display in the returned documents
     * @param attributesToCrop Attributes whose values have been cropped
     * @param cropLength Length used to crop field values
     * @param cropMarker String to add before and/or after the cropped text, default value: …
     * @param highlightPreTag String to customize highlight tag before every highlighted query
     * terms, default value: *
     * @param highlightPostTag String to customize highlight tag after every highlighted query
     * terms, default value: *
     * @param attributesToHighlight Attributes whose values will contain highlighted matching terms
     * @param filter Filter queries by an attribute value
     * @param matches Defines whether an object that contains information about the matches should
     * be returned or not
     * @param facetsDistribution Facets for which to retrieve the matching count
     * @param sort Sort queries by an attribute value
     * @return search results, as raw data
     * @throws Exception Search Exception or Client Error
     */
    fun rawSearch(
        uid: String,
        q: String?,
        offset: Int,
        limit: Int,
        attributesToRetrieve: Array<String?>?,
        attributesToCrop: Array<String?>?,
        cropLength: Int,
        cropMarker: String?,
        highlightPreTag: String?,
        highlightPostTag: String?,
        attributesToHighlight: Array<String?>?,
        filter: Array<String?>?,
        matches: Boolean,
        facetsDistribution: Array<String?>?,
        sort: Array<String?>?
    ): String {
        val requestQuery = "/indexes/$uid/search"
        val sr = SearchRequest(
            q,
            offset,
            limit,
            attributesToRetrieve,
            attributesToCrop,
            cropLength,
            cropMarker,
            highlightPreTag,
            highlightPostTag,
            attributesToHighlight,
            filter,
            null,
            matches,
            facetsDistribution,
            sort
        )
        return meiliSearchHttpRequest.post(requestQuery, sr.getQuery())
    }

    /**
     * Performs a search on a given index with a given query
     *
     * @param uid Index identifier
     * @param sr SearchRequest to search on index
     * @return search results, as raw data
     * @throws Exception Search Exception or Client Error
     */
    fun rawSearch(uid: String?, sr: SearchRequest): String {
        val requestQuery = "/indexes/$uid/search"
        return meiliSearchHttpRequest.post(requestQuery, sr.getQuery())
    }

    /**
     * Performs a search on a given index with a given query
     *
     * @param uid Index identifier
     * @param q Query to search on index
     * @return search results
     * @throws Exception Search Exception or Client Error
     */
    fun search(uid: String?, q: String?): SearchResult? {
        return jsonGson.decode(rawSearch(uid, q), SearchResult::class.java)
    }

    /**
     * Performs a search on a given index with a given query
     *
     * @param q Query string
     * @param offset Number of documents to skip
     * @param limit Maximum number of documents returned
     * @param attributesToRetrieve Attributes to display in the returned documents
     * @param attributesToCrop Attributes whose values have been cropped
     * @param cropLength Length used to crop field values
     * @param cropMarker String to customize default crop marker, default value: …
     * @param highlightPreTag String to customize highlight tag before every highlighted query
     * terms, default value: *
     * @param highlightPostTag String to customize highlight tag after every highlighted query
     * terms, default value: *
     * @param attributesToHighlight Attributes whose values will contain highlighted matching terms
     * @param filter Filter queries by an attribute value
     * @param matches Defines whether an object that contains information about the matches should
     * be returned or not
     * @param facetsDistribution Facets for which to retrieve the matching count
     * @param sort Sort queries by an attribute value
     * @return search results
     * @throws Exception Search Exception or Client Error
     */
    fun search(
        uid: String,
        q: String?,
        offset: Int,
        limit: Int,
        attributesToRetrieve: Array<String?>?,
        attributesToCrop: Array<String?>?,
        cropLength: Int,
        cropMarker: String?,
        highlightPreTag: String?,
        highlightPostTag: String?,
        attributesToHighlight: Array<String?>?,
        filter: Array<String?>?,
        matches: Boolean,
        facetsDistribution: Array<String?>?,
        sort: Array<String?>?
    ): SearchResult {
        return jsonGson.decode(
            rawSearch(
                uid,
                q,
                offset,
                limit,
                attributesToRetrieve,
                attributesToCrop,
                cropLength,
                cropMarker,
                highlightPreTag,
                highlightPostTag,
                attributesToHighlight,
                filter,
                matches,
                facetsDistribution,
                sort
            ),
            SearchResult::class.java
        )
    }

    /**
     * Performs a search on a given index with a given query
     *
     * @param uid Index identifier
     * @param sr SearchRequest to search on index
     * @return search results
     * @throws Exception Search Exception or Client Error
     */
    fun search(uid: String?, sr: SearchRequest): SearchResult {
        return jsonGson.decode(rawSearch(uid, sr), SearchResult::class.java)
    }
}
