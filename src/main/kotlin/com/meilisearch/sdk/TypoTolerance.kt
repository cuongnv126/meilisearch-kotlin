package com.meilisearch.sdk

import org.json.JSONObject

class TypoTolerance {
    private val enabled = true
    private val minWordSizeForTypos: HashMap<String?, Int?>? = null
    private val disableOnWords: Array<String> = arrayOf()
    private val disableOnAttributes: Array<String> = arrayOf()

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
