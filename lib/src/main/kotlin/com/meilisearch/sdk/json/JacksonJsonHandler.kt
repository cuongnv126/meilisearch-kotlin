//package com.meilisearch.sdk.json
//
//import com.fasterxml.jackson.core.JsonProcessingException
//import com.fasterxml.jackson.databind.DeserializationFeature
//import com.fasterxml.jackson.databind.ObjectMapper
//import java.io.IOException
//
//class JacksonJsonHandler : JsonHandler {
//    private val mapper: ObjectMapper
//
//    /**
//     * this constructor uses a default ObjectMapper with enabled 'FAIL_ON_UNKNOWN_PROPERTIES'
//     * feature.
//     */
//    constructor() {
//        mapper = ObjectMapper()
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//    }
//
//    /** @param mapper ObjectMapper
//     */
//    constructor(mapper: ObjectMapper) {
//        this.mapper = mapper
//    }
//
//    /** {@inheritDoc}  */
//    override fun encode(o: Any?): String? {
//        return if (o?.javaClass == String::class.java) {
//            o as String?
//        } else try {
//            mapper.writeValueAsString(o)
//        } catch (e: JsonProcessingException) {
//            // todo: use dedicated exception
//            throw RuntimeException("Error while serializing: ", e)
//        }
//    }
//
//    /** {@inheritDoc}  */
//    override fun <T> decode(o: Any?, targetClass: Class<*>, vararg parameters: Class<*>?): T {
//        if (o == null) {
//            // todo: use dedicated exception
//            throw RuntimeException("String to deserialize is null")
//        }
//        return if (targetClass == String::class.java) {
//            o as T
//        } else try {
//            if (parameters.isEmpty()) {
//                mapper.readValue(o as String?, targetClass) as T
//            } else {
//                mapper.readValue(
//                    o as String?,
//                    mapper.typeFactory.constructParametricType(targetClass, *parameters)
//                )
//            }
//        } catch (e: IOException) {
//            // todo: use dedicated exception
//            throw RuntimeException("Error while serializing: ", e)
//        }
//    }
//}
