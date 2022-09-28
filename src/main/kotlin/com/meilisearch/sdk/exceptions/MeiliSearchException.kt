package com.meilisearch.sdk.exceptions

open class MeiliSearchException(message: String?) : Exception(message) {
    var errorType: String? = null
    var errorCode: String? = null
    var errorLink: String? = null

    override fun toString(): String {
        return this.javaClass.name + ". Error message: " + message + "."
    }
}
