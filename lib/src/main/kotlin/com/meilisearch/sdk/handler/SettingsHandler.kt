package com.meilisearch.sdk.handler

import com.meilisearch.sdk.Config
import com.meilisearch.sdk.model.Task
import com.meilisearch.sdk.model.TypoTolerance
import com.meilisearch.sdk.http.MeiliSearchHttpRequest
import com.meilisearch.sdk.json.decode
import com.meilisearch.sdk.model.Settings

/**
 * Settings Handler for manipulation of an Index [Settings]
 *
 *
 * Refer https://docs.meilisearch.com/reference/api/settings.html
 */
internal class SettingsHandler(config: Config) {
    private val request = MeiliSearchHttpRequest(config)
    private val jsonHandler = config.jsonHandlerFactory.newJsonHandler()

    /**
     * Gets the settings of a given index Refer
     * https://docs.meilisearch.com/reference/api/settings.html#get-settings
     *
     * @param uid Index identifier
     * @return settings of a given uid as String
     * @throws Exception if an error occurs
     */
    fun getSettings(uid: String): Settings {
        return jsonHandler.decode(request.get("/indexes/$uid/settings"))
    }

    /**
     * Updates the settings of a given index Refer
     * https://docs.meilisearch.com/reference/api/settings.html#update-settings
     *
     * @param uid Index identifier
     * @param settings the data that contains the new settings
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun updateSettings(uid: String, settings: Settings): Task {
        return jsonHandler.decode(request.post("/indexes/$uid/settings", settings.getUpdateQuery()))
    }

    /**
     * Resets the settings of a given index Refer
     * https://docs.meilisearch.com/reference/api/settings.html#reset-settings
     *
     * @param uid Index identifier
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun resetSettings(uid: String): Task {
        return jsonHandler.decode(request.delete("/indexes/$uid/settings"))
    }

    /**
     * Gets the ranking rules settings of a given index Refer
     * https://docs.meilisearch.com/reference/api/ranking_rules.html#get-ranking-rules
     *
     * @param uid Index identifier
     * @return an array of strings that contains the ranking rules settings
     * @throws Exception if an error occurs
     */
    fun getRankingRuleSettings(uid: String): Array<String> {
        return jsonHandler.decode(request.get("/indexes/$uid/settings/ranking-rules"))
    }

    /**
     * Updates the ranking rules settings of a given index Refer
     * https://docs.meilisearch.com/reference/api/ranking_rules.html#update-ranking-rules
     *
     * @param uid Index identifier
     * @param rankingRules the data that contains the new settings
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun updateRankingRuleSettings(uid: String, rankingRules: Array<String>): Task {
        val rankingRulesAsJson = jsonHandler.encode(rankingRules)
        return jsonHandler.decode(request.post("/indexes/$uid/settings/ranking-rules", rankingRulesAsJson))
    }

    /**
     * Resets the ranking rules settings of a given index Refer
     * https://docs.meilisearch.com/reference/api/ranking_rules.html#reset-ranking-rules
     *
     * @param uid Index identifier
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun resetRankingRulesSettings(uid: String): Task {
        return jsonHandler.decode(request.delete("/indexes/$uid/settings/ranking-rules"))
    }

    /**
     * Gets the synonyms settings of a given index Refer
     * https://docs.meilisearch.com/reference/api/synonyms.html#get-synonyms
     *
     * @param uid Index identifier
     * @return a Map that contains all synonyms and their associated words
     * @throws Exception if an error occurs
     */
    fun getSynonymsSettings(uid: String): Map<String, Array<String>> {
        return jsonHandler.decode(request.get("/indexes/$uid/settings/synonyms"))
    }

    /**
     * Updates the synonyms settings of a given index Refer
     * https://docs.meilisearch.com/reference/api/synonyms.html#update-synonyms
     *
     * @param uid Index identifier
     * @param synonyms a Map that contains the new synonyms settings
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun updateSynonymsSettings(uid: String, synonyms: Map<String?, Array<String>?>): Task {
        val synonymsAsJson = jsonHandler.encode(synonyms)
        return jsonHandler.decode(request.post("/indexes/$uid/settings/synonyms", synonymsAsJson))
    }

    /**
     * Resets the synonyms settings of a given index Refer
     * https://docs.meilisearch.com/reference/api/synonyms.html#reset-synonyms
     *
     * @param uid Index identifier
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun resetSynonymsSettings(uid: String): Task {
        return jsonHandler.decode(request.delete("/indexes/$uid/settings/synonyms"))
    }

    /**
     * Gets the stop-words settings of the index Refer
     * https://docs.meilisearch.com/reference/api/stop_words.html#get-stop-words
     *
     * @param uid Index identifier
     * @return an array of strings that contains the stop-words
     * @throws Exception if an error occurs
     */
    fun getStopWordsSettings(uid: String): Array<String> {
        return jsonHandler.decode(request.get("/indexes/$uid/settings/stop-words"))
    }

