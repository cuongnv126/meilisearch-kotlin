package com.meilisearch.sdk

import com.google.gson.Gson
import java.util.Date

class Task(
    val status: String = "",
    val uid: Long = 0,
    val indexUid: String = "",
    val type: String? = null,
    val duration: String = "",
    val enqueuedAt: Date? = null,
    val startedAt: Date? = null,
    val finishedAt: Date? = null,
    val error: TaskError? = null,
    val details: Details? = null
) {
    /**
     * Method to return the JSON String of the Task
     *
     * @return JSON string of the Task object
     */
    override fun toString(): String {
        return gsonTask.toJson(this)
    }

    companion object {
        private val gsonTask = Gson()
    }
}
