package com.meilisearch.sdk.exceptions

class MeiliSearchApiException(
    private val error: ApiError?
) : MeiliSearchRuntimeException() {

    override val message = error?.message
    val errorCode = error?.errorCode
    val errorType = error?.errorType
    val errorLink = error?.errorLink

    override fun toString(): String {
        return "MeiliSearchApiException{error=$error}"
    }
}
