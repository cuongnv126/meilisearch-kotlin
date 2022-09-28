package com.meilisearch.sdk

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
 * @param matches Defines whether an object that contains information about the matches should
 * be returned or not
 * @param facetsDistribution Facets for which to retrieve the matching count
 * @param sort Sort queries by an attribute value
 */
class SearchRequest(
    private var q: String? = null,
    private var offset: Int = 0,
    private var limit: Int = 20,
    private var attributesToRetrieve: Array<String?>? = null,
    private var attributesToCrop: Array<String?>? = null,
    private var cropLength: Int = 200,
    private var cropMarker: String? = null,
    private var highlightPreTag: String? = null,
    private var highlightPostTag: String? = null,
    private var attributesToHighlight: Array<String?>? = null,
    private var filter: Array<String?>? = null,
    private var filterArray: Array<Array<String>>? = null,
    private var matches: Boolean = false,
    private var facetsDistribution: Array<String?>? = null,
    private var sort: Array<String?>? = null,
) {
    /**
     * Method to set the Query String
     *
     * @param q Query String
     * @return altered SearchRequest
     */
    fun q(q: String?): SearchRequest {
        this.q = q
        return this
    }

    /**
     * Method to set the offset
     *
     * @param offset Number of documents to skip
     * @return altered SearchRequest
     */
    fun setOffset(offset: Int): SearchRequest {
        this.offset = offset
        return this
    }

    /**
     * Method to set the limit
     *
     * @param limit Maximum number of documents returned
     * @return altered SearchRequest
     */
    fun setLimit(limit: Int): SearchRequest {
        this.limit = limit
        return this
    }

    /**
     * Method to set the attributesToRetrieve
     *
     * @param attributesToRetrieve Attributes to display in the returned documents
     * @return altered SearchRequest
     */
    fun setAttributesToRetrieve(attributesToRetrieve: Array<String?>?): SearchRequest {
        this.attributesToRetrieve = attributesToRetrieve
        return this
    }

    /**
     * Method to set the attributesToCrop
     *
     * @param attributesToCrop Attributes whose values have been cropped
     * @return altered SearchRequest
     */
    fun setAttributesToCrop(attributesToCrop: Array<String?>?): SearchRequest {
        this.attributesToCrop = attributesToCrop
        return this
    }

    /**
     * Method to set the cropLength
     *
     * @param cropLength Length used to crop field values
     * @return altered SearchRequest
     */
    fun setCropLength(cropLength: Int): SearchRequest {
        this.cropLength = cropLength
        return this
    }

    /**
     * Method to set the cropMarker
     *
     * @param cropMarker Marker used to crop field values
     * @return altered SearchRequest
     */
    fun setCropMarker(cropMarker: String?): SearchRequest {
        this.cropMarker = cropMarker
        return this
    }

    /**
     * Method to set the highlightPreTag
     *
     * @param highlightPreTag Highlight tag use before every highlighted query terms
     * @return altered SearchRequest
     */
    fun setHighlightPreTag(highlightPreTag: String?): SearchRequest {
        this.highlightPreTag = highlightPreTag
        return this
    }

    /**
     * Method to set the highlightPostTag
     *
     * @param highlightPostTag Highlight tag use after every highlighted query terms
     * @return altered SearchRequest
     */
    fun setHighlightPostTag(highlightPostTag: String?): SearchRequest {
        this.highlightPostTag = highlightPostTag
        return this
    }

    /**
     * Method to set the attributesToHighlight
     *
     * @param attributesToHighlight Attributes whose values will contain highlighted matching terms
     * @return altered SearchRequest
     */
    fun setAttributesToHighlight(attributesToHighlight: Array<String?>?): SearchRequest {
        this.attributesToHighlight = attributesToHighlight
        return this
    }

    /**
     * Method to set the filter
     *
     * @param filter Filter queries by an attribute value
     * @return altered SearchRequest
     */
    fun setFilter(filter: Array<String?>?): SearchRequest {
        if (filter != null) {
            this.filter = filter
        }
        return this
    }

    fun setFilterArray(filterArray: Array<Array<String>>?): SearchRequest {
        if (filterArray != null) {
            this.filterArray = filterArray
        }
        return this
    }

    /**
     * Method to set the matches boolean
     *
     * @param matches Defines whether an object that contains information about the matches should
     * be returned or not
     * @return altered SearchRequest
     */
    fun setMatches(matches: Boolean): SearchRequest {
        this.matches = matches
        return this
    }

    /**
     * Method to set the facetsDistribution
     *
     * @param facetsDistribution Facets for which to retrieve the matching count
     * @return altered SearchRequest
     */
    fun setFacetsDistribution(facetsDistribution: Array<String?>?): SearchRequest {
        this.facetsDistribution = facetsDistribution
        return this
    }

    /**
     * Method to set the sort
     *
     * @param sort Sort queries by an attribute value
     * @return altered SearchRequest
     */
    fun setSort(sort: Array<String?>?): SearchRequest {
        this.sort = sort
        return this
    }

    /**
     * Method that returns the JSON String of the SearchRequest
     *
     * @return JSON String of the SearchRequest query
     */
    fun getQuery(): String {
        val jsonObject = JSONObject()
            .put("q", q)
            .put("offset", offset)
            .put("limit", limit)
            .put("attributesToRetrieve", attributesToRetrieve)
            .put("cropLength", cropLength)
            .put("cropMarker", cropMarker)
            .put("highlightPreTag", highlightPreTag)
            .put("highlightPostTag", highlightPostTag)
            .put("showMatchesPosition", matches)
            .put("facetsDistribution", facetsDistribution)
            .put("sort", sort)
        if (attributesToCrop != null) {
            jsonObject.put("attributesToCrop", attributesToCrop)
        }
        if (attributesToHighlight != null) {
            jsonObject.put("attributesToHighlight", attributesToHighlight)
        }
        if (filter != null) {
            jsonObject.put("filter", filter)
        }
        if (filterArray != null) {
            jsonObject.put("filter", filterArray)
        }
        return jsonObject.toString()
    }
}
