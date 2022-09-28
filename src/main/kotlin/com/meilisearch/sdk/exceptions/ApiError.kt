package com.meilisearch.sdk.exceptions

import java.io.Serializable

/** This is class wraps errors sent by Meilisearch API  */
class ApiError(
    val message: String,
    val errorCode: String,
    val errorType: String,
    val errorLink: String
) : Serializable {

    override fun toString(): String {
        return ("ApiError{"
            + "message='"
            + message
            + '\''
            + ", code='"
            + errorCode
            + '\''
            + ", type='"
            + errorType
            + '\''
            + ", link='"
            + errorLink
            + '\''
            + '}')
    }

    companion object {
        private const val serialVersionUID = 900737636809105793L
    }
}
