package com.meilisearch.sdk.model

import org.json.JSONObject

class TypoTolerance(
    val enabled: Boolean = true,
    val minWordSizeForTypos: HashMap<String?, Int?>? = null,
    val disableOnWords: Array<String>? = null,
    val disableOnAttributes: Array<String>? = null
) {
    /**
     * Method to return the JSONObject of the TypoTolerance Setting
     *
     * @return JSONObject of the TypoTolerance Setting object
     */
    fun toJson(): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("enabled", enabled)
        jsonObject.put("minWordSizeForTypos", minWordSizeForTypos)
        jsonObject.put("disableOnWords", disableOnWords)
        jsonObject.put("disableOnAttributes", disableOnAttributes)
        return jsonObject
    }
}
