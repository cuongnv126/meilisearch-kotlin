package com.meilisearch.sdk

import org.json.JSONObject

/**
 * Data Structure for the Settings in an [Index]
 *
 *
 * Refer https://docs.meilisearch.com/reference/api/settings.html
 */
class Settings(
    private val synonyms: HashMap<String, Array<String>>? = null,
    val stopWords: Array<String>?,
    val rankingRules: Array<String>?,
    val filterableAttributes: Array<String>?,
    val distinctAttribute: String? = null,
    val searchableAttributes: Array<String>?,
    val displayedAttributes: Array<String>?,
    val sortableAttributes: Array<String>?,
    val typoTolerance: TypoTolerance? = null
) {

    /**
     * Method that returns the JSON String of the update settings query
     *
     * @return JSON String of the update settings query
     */
    fun getUpdateQuery(): String {
        val jsonObject = JSONObject()
        if (this.synonyms != null) {
            jsonObject.put("synonyms", this.synonyms)
        }
        if (this.stopWords != null) {
            jsonObject.put("stopWords", this.stopWords)
        }
        if (this.rankingRules != null) {
            jsonObject.put("rankingRules", this.rankingRules)
        }
        if (this.filterableAttributes != null) {
            jsonObject.put("filterableAttributes", this.filterableAttributes)
        }
        if (this.distinctAttribute != null) {
            jsonObject.put("distinctAttribute", this.distinctAttribute)
        }
        if (this.searchableAttributes != null) {
            jsonObject.put("searchableAttributes", this.searchableAttributes)
        }
        if (this.displayedAttributes != null) {
            jsonObject.put("displayedAttributes", this.displayedAttributes)
        }
        if (this.sortableAttributes != null) {
            jsonObject.put("sortableAttributes", this.sortableAttributes)
        }
        if (this.typoTolerance != null) {
            jsonObject.put("typoTolerance", this.typoTolerance.toJson())
        }
        return jsonObject.toString()
    }
}
