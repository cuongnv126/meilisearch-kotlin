package com.meilisearch.sdk

import com.google.gson.Gson
import com.google.gson.JsonArray
import java.util.function.Consumer

class Documents(config: Config) {
    private val meiliSearchHttpRequest = MeiliSearchHttpRequest(config)
    private val gson = Gson()

    /**
     * Retrieves the document at the specified uid with the specified identifier
     *
     * @param uid Partial index identifier for the requested documents
     * @param identifier ID of the document
     * @return String containing the requested document
     * @throws Exception if client request causes an error
     */
    fun getDocument(uid: String?, identifier: String): String {
        val urlPath = "/indexes/$uid/documents/$identifier"
        return meiliSearchHttpRequest.get(urlPath)
    }

    /**
     * Retrieves the documents at the specified uid
     *
     * @param uid Partial index identifier for the requested documents
     * @return String containing the requested document
     * @throws Exception if the client request causes an error
     */
    fun getDocuments(uid: String?): String {
        val urlPath = "/indexes/$uid/documents"
        return meiliSearchHttpRequest.get(urlPath)
    }

    /**
     * Retrieves the documents at the specified uid
     *
     * @param uid Partial index identifier for the requested documents
     * @param limit Limit on the requested documents to be returned
     * @return String containing the requested document
     * @throws Exception if the client request causes an error
     */
    fun getDocuments(uid: String?, limit: Int): String {
        val urlQuery = "/indexes/$uid/documents?limit=$limit"
        return meiliSearchHttpRequest.get(urlQuery)
    }

    /**
     * Retrieves the documents at the specified uid
     *
     * @param uid Partial index identifier for the requested documents
     * @param limit Limit on the requested documents to be returned
     * @param offset Specify the offset of the first hit to return
     * @return String containing the requested document
     * @throws Exception if the client request causes an error
     */
    fun getDocuments(uid: String?, limit: Int, offset: Int): String {
        val urlQuery = "/indexes/$uid/documents?limit=$limit&offset=$offset"
        return meiliSearchHttpRequest.get(urlQuery)
    }

    /**
     * Retrieves the documents at the specified uid
     *
     * @param uid Partial index identifier for the requested documents
     * @param limit Limit on the requested documents to be returned
     * @param offset Specify the offset of the first hit to return
     * @param attributesToRetrieve Document attributes to show
     * @return String containing the requested document
     * @throws Exception if the client request causes an error
     */
    fun getDocuments(uid: String?, limit: Int, offset: Int, attributesToRetrieve: List<String?>?): String {
        val attrRetrieve = if (attributesToRetrieve.isNullOrEmpty()) listOf("*") else attributesToRetrieve
        val attrRetrieveString = attrRetrieve.joinToString(",")

        val urlQuery = ("/indexes/"
            + uid
            + "/documents?limit="
            + limit
            + "&offset="
            + offset
            + "&attributesToRetrieve="
            + attrRetrieveString)
        return meiliSearchHttpRequest.get(urlQuery)
    }

    /**
     * Adds/Replaces a document at the specified uid
     *
     * @param uid Partial index identifier for the document
     * @param document String containing the document to add
     * @param primaryKey PrimaryKey of the document
     * @return Meilisearch's Task API response
     * @throws Exception if the client request causes an error
     */
    fun addDocuments(
        uid: String?,
        document: String?,
        primaryKey: String?
    ): Task {
        var urlQuery = "/indexes/$uid/documents"
        if (primaryKey != null) {
            urlQuery += "?primaryKey=$primaryKey"
        }
        return gson.fromJson(meiliSearchHttpRequest.post(urlQuery, document), Task::class.java)
    }

    /**
     * Replaces a document at the specified uid
     *
     * @param uid Partial index identifier for the document
     * @param document String containing the document to replace the existing document
     * @param primaryKey PrimaryKey of the document
     * @return Meilisearch's Task API response
     * @throws Exception if the client request causes an error
     */
    fun updateDocuments(
        uid: String?,
        document: String?,
        primaryKey: String?
    ): Task {
        var urlPath = "/indexes/$uid/documents"
        if (primaryKey != null) {
            urlPath += "?primaryKey=$primaryKey"
        }
        return gson.fromJson(meiliSearchHttpRequest.put(urlPath, document), Task::class.java)
    }

    /**
     * Deletes the document at the specified uid with the specified identifier
     *
     * @param uid Partial index identifier for the requested document
     * @param identifier ID of the document
     * @return Meilisearch's Task API response
     * @throws Exception if the client request causes an error
     */
    fun deleteDocument(uid: String?, identifier: String): Task {
        val urlPath = "/indexes/$uid/documents/$identifier"
        return gson.fromJson(meiliSearchHttpRequest.delete(urlPath), Task::class.java)
    }

    /**
     * Deletes the documents at the specified uid with the specified identifiers
     *
     * @param uid Partial index identifier for the requested documents
     * @param identifiers ID of documents to delete
     * @return Meilisearch's Task API response
     * @throws Exception if the client request causes an error
     */
    fun deleteDocuments(
        uid: String?,
        identifiers: List<String?>
    ): Task {
        val urlPath = "/indexes/$uid/documents/delete-batch"
        val requestData = JsonArray(identifiers.size)
        identifiers.forEach(Consumer { string: String? ->
            requestData.add(
                string
            )
        })
        return gson.fromJson(
            meiliSearchHttpRequest.post(urlPath, requestData.toString()), Task::class.java
        )
    }

    /**
     * Deletes all documents at the specified uid
     *
     * @param uid Partial index identifier for the requested documents
     * @return Meilisearch's Task API response
     * @throws Exception if the client request causes an error
     */
    fun deleteAllDocuments(uid: String?): Task {
        val urlPath = "/indexes/$uid/documents"
        return gson.fromJson(meiliSearchHttpRequest.delete(urlPath), Task::class.java)
    }
}
