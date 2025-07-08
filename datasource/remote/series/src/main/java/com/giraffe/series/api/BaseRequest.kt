package com.giraffe.series.api

import io.ktor.http.HttpMethod
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


data class BaseRequest(
    val baseURL: String
) {
    var endPoint: String = ""
    var method: HttpMethod = HttpMethod.Companion.Get
    var headers: Map<String, String> = emptyMap()
    var parameters: Map<String, Any> = emptyMap()
    var body: Any? = null

    fun endpoint(endpoint: String) = apply { this.endPoint = endpoint }

    fun method(method: HttpMethod) = apply { this.method = method }

    fun parameters(params: Map<String, Any>) = apply { this.parameters = params }

    fun addParameter(key: String, value: Any) = apply {
        this.parameters = this.parameters + (key to value)
    }

    fun headers(headers: Map<String, String>) = apply { this.headers = headers }

    fun addHeader(key: String, value: String) = apply {
        this.headers = this.headers + (key to value)
    }

    fun body(body: Any?) = apply { this.body = body }

    fun buildFullUrl(): String {
        val fullUrl = if (endPoint.isEmpty()) baseURL else "$baseURL$endPoint"

        return if (parameters.isNotEmpty() && method == HttpMethod.Companion.Get) {
            val query = parameters.map { (k, v) -> "${k.encode()}=${v.toString().encode()}" }
                .joinToString("&")
            "$fullUrl?$query"
        } else {
            fullUrl
        }
    }

    fun validate(): Boolean {
        return baseURL.isNotEmpty() &&
                baseURL.startsWith("http")
    }
}

private fun String.encode(): String = URLEncoder.encode(this, StandardCharsets.UTF_8.toString())