    /**
     * Updates the stop-words settings of the index Refer
     * https://docs.meilisearch.com/reference/api/stop_words.html#update-stop-words
     *
     * @param uid Index identifier
     * @param stopWords an array of strings that contains the new stop-words settings
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun updateStopWordsSettings(uid: String, stopWords: Array<String>): Task {
        val stopWordsAsJson = jsonHandler.encode(stopWords)
        return jsonHandler.decode(request.post("/indexes/$uid/settings/stop-words", stopWordsAsJson))
    }

    /**
     * Resets the stop-words settings of the index Refer
     * https://docs.meilisearch.com/reference/api/stop_words.html#reset-stop-words
     *
     * @param uid Index identifier
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun resetStopWordsSettings(uid: String): Task {
        return jsonHandler.decode(request.delete("/indexes/$uid/settings/stop-words"))
    }

    /**
     * Get the searchable attributes of an index.
     * https://docs.meilisearch.com/reference/api/searchable_attributes.html#get-searchable-attributes
     *
     * @param uid Index identifier
     * @return an array of strings that contains the searchable attributes
     * @throws Exception if an error occurs
     */
    fun getSearchableAttributesSettings(uid: String): Array<String> {
        return jsonHandler.decode(request.get("/indexes/$uid/settings/searchable-attributes"))
    }

    /**
     * Updates the searchable attributes an index Refer
     * https://docs.meilisearch.com/reference/api/searchable_attributes.html#update-searchable-attributes
     *
     * @param uid Index identifier
     * @param searchableAttributes an array of strings that contains the new searchable attributes
     * settings
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun updateSearchableAttributesSettings(uid: String, searchableAttributes: Array<String>): Task {
        val searchableAttributesAsJson = jsonHandler.encode(searchableAttributes)
        return jsonHandler.decode(
            request.put(
                "/indexes/$uid/settings/searchable-attributes",
                searchableAttributesAsJson
            )
        )
    }

    /**
     * Reset the searchable attributes of the index to the default value.
     * https://docs.meilisearch.com/reference/api/searchable_attributes.html#reset-searchable-attributes
     *
     * @param uid Index identifier
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun resetSearchableAttributesSettings(uid: String): Task {
        return jsonHandler.decode(request.delete("/indexes/$uid/settings/searchable-attributes"))
    }

    /**
     * Get the display attributes of an index.
     * https://docs.meilisearch.com/reference/api/displayed_attributes.html#get-displayed-attributes
     *
     * @param uid Index identifier
     * @return an array of strings that contains attributes of an index to display
     * @throws Exception if an error occurs
     */
    fun getDisplayedAttributesSettings(uid: String): Array<String> {
        return jsonHandler.decode(request.get("/indexes/$uid/settings/displayed-attributes"))
    }

    /**
     * Updates the display attributes of an index Refer
     * https://docs.meilisearch.com/reference/api/displayed_attributes.html#update-displayed-attributes
     *
     * @param uid Index identifier
     * @param displayAttributes an array of strings that contains the new displayed attributes
     * settings
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun updateDisplayedAttributesSettings(uid: String, displayAttributes: Array<String>): Task {
        val displayAttributesAsJson = jsonHandler.encode(displayAttributes)
        return jsonHandler.decode(
            request.put(
                "/indexes/$uid/settings/displayed-attributes",
                displayAttributesAsJson
            )
        )
    }

    /**
     * Reset the displayed attributes of the index to the default value.
     * https://docs.meilisearch.com/reference/api/displayed_attributes.html#reset-displayed-attributes
     *
     * @param uid Index identifier
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun resetDisplayedAttributesSettings(uid: String): Task {
        return jsonHandler.decode(request.delete("/indexes/$uid/settings/displayed-attributes"))
    }

    /**
     * Get an index's filterableAttributes.
     * https://docs.meilisearch.com/reference/api/filterable_attributes.html#get-filterable-attributes
     *
     * @param uid Index identifier
     * @return an array of strings that contains the filterable attributes settings
     * @throws Exception if an error occurs
     */
    fun getFilterableAttributesSettings(uid: String): Array<String> {
        return jsonHandler.decode(request.get("/indexes/$uid/settings/filterable-attributes"))
    }

