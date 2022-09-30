package com.meilisearch.sdk.handler

import com.meilisearch.sdk.Config
import com.meilisearch.sdk.http.MeiliSearchHttpRequest
import com.meilisearch.sdk.model.Settings
import com.meilisearch.sdk.model.Task
import com.meilisearch.sdk.model.TypoTolerance
import com.meilisearch.sdk.json.JsonHandler
import com.meilisearch.sdk.json.decode
import com.meilisearch.sdk.model.SearchRequest
import com.meilisearch.sdk.model.SearchResult
import java.io.Serializable
import org.json.JSONArray

class Index internal constructor(
    val uid: String,
    val createdAt: String? = null,
    val updatedAt: String? = null
) : Serializable {
    var primaryKey: String? = null

    private lateinit var config: Config
    private lateinit var documents: Documents
    private lateinit var tasksHandler: TasksHandler
    private lateinit var search: Search
    private lateinit var settingsHandler: SettingsHandler
    private lateinit var jsonHandler: JsonHandler

    /**
     * Sets the Meilisearch configuration for the index
     *
     * @param config Meilisearch configuration to use
     */
    fun setConfig(config: Config) {
        this.config = config
        documents = Documents(config)
        tasksHandler = TasksHandler(config)
        search = Search(config)
        settingsHandler = SettingsHandler(config)
        jsonHandler = config.jsonHandlerFactory.newJsonHandler()
    }

    /**
     * Gets a document in the index
     *
     * @param identifier Identifier of the document to get
     * @return Meilisearch API response
     * @throws Exception if an error occurs
     */
    fun getDocument(identifier: String): String {
        return documents.getDocument(uid, identifier)
    }

    /**
     * Gets documents in the index
     *
     * @return Meilisearch API response
     * @throws Exception if an error occurs
     */
    fun getDocuments(): String {
        return documents.getDocuments(uid)
    }

    /**
     * Gets documents in the index and limit the number of documents returned
     *
     * @param limits Maximum amount of documents to return
     * @return Meilisearch API response
     * @throws Exception if an error occurs
     */
    fun getDocuments(limits: Int): String {
        return documents.getDocuments(uid, limits)
    }

    /**
     * Gets documents in the index and limit the number of documents returned
     *
     * @param limits Maximum amount of documents to return
     * @param offset Number of documents to skip
     * @return Meilisearch API response
     * @throws Exception if an error occurs
     */
    fun getDocuments(limits: Int, offset: Int): String {
        return documents.getDocuments(uid, limits, offset)
    }

    /**
     * Gets documents in the index and limit the number of documents returned
     *
     * @param limits Maximum amount of documents to return
     * @param offset Number of documents to skip
     * @param attributesToRetrieve Document attributes to show
     * @return Meilisearch API response
     * @throws Exception if an error occurs
     */
    fun getDocuments(limits: Int, offset: Int, attributesToRetrieve: List<String?>?): String? {
        return documents.getDocuments(uid, limits, offset, attributesToRetrieve)
    }

    /**
     * Adds a document in the index
     *
     * @param document Document to add in JSON string format
     * @return Task Meilisearch API response
     * @throws Exception if an error occurs
     */
    fun addDocuments(document: String?): Task {
        return documents.addDocuments(uid, document, null)
    }

    /**
     * Adds a document in the index
     *
     * @param document Document to add in JSON string format
     * @param primaryKey PrimaryKey of the document to add
     * @return Task Meilisearch API response
     * @throws Exception if an error occurs
     */
    fun addDocuments(document: String?, primaryKey: String?): Task {
        return documents.addDocuments(uid, document, primaryKey)
    }
    /**
     * Adds a document in the index in batches
     *
     * @param batchSize size of the batch of documents
     * @param document Document to add in JSON string format
     * @param primaryKey PrimaryKey of the document to add
     * @return Task Meilisearch API response
     * @throws Exception if an error occurs
     */
    /**
     * Add Documents in Index in Batches
     *
     * @param document Document to add in JSON string format
     * @return Task Meilisearch API response
     * @throws Exception if an error occurs
     */
    @JvmOverloads
    fun addDocumentsInBatches(
        document: String?,
        batchSize: Int = 1000,
        primaryKey: String? = null
    ): Array<Task?> {
        val jsonDocumentsArray = JSONArray(document)
        val jsonSubArray = JSONArray()
        val arrayResponses: MutableList<Task?> = ArrayList()
        val calcBatchSize = if (jsonDocumentsArray.length() < batchSize) jsonDocumentsArray.length() else batchSize

        var i = 0
        while (i < jsonDocumentsArray.length()) {
            var j = 0
            while (j < calcBatchSize && j + i < jsonDocumentsArray.length()) {
                jsonSubArray.put(j, jsonDocumentsArray[i + j])
                j++
            }
            arrayResponses.add(
                documents.addDocuments(uid, jsonSubArray.toString(), primaryKey)
            )
            i += calcBatchSize
        }

        return arrayResponses.toTypedArray()
    }

    /**
     * Updates a document in the index
     *
     * @param document Document to update in JSON string format
     * @return Task Meilisearch API response
     * @throws Exception if an error occurs
     */
    fun updateDocuments(document: String?): Task {
        return documents.updateDocuments(uid, document, null)
    }

    /**
     * Updates a document in the index
     *
     * @param document Document to update in JSON string format
     * @param primaryKey PrimaryKey of the document
     * @return Task Meilisearch API response
     * @throws Exception if an error occurs
     */
    fun updateDocuments(document: String?, primaryKey: String?): Task {
        return documents.updateDocuments(uid, document, primaryKey)
    }
    /**
     * Update Documents in Index in Batches
     *
     * @param document Document to add in JSON string format
     * @param batchSize size of the batch of documents
     * @param primaryKey PrimaryKey of the document to add
     * @return Task Meilisearch API response
     * @throws Exception if an error occurs
     */
    /**
     * Update Documents in Index in Batches
     *
     * @param document Document to add in JSON string format
     * @return Task Meilisearch API response
     * @throws Exception if an error occurs
     */
    @JvmOverloads
    fun updateDocumentsInBatches(document: String?, batchSize: Int = 1000, primaryKey: String? = null): Array<Task?> {
        val jsonDocumentsArray = JSONArray(document)
        val jsonSubArray = JSONArray()
        val arrayResponses: MutableList<Task?> = ArrayList()
        val batchSize2 = if (jsonDocumentsArray.length() < batchSize) jsonDocumentsArray.length() else batchSize
        var i = 0
        while (i < jsonDocumentsArray.length()) {
            var j = 0
            while (j < batchSize2 && j + i < jsonDocumentsArray.length()) {
                jsonSubArray.put(j, jsonDocumentsArray[i + j])
                j++
            }
            arrayResponses.add(
                documents.updateDocuments(uid, jsonSubArray.toString(), primaryKey)
            )
            i += batchSize2
        }
        return arrayResponses.toTypedArray()
    }

    /**
     * Deletes a document from the index
     *
     * @param identifier Identifier of the document to delete
     * @return Task Meilisearch API response
     * @throws Exception if an error occurs
     */
    fun deleteDocument(identifier: String): Task {
        return documents.deleteDocument(uid, identifier)
    }

    /**
     * Deletes list of documents from the index
     *
     * @param documentsIdentifiers list of identifiers of documents to delete
     * @return Task Meilisearch API response
     * @throws Exception if an error occurs
     */
    fun deleteDocuments(documentsIdentifiers: List<String?>): Task {
        return documents.deleteDocuments(uid, documentsIdentifiers)
    }

    /**
     * Deletes all documents in the index
     *
     * @return List of tasks Meilisearch API response
     * @throws Exception if an error occurs
     */
    fun deleteAllDocuments(): Task {
        return documents.deleteAllDocuments(uid)
    }

    /**
     * Searches documents in index
     *
     * @param query Query string
     * @return Meilisearch API response
     * @throws Exception if an error occurs
     */
    fun search(query: String): SearchResult {
        return search.search(uid, query)
    }

    /**
     * Searches documents in index
     *
     * @param searchRequest SearchRequest SearchRequest
     * @return Meilisearch API response
     * @throws Exception if an error occurs
     */
    fun search(searchRequest: SearchRequest): SearchResult {
        return search.search(uid, searchRequest)
    }

    fun rawSearch(query: String): String {
        return search.rawSearch(uid, query)
    }

    fun rawSearch(searchRequest: SearchRequest): String {
        return search.rawSearch(uid, searchRequest)
    }

    /**
     * Gets the settings of the index Refer
     * https://docs.meilisearch.com/reference/api/settings.html#get-settings
     *
     * @return settings of a given uid as String
     * @throws Exception if an error occurs
     */
    fun getSettings() = settingsHandler.getSettings(uid)

    /**
     * Updates the settings of the index Refer
     * https://docs.meilisearch.com/reference/api/settings.html#update-settings
     *
     * @param settings the object that contains the data with the new settings
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun updateSettings(settings: Settings): Task {
        return settingsHandler.updateSettings(uid, settings)
    }

    /**
     * Resets the settings of the index Refer
     * https://docs.meilisearch.com/reference/api/settings.html#reset-settings
     *
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun resetSettings(): Task {
        return settingsHandler.resetSettings(uid)
    }

    /**
     * Gets the ranking rule settings of the index Refer
     * https://docs.meilisearch.com/reference/api/settings.html#get-settings
     *
     * @return ranking rules of a given uid as String
     * @throws Exception if an error occurs
     */
    fun getRankingRuleSettings() = settingsHandler.getRankingRuleSettings(uid)

    /**
     * Updates the ranking rule settings of the index Refer
     * https://docs.meilisearch.com/reference/api/settings.html#update-settings
     *
     * @param rankingRules array that contain the data with the new ranking rules
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun updateRankingRuleSettings(rankingRules: Array<String>): Task {
        return settingsHandler.updateRankingRuleSettings(uid, rankingRules)
    }

    /**
     * Resets the ranking rule settings of the index Refer
     * https://docs.meilisearch.com/reference/api/settings.html#reset-settings
     *
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun resetRankingRuleSettings(): Task {
        return settingsHandler.resetRankingRulesSettings(uid)
    }

    /**
     * Gets the synonyms settings of the index Refer
     * https://docs.meilisearch.com/reference/api/settings.html#get-settings
     *
     * @return synonyms of a given uid as String
     * @throws Exception if an error occurs
     */
    fun getSynonymsSettings() = settingsHandler.getSynonymsSettings(uid)

    /**
     * Updates the synonyms settings of the index Refer
     * https://docs.meilisearch.com/reference/api/synonyms.html#update-synonyms
     *
     * @param synonyms key (String) value (array) pair of synonyms
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun updateSynonymsSettings(synonyms: Map<String?, Array<String>?>): Task {
        return settingsHandler.updateSynonymsSettings(uid, synonyms)
    }

    /**
     * Resets the synonyms settings of the index Refer
     * https://docs.meilisearch.com/reference/api/synonyms.html#reset-synonyms
     *
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun resetSynonymsSettings(): Task {
        return settingsHandler.resetSynonymsSettings(uid)
    }

    /**
     * Gets the stop-words settings of the index Refer
     * https://docs.meilisearch.com/reference/api/stop_words.html#get-stop-words
     *
     * @return stop-words of a given uid as String
     * @throws Exception if an error occurs
     */
    fun getStopWordsSettings() = settingsHandler.getStopWordsSettings(uid)

    /**
     * Updates the stop-words settings of the index Refer
     * https://docs.meilisearch.com/reference/api/stop_words.html#update-stop-words
     *
     * @param stopWords An array of strings that contains the stop-words.
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun updateStopWordsSettings(stopWords: Array<String>): Task {
        return settingsHandler.updateStopWordsSettings(uid, stopWords)
    }

    /**
     * Resets the stop-words settings of the index Refer
     * https://docs.meilisearch.com/reference/api/stop_words.html#reset-stop-words
     *
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun resetStopWordsSettings(): Task {
        return settingsHandler.resetStopWordsSettings(uid)
    }

    /**
     * Get the searchable attributes of an index.
     * https://docs.meilisearch.com/reference/api/searchable_attributes.html#get-searchable-attributes
     *
     * @return searchable attributes of a given uid as String
     * @throws Exception if an error occurs
     */
    fun getSearchableAttributesSettings() = settingsHandler.getSearchableAttributesSettings(uid)

    /**
     * Updates the searchable attributes an index Refer
     * https://docs.meilisearch.com/reference/api/searchable_attributes.html#update-searchable-attributes
     *
     * @param searchableAttributes An array of strings that contains the searchable attributes.
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun updateSearchableAttributesSettings(searchableAttributes: Array<String>): Task {
        return settingsHandler.updateSearchableAttributesSettings(
            uid, searchableAttributes
        )
    }

    /**
     * Reset the searchable attributes of the index to the default value.
     * https://docs.meilisearch.com/reference/api/searchable_attributes.html#reset-searchable-attributes
     *
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun resetSearchableAttributesSettings(): Task {
        return settingsHandler.resetSearchableAttributesSettings(uid)
    }

    /**
     * Get the display attributes of an index.
     * https://docs.meilisearch.com/reference/api/displayed_attributes.html#get-displayed-attributes
     *
     * @return display attributes of a given uid as String
     * @throws Exception if an error occurs
     */
    fun getDisplayedAttributesSettings() = settingsHandler.getDisplayedAttributesSettings(uid)

    /**
     * Updates the display attributes of an index Refer
     * https://docs.meilisearch.com/reference/api/displayed_attributes.html#update-displayed-attributes
     *
     * @param displayAttributes An array of strings that contains attributes of an index to display
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun updateDisplayedAttributesSettings(displayAttributes: Array<String>): Task {
        return settingsHandler.updateDisplayedAttributesSettings(uid, displayAttributes)
    }

    /**
     * Reset the displayed attributes of the index to the default value.
     * https://docs.meilisearch.com/reference/api/displayed_attributes.html#reset-displayed-attributes
     *
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun resetDisplayedAttributesSettings(): Task {
        return settingsHandler.resetDisplayedAttributesSettings(uid)
    }

    /**
     * Get an index's filterable attributes.
     * https://docs.meilisearch.com/reference/api/filterable_attributes.html#get-filterable-attributes
     *
     * @return filterable attributes of a given uid as String
     * @throws Exception if an error occurs
     */
    fun getFilterableAttributesSettings() = settingsHandler.getFilterableAttributesSettings(uid)

    /**
     * Update an index's filterable attributes list. This will re-index all documents in the index.
     * https://docs.meilisearch.com/reference/api/filterable_attributes.html#update-filterable-attributes
     *
     * @param filterableAttributes An array of strings containing the attributes that can be used as
     * filters at query time.
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun updateFilterableAttributesSettings(filterableAttributes: Array<String>): Task {
        return settingsHandler.updateFilterableAttributesSettings(
            uid, filterableAttributes
        )
    }

    /**
     * Reset an index's filterable attributes list back to its default value.
     * https://docs.meilisearch.com/reference/api/filterable_attributes.html#reset-filterable-attributes
     *
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun resetFilterableAttributesSettings(): Task {
        return settingsHandler.resetFilterableAttributesSettings(uid)
    }

    /**
     * Get the distinct attribute field of an index.
     * https://docs.meilisearch.com/reference/api/distinct_attribute.html#get-distinct-attribute
     *
     * @return distinct attribute field of a given uid as String
     * @throws Exception if an error occurs
     */
    fun getDistinctAttributeSettings() = settingsHandler.getDistinctAttributeSettings(uid)

    /**
     * Update the distinct attribute field of an index.
     * https://docs.meilisearch.com/reference/api/distinct_attribute.html#update-distinct-attribute
     *
     * @param distinctAttribute A String: the field name.
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun updateDistinctAttributeSettings(distinctAttribute: String?): Task {
        return settingsHandler.updateDistinctAttributeSettings(uid, distinctAttribute)
    }

    /**
     * Reset the distinct attribute field of an index to its default value.
     * https://docs.meilisearch.com/reference/api/distinct_attribute.html#reset-distinct-attribute
     *
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun resetDistinctAttributeSettings(): Task {
        return settingsHandler.resetDistinctAttributeSettings(uid)
    }

    /**
     * Get the typo tolerance field of an index.
     * https://docs.meilisearch.com/reference/api/typo_tolerance.html#get-typo-tolerance
     *
     * @return TypoTolerance instance from Index
     * @throws Exception if an error occurs
     */
    fun getTypoToleranceSettings() = settingsHandler.getTypoToleranceSettings(uid)

    /**
     * Update the typo tolerance field of an index.
     * https://docs.meilisearch.com/reference/api/typo_tolerance.html#update-typo-tolerance
     *
     * @param typoTolerance A TypoTolerance instance
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun updateTypoToleranceSettings(typoTolerance: TypoTolerance?): Task {
        return settingsHandler.updateTypoToleranceSettings(uid, typoTolerance)
    }

    /**
     * Reset the typo tolerance field of an index to its default value.
     * https://docs.meilisearch.com/reference/api/typo_tolerance.html#reset-typo-tolerance
     *
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun resetTypoToleranceSettings(): Task {
        return settingsHandler.resetTypoToleranceSettings(uid)
    }

    /**
     * Retrieves an index tasks by its ID
     *
     * @param taskId Identifier of the requested index task
     * @return Task instance
     * @throws Exception if an error occurs
     */
    fun getTask(taskId: Int): Task {
        return tasksHandler.getTask(uid, taskId)
    }

    /**
     * Retrieves list of tasks of the index
     *
     * @return List of tasks in the MeiliSearch index
     * @throws Exception if an error occurs
     */
    fun getTasks() = tasksHandler.getTasks(uid)

    /**
     * Waits for a task to be processed
     *
     * @param taskId Identifier of the requested Task
     * @throws Exception if an error occurs or if timeout is reached
     */
    suspend fun waitForTask(taskId: Int) = tasksHandler.waitForTask(taskId)

    /**
     * Waits for a task to be processed
     *
     * @param taskId ID of the index update
     * @param timeoutInMs number of milliseconds before throwing an Exception
     * @param intervalInMs number of milliseconds before requesting the status again
     * @throws Exception if an error occurs or if timeout is reached
     */
    suspend fun waitForTask(taskId: Int, timeoutInMs: Long, intervalInMs: Long) =
        tasksHandler.waitForTask(taskId, timeoutInMs, intervalInMs)

    /**
     * Fetches the primary key of the index in the Meilisearch instance
     *
     * @throws Exception if an error occurs
     */
    fun fetchPrimaryKey() {
        val requestQuery = "/indexes/$uid"
        val request = MeiliSearchHttpRequest(config)
        val retrievedIndex: Index = jsonHandler.decode(request.get(requestQuery))
        primaryKey = retrievedIndex.primaryKey
    }
}
