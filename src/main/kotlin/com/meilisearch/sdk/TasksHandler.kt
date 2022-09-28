package com.meilisearch.sdk

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

/**
 * Wrapper around MeilisearchHttpRequest class to use for MeiliSearch tasks
 *
 *
 * Refer https://docs.meilisearch.com/reference/api/tasks.html
 */
class TasksHandler(config: Config) {
    private val meiliSearchHttpRequest = MeiliSearchHttpRequest(config)
    private val gson = Gson()

    /**
     * Retrieves the Task at the specified indexUid with the specified taskUid
     *
     * @param indexUid Index identifier to the requested Task
     * @param taskUid Identifier of the requested Task
     * @return Task instance
     * @throws Exception if client request causes an error
     */
    fun getTask(indexUid: String?, taskUid: Int): Task {
        val urlPath = "/indexes/$indexUid/tasks/$taskUid"
        return gson.fromJson(meiliSearchHttpRequest.get(urlPath), Task::class.java)
    }

    /**
     * Retrieves all TasksHandler at the specified iud
     *
     * @param indexUid Index identifier to the requested Tasks
     * @return List of task instance
     * @throws Exception if client request causes an error
     */
    fun getTasks(indexUid: String?): Array<Task> {
        val urlPath = "/indexes/$indexUid/tasks"
        return gson.fromJson(meiliSearchHttpRequest.get(urlPath), Array<Task>::class.java)
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
        return gson.fromJson(meiliSearchHttpRequest.get(urlPath), Task::class.java)
    }

    /**
     * Retrieves Tasks from the client
     *
     * @return List of task instance
     * @throws Exception if client request causes an error
     */
    fun getTasks(): Array<Task>? {
        val urlPath = "/tasks"
        val result = gson.fromJson<Result<Task>>(
            meiliSearchHttpRequest.get(urlPath),
            object : TypeToken<Result<Task?>?>() {}.type
        )
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
    fun waitForTask(taskUid: Int, timeoutInMs: Int = 5000, intervalInMs: Int = 50) {
        var task: Task
        var status = ""
        val startTime = Date().time
        var elapsedTime: Long = 0
        while (status != SUCCEEDED && status != FAILED) {
            if (elapsedTime >= timeoutInMs) {
                throw Exception()
            }
            task = this.getTask(taskUid)
            status = task.status
            Thread.sleep(intervalInMs.toLong())
            elapsedTime = Date().time - startTime
        }
    }

    companion object {
        const val SUCCEEDED = "succeeded"
        const val FAILED = "failed"
    }
}
