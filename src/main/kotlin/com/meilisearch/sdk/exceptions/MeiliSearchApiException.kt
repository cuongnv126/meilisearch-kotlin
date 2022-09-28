package com.meilisearch.sdk.exceptions

class MeiliSearchApiException(private val error: ApiError?) : MeiliSearchRuntimeException() {
    override val message: String? get() = error?.message
    val errorCode: String? get() = error?.errorCode
    val errorType: String? get() = error?.errorType
    val errorLink: String? get() = error?.errorLink

    override fun toString(): String {
        return "MeiliSearchApiException{error=$error}"
    }
}
