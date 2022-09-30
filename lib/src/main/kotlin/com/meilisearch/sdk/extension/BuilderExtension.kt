package com.meilisearch.sdk.extension

/**
 * Created by cuongnv on Sep 30, 2022
 */

object BuilderExtension {
    internal inline fun <reified T> T.self(block: T.() -> Unit): T {
        this.block()
        return this
    }
}
