package com.meilisearch.sdk.model

import java.util.Date

/**
 * The option you want to pass for generate a Tenant Token
 */
class TenantTokenOptions(
    val apiKey: String? = null,
    val expiresAt: Date? = null
)
