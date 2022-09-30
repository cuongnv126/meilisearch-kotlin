//package com.meilisearch.sdk
//
//import com.meilisearch.sdk.exceptions.ApiError
//import com.meilisearch.sdk.exceptions.MeiliSearchApiException
//import com.meilisearch.sdk.exceptions.MeiliSearchRuntimeException
//import com.meilisearch.sdk.http.AbstractHttpClient
//import com.meilisearch.sdk.http.factory.RequestFactory
//import com.meilisearch.sdk.http.request.HttpMethod
//import com.meilisearch.sdk.http.request.HttpRequest
//import com.meilisearch.sdk.http.response.HttpResponse
//import com.meilisearch.sdk.json.JsonHandler
//
//class GenericServiceTemplate(
//    override val client: AbstractHttpClient,
//    override val processor: JsonHandler,
//    override val requestFactory: RequestFactory
//) : ServiceTemplate {
//
//    override fun <T> execute(request: HttpRequest<*>, targetClass: Class<*>?, vararg parameter: Class<*>): T? {
//        if (targetClass == null) {
//            return makeRequest(request) as? T?
//        }
//        val response = makeRequest(request)
//        if (response.statusCode >= 400) {
//            val error = decodeResponse<ApiError>(response.content, ApiError::class.java)!!
//            throw MeiliSearchRuntimeException(MeiliSearchApiException(error))
//        }
//        return decodeResponse<T>(response.content, targetClass, *parameter)
//    }
//
//    private fun <T> decodeResponse(o: Any?, targetClass: Class<*>, vararg parameters: Class<*>): T? {
//        return try {
//            processor.decode(o, targetClass, *parameters)
//        } catch (e: Exception) {
//            throw MeiliSearchRuntimeException(e)
//        }
//    }
//
//    /**
//     * Executes the given [HttpRequest]
//     *
//     * @param request the [HttpRequest]
//     * @return the HttpResponse
//     * @throws MeiliSearchRuntimeException in case there are Problems with the Request, including
//     * error codes
//     */
//    private suspend fun makeRequest(request: HttpRequest<*>): HttpResponse<*> {
//        return try {
//            when (request.method) {
//                HttpMethod.GET -> client.get(request)
//                HttpMethod.POST -> client.post(request)
//                HttpMethod.PUT -> client.put(request)
//                HttpMethod.DELETE -> client.delete(request)
//                else -> throw IllegalStateException("Unexpected value: " + request.method)
//            }
//        } catch (e: Exception) {
//            throw MeiliSearchRuntimeException(e)
//        }
//    }
//}
