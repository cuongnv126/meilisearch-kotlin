package com.meilisearch.sdk.handler

import com.meilisearch.sdk.Config
import com.meilisearch.sdk.http.MeiliSearchHttpRequest
import com.meilisearch.sdk.json.decode
import com.meilisearch.sdk.model.Result
import com.meilisearch.sdk.model.Task
import java.util.concurrent.TimeoutException
import kotlinx.coroutines.delay

/**
 * Wrapper around MeilisearchHttpRequest class to use for MeiliSearch tasks
 *
 *
 * Refer https://docs.meilisearch.com/reference/api/tasks.html
 */
internal class TasksHandler(config: Config) {
    private val request = MeiliSearchHttpRequest(config)
    private val jsonHandler = config.jsonHandlerFactory.newJsonHandler()

    /**
     * Retrieves the Task at the specified indexUid with the specified taskUid
     *
     * @param indexUid Index identifier to the requested Task
     * @param taskUid Identifier of the requested Task
     * @return Task instance
     * @throws Exception if client request causes an error
     */
    fun getTask(indexUid: String, taskUid: Int): Task {
        val urlPath = "/indexes/$indexUid/tasks/$taskUid"
        return jsonHandler.decode(request.get(urlPath))
    }

    /**
     * Retrieves all TasksHandler at the specified iud
     *
     * @param indexUid Index identifier to the requested Tasks
     * @return List of task instance
     * @throws Exception if client request causes an error
     */
    fun getTasks(indexUid: String): Array<Task> {
        val urlPath = "/indexes/$indexUid/tasks"
        return jsonHandler.decode(request.get(urlPath))
    }

    /**
     * Retrieves the Task at the specified iud with the specified taskUid
     *
     * @param taskUid Identifier of the requested Task
     * @return Task instance
     * @throws Exception if client request causes an error
     */
    fun getTask(taskUid: Int): Task {
        val urlPath = "/tasks/$taskUid"
        return jsonHandler.decode(request.get(urlPath))
    }

    /**
     * Retrieves Tasks from the client
     *
     * @return List of task instance
     * @throws Exception if client request causes an error
     */
    fun getTasks(): Array<Task>? {
        val urlPath = "/tasks"
        val result = jsonHandler.decode<Result<Task>>(request.get(urlPath))
        return result.results
    }
    /**
     * Waits for a task to be processed
     *
     * @param taskUid Identifier of the Task
     * @param timeoutInMs number of milliseconds before throwing an Exception
     * @param intervalInMs number of milliseconds before requesting the status again
     * @throws Exception if timeout is reached
     */
    /**
     * Waits for a task to be processed
     *
     * @param taskUid Identifier of the Task
     * @throws Exception if timeout is reached
     */
    @JvmOverloads
    suspend fun waitForTask(taskUid: Int, timeoutInMs: Long = 5000L, intervalInMs: Long = 50L): Task {
        var task: Task
        var status: String

        val startTime = System.currentTimeMillis()
        var elapsedTime: Long = 0
        do {
            if (elapsedTime >= timeoutInMs) {
                throw TimeoutException()
            }

            task = getTask(taskUid)
            status = task.status
            if (status == SUCCEEDED || status == FAILED) break

            delay(intervalInMs)
            elapsedTime = System.currentTimeMillis() - startTime
        } while (true)

        return task
    }

    companion object {
        const val SUCCEEDED = "succeeded"
        const val FAILED = "failed"
    }
}