    /**
     * Update an index's filterable attributes list. This will re-index all documents in the index.
     * https://docs.meilisearch.com/reference/api/filterable_attributes.html#update-filterable-attributes
     *
     * @param uid Index identifier
     * @param filterableAttributes an array of strings that contains the new filterable attributes
     * settings
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun updateFilterableAttributesSettings(uid: String, filterableAttributes: Array<String>): Task {
        val filterableAttributesAsJson = jsonHandler.encode(filterableAttributes)
        return jsonHandler.decode(
            request.put(
                "/indexes/$uid/settings/filterable-attributes",
                filterableAttributesAsJson
            )
        )
    }

    /**
     * Reset an index's filterable attributes list back to its default value.
     * https://docs.meilisearch.com/reference/api/filterable_attributes.html#reset-filterable-attributes
     *
     * @param uid Index identifier
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun resetFilterableAttributesSettings(uid: String): Task {
        return jsonHandler.decode(request.delete("/indexes/$uid/settings/filterable-attributes"))
    }

    /**
     * Get the distinct attribute field of an index.
     * https://docs.meilisearch.com/reference/api/distinct_attribute.html#get-distinct-attribute
     *
     * @param uid Index identifier
     * @return a string of the distinct attribute field
     * @throws Exception if an error occurs
     */
    fun getDistinctAttributeSettings(uid: String): String {
        return jsonHandler.decode(request.get("/indexes/$uid/settings/distinct-attribute"))
    }

    /**
     * Update the distinct attribute field of an index.
     * https://docs.meilisearch.com/reference/api/distinct_attribute.html#update-distinct-attribute
     *
     * @param uid Index identifier
     * @param distinctAttribute a String that contains the new distinct attributes settings
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun updateDistinctAttributeSettings(uid: String, distinctAttribute: String?): Task {
        val distinctAttributeAsJson = jsonHandler.encode(distinctAttribute)
        return jsonHandler.decode(
            request.put(
                "/indexes/$uid/settings/distinct-attribute",
                distinctAttributeAsJson
            )
        )
    }

    /**
     * Reset the distinct attribute field of an index to its default value.
     * https://docs.meilisearch.com/reference/api/distinct_attribute.html#reset-distinct-attribute
     *
     * @param uid Index identifier
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun resetDistinctAttributeSettings(uid: String): Task {
        return jsonHandler.decode(request.delete("/indexes/$uid/settings/distinct-attribute"))
    }

    /**
     * Gets the typo tolerance settings of a given index Refer
     * https://docs.meilisearch.com/reference/api/typo_tolerance.html#get-typo-tolerance
     *
     * @param uid Index identifier
     * @return a TypoTolerance instance that contains all typo tolerance settings
     * @throws Exception if an error occurs
     */
    fun getTypoToleranceSettings(uid: String): TypoTolerance {
        return jsonHandler.decode(request.get("/indexes/$uid/settings/typo-tolerance"))
    }

    /**
     * Updates the typo tolerance settings of a given index Refer
     * https://docs.meilisearch.com/reference/api/typo_tolerance.html#update-typo-tolerance
     *
     * @param uid Index identifier
     * @param typoTolerance a TypoTolerance instance that contains the new typo tolerance settings
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun updateTypoToleranceSettings(uid: String, typoTolerance: TypoTolerance?): Task {
        val typoToleranceAsJson = jsonHandler.encode(typoTolerance)
        return jsonHandler.decode(request.post("/indexes/$uid/settings/typo-tolerance", typoToleranceAsJson))
    }

    /**
     * Resets the typo tolerance settings of a given index Refer
     * https://docs.meilisearch.com/reference/api/typo_tolerance.html#reset-typo-tolerance
     *
     * @param uid Index identifier
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun resetTypoToleranceSettings(uid: String): Task {
        return jsonHandler.decode(request.delete("/indexes/$uid/settings/typo-tolerance"))
    }
}
