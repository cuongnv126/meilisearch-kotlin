package com.meilisearch.sdk.model

import org.json.JSONObject

/**
 * Full SearchRequest Constructor for building search queries with 2D filter Array
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
 * @param filterArray String array that can take multiple nested filters
 * @param showMatchesPosition Defines whether an object that contains information about the matches should
 * be returned or not
 * @param facetsDistribution Facets for which to retrieve the matching count
 * @param sort Sort queries by an attribute value
 */
class SearchRequest(
    private var q: String? = null,
    private var offset: Int? = null,
    private var limit: Int? = null,
    private var attributesToRetrieve: Array<String>? = null,
    private var attributesToCrop: Array<String>? = null,
    private var cropLength: Int? = null,
    private var cropMarker: String? = null,
    private var highlightPreTag: String? = null,
    private var highlightPostTag: String? = null,
    private var attributesToHighlight: Array<String>? = null,
    private var filter: Array<String>? = null,
    private var filterArray: Array<Array<String>>? = null,
    private var showMatchesPosition: Boolean? = false,
    private var facetsDistribution: Array<String>? = null,
    private var sort: Array<String>? = null,
) {
    private inline fun self(builder: SearchRequest.() -> Unit): SearchRequest {
        builder(this)
        return this
    }

    fun q(q: String?) = self {
        this.q = q
    }

    /**
     * Method to set the offset
     *
     * @param offset Number of documents to skip
     * @return altered SearchRequest
     */
    fun offset(offset: Int) = self {
        this.offset = offset
    }

    /**
     * Method to set the limit
     *
     * @param limit Maximum number of documents returned
     * @return altered SearchRequest
     */
    fun limit(limit: Int) = self {
        this.limit = limit
    }

    /**
     * Method to set the attributesToRetrieve
     *
     * @param attributesToRetrieve Attributes to display in the returned documents
     * @return altered SearchRequest
     */
    fun attributesToRetrieve(attributesToRetrieve: Array<String>?) = self {
        this.attributesToRetrieve = attributesToRetrieve
    }

    /**
     * Method to set the attributesToCrop
     *
     * @param attributesToCrop Attributes whose values have been cropped
     * @return altered SearchRequest
     */
    fun attributesToCrop(attributesToCrop: Array<String>?) = self {
        this.attributesToCrop = attributesToCrop
    }

    /**
     * Method to set the cropLength
     *
     * @param cropLength Length used to crop field values
     * @return altered SearchRequest
     */
    fun cropLength(cropLength: Int) = self {
        this.cropLength = cropLength
    }

    /**
     * Method to set the cropMarker
     *
     * @param cropMarker Marker used to crop field values
     * @return altered SearchRequest
     */
    fun cropMarker(cropMarker: String?) = self {
        this.cropMarker = cropMarker
    }

    /**
     * Method to set the highlightPreTag
     *
     * @param highlightPreTag Highlight tag use before every highlighted query terms
     * @return altered SearchRequest
     */
    fun highlightPreTag(highlightPreTag: String?) = self {
        this.highlightPreTag = highlightPreTag
    }

    /**
     * Method to set the highlightPostTag
     *
     * @param highlightPostTag Highlight tag use after every highlighted query terms
     * @return altered SearchRequest
     */
    fun highlightPostTag(highlightPostTag: String?) = self {
        this.highlightPostTag = highlightPostTag
    }

    /**
     * Method to set the attributesToHighlight
     *
     * @param attributesToHighlight Attributes whose values will contain highlighted matching terms
     * @return altered SearchRequest
     */
    fun attributesToHighlight(attributesToHighlight: Array<String>?) = self {
        this.attributesToHighlight = attributesToHighlight
    }

    /**
     * Method to set the filter
     *
     * @param filter Filter queries by an attribute value
     * @return altered SearchRequest
     */
    fun filter(filter: Array<String>?) = self {
        this.filter = filter
    }

    fun filterArray(filterArray: Array<Array<String>>?) = self {
        this.filterArray = filterArray
    }

    /**
     * Method to set the matches boolean
     *
     * @param matches Defines whether an object that contains information about the matches should
     * be returned or not
     * @return altered SearchRequest
     */
    fun showMatchesPosition(matches: Boolean) = self {
        this.showMatchesPosition = matches
    }

    /**
     * Method to set the facetsDistribution
     *
     * @param facetsDistribution Facets for which to retrieve the matching count
     * @return altered SearchRequest
     */
    fun facetsDistribution(facetsDistribution: Array<String>?) = self {
        this.facetsDistribution = facetsDistribution
    }

    /**
     * Method to set the sort
     *
     * @param sort Sort queries by an attribute value
     * @return altered SearchRequest
     */
    fun sort(sort: Array<String>?) = self {
        this.sort = sort
    }

    /**
     * Method that returns the JSON String of the SearchRequest
     *
     * @return JSON String of the SearchRequest query
     */
    internal fun getQuery(): String {
        val jsonObject = JSONObject().apply {
            put("q", q)

            offset?.let { put("offset", it) }
            limit?.let { put("limit", it) }

            attributesToRetrieve?.let { put("attributesToRetrieve", it) }

            cropLength?.let { put("cropLength", it) }
            cropLength?.let { put("cropMarker", it) }
            showMatchesPosition?.let { put("showMatchesPosition", it) }

            facetsDistribution?.let { put("facetsDistribution", it) }
            sort?.let { put("sort", it) }

            highlightPreTag?.let { put("highlightPreTag", it) }
            highlightPostTag?.let { put("highlightPostTag", it) }

            attributesToCrop?.let { put("attributesToCrop", it) }
            attributesToHighlight?.let { put("attributesToHighlight", it) }

            filter?.let { put("filter", it) }
            filterArray?.let { put("filter", it) }
        }
        return jsonObject.toString()
    }
}
