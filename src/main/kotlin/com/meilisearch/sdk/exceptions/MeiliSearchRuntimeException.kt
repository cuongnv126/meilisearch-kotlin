package com.meilisearch.sdk.exceptions

open class MeiliSearchRuntimeException : RuntimeException {
    constructor() {}
    constructor(e: Exception?) : super(e) {}
    constructor(cause: Throwable?) : super(cause) {}
}
