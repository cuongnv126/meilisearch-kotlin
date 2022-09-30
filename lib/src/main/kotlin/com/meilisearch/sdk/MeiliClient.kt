/*
 * Official Java client for Meilisearch
 */
package com.meilisearch.sdk

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.meilisearch.sdk.handler.DumpHandler
import com.meilisearch.sdk.handler.Index
import com.meilisearch.sdk.handler.IndexesHandler
import com.meilisearch.sdk.handler.KeysHandler
import com.meilisearch.sdk.handler.TasksHandler
import com.meilisearch.sdk.exceptions.MeiliSearchException
import com.meilisearch.sdk.json.decode
import com.meilisearch.sdk.model.Dump
import com.meilisearch.sdk.model.Key
import com.meilisearch.sdk.model.Task
import com.meilisearch.sdk.model.TenantTokenOptions
import java.util.Date
import java.util.TimeZone

class MeiliClient(
    val config: Config
) {
    private val indexesHandler: IndexesHandler = IndexesHandler(config)
    private val tasksHandler: TasksHandler = TasksHandler(config)
    private val keysHandler: KeysHandler = KeysHandler(config)
    private val dumpHandler: DumpHandler = DumpHandler(config)
    private val jsonHandler = config.jsonHandlerFactory.newJsonHandler()

    /**
     * Creates index Refer https://docs.meilisearch.com/reference/api/indexes.html#create-an-index
     *
     * @param uid Unique identifier for the index to create
     * @return Meilisearch API response as Task
     * @throws Exception if an error occurs
     */
    @JvmOverloads
    fun createIndex(uid: String, primaryKey: String? = null): Task {
        return jsonHandler.decode(indexesHandler.create(uid, primaryKey))
    }

    /**
     * Gets all indexes Refer
     * https://docs.meilisearch.com/reference/api/indexes.html#list-all-indexes
     *
     * @return List of indexes in the Meilisearch client
     * @throws Exception if an error occurs
     */
    fun getIndexList(): Array<Index> {
        val meiliSearchIndexList = jsonHandler.decode<Array<Index>>(getRawIndexList())
        for (indexes in meiliSearchIndexList) {
            indexes.setConfig(config)
        }
        return meiliSearchIndexList
    }

    /**
     * Gets all indexes https://docs.meilisearch.com/reference/api/indexes.html#list-all-indexes
     *
     * @return Meilisearch API response as String
     * @throws Exception if an error occurs
     */
    fun getRawIndexList(): String = indexesHandler.getAll()

    /**
     * Creates a local reference to an index identified by `uid`, without doing an HTTP call.
     * Calling this method doesn't create an index by itself, but grants access to all the other
     * methods in the Index class.
     *
     * @param uid Unique identifier of the index
     * @return Index instance
     * @throws Exception if an error occurs
     */
    fun index(uid: String): Index {
        return Index(uid).apply {
            this.setConfig(config)
        }
    }

    /**
     * Gets single index by uid Refer
     * https://docs.meilisearch.com/reference/api/indexes.html#get-one-index
     *
     * @param uid Unique identifier of the index to get
     * @return Meilisearch API response
     * @throws Exception if an error occurs
     */
    fun getIndex(uid: String): Index {
        val index: Index = jsonHandler.decode(getRawIndex(uid))
        index.setConfig(config)
        return index
    }

    /**
     * Gets single index by uid Refer
     * https://docs.meilisearch.com/reference/api/indexes.html#get-one-index
     *
     * @param uid Unique identifier of the index to get
     * @return Meilisearch API response as String
     * @throws Exception if an error occurs
     */
    fun getRawIndex(uid: String): String {
        return indexesHandler.get(uid)
    }

    /**
     * Updates single index by uid Refer
     * https://docs.meilisearch.com/reference/api/indexes.html#update-an-index
     *
     * @param uid Unique identifier of the index to update
     * @param primaryKey Primary key of the documents in the index
     * @return Meilisearch API response as Task
     * @throws Exception if an error occurs
     */
    fun updateIndex(uid: String, primaryKey: String?): Task {
        return jsonHandler.decode(indexesHandler.updatePrimaryKey(uid, primaryKey))
    }

    /**
     * Deletes single index by uid Refer
     * https://docs.meilisearch.com/reference/api/indexes.html#delete-one-index
     *
     * @param uid Unique identifier of the index to delete
     * @return Meilisearch API response as Task
     * @throws Exception if an error occurs
     */
    fun deleteIndex(uid: String): Task {
        return jsonHandler.decode(indexesHandler.delete(uid))
    }

    /**
     * Triggers the creation of a Meilisearch dump. Refer
     * https://docs.meilisearch.com/reference/api/dump.html#create-a-dump
     *
     * @return Dump instance
     * @throws Exception if an error occurs
     */
    fun createDump(): Dump {
        return dumpHandler.createDump()
    }

    /**
     * Gets the status of a Meilisearch dump.
     * https://docs.meilisearch.com/reference/api/dump.html#get-dump-status
     *
     * @param uid Unique identifier for correspondent dump
     * @return String with dump status
     * @throws Exception if an error occurs
     */
    fun getDumpStatus(uid: String): String {
        return dumpHandler.getDumpStatus(uid)
    }

    /**
     * Retrieves a task with the specified uid
     *
     * @param uid Identifier of the requested Task
     * @return Task Instance
     * @throws Exception if an error occurs
     */
    fun getTask(uid: Int): Task {
        return tasksHandler.getTask(uid)
    }

    /**
     * Retrieves list of tasks
     *
     * @return List of tasks in the Meilisearch client
     * @throws Exception if an error occurs
     */
    fun getTask() = tasksHandler.getTasks()

    /**
     * Waits for a task to be processed
     *
     * @param uid Identifier of the requested Task
     * @throws Exception if an error occurs or if timeout is reached
     */
    fun waitForTask(uid: Int) = tasksHandler.waitForTask(uid)

    /**
     * Retrieves the key with the specified uid
     *
     * @param uid Identifier of the requested Key
     * @return Key Instance
     * @throws Exception if an error occurs
     */
    fun getKey(uid: String): Key {
        return keysHandler.getKey(uid)
    }

    /**
     * Retrieves list of keys
     *
     * @return List of keys in the Meilisearch client
     * @throws Exception if an error occurs
     */
    fun getKeys() = keysHandler.getKeys()

    /**
     * Creates a key
     *
     * @param options Key containing the options of the key
     * @return Key Instance
     * @throws Exception if an error occurs
     */
    fun createKey(options: Key): Key {
        return keysHandler.createKey(options)
    }

    /**
     * Deletes a key
     *
     * @param key String containing the key
     * @throws Exception if an error occurs
     */
    fun deleteKey(key: String) {
        keysHandler.deleteKey(key)
    }

    /**
     * Generate a tenant token
     *
     * @param searchRules A Map of string, object which contains the rules to be enforced at search
     * time for all or specific accessible indexes for the signing API Key.
     * @param options A TenantTokenOptions, the following fileds are accepted: - apiKey: String
     * containing the API key parent of the token. If you leave it empty the client API Key will
     * be used. - expiresAt: A DateTime when the key will expire. Note that if an expiresAt
     * value is included it should be in UTC time.
     * @return String containing the tenant token
     * @throws MeiliSearchException if an error occurs
     */
    @JvmOverloads
    fun generateTenantToken(
        searchRules: Map<String?, Any?>?,
        options: TenantTokenOptions = TenantTokenOptions()
    ): String {
        // Validate all fields
        val now = Date()
        val secret: String = if (options.apiKey.isNullOrEmpty()) {
            config.masterKey
        } else {
            options.apiKey
        }
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
        if (options.expiresAt != null && now.after(options.expiresAt)) {
            throw MeiliSearchException("The date expiresAt should be in the future.")
        }
        if (secret === "" || secret.length <= 8) {
            throw MeiliSearchException(
                "An api key is required in the client or should be passed as an argument and this key cannot be the master key."
            )
        }
        if (searchRules == null) {
            throw MeiliSearchException(
                "The searchRules field is mandatory and should be defined."
            )
        }

        // Encrypt the key
        val algorithm = Algorithm.HMAC256(secret)

        // Create JWT
        return JWT.create()
            .withClaim("searchRules", searchRules)
            .withClaim("apiKeyPrefix", secret.substring(0, 8))
            .withExpiresAt(options.expiresAt)
            .sign(algorithm)
    }
}
