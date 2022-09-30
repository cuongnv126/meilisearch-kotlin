package com.meilisearch.sdk.model

import com.meilisearch.sdk.shared.SharedObject
import java.util.Date

class Task(
    val taskUid: Int,
    val indexUid: String = "",
    val status: String = "",
    val type: String? = null,
    val duration: String = "",
    val enqueuedAt: String? = null,
    val startedAt: String? = null,
    val finishedAt: String? = null,
    val error: TaskError? = null,
    val details: Details? = null
) {
    /**
     * Method to return the JSON String of the Task
     *
     * @return JSON string of the Task object
     */
    override fun toString(): String {
        return SharedObject.gson.toJson(this)
    }
}
