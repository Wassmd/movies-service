package com.paxier.moviesservice.header

import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.MediaType
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.util.UUID

val DEVICE_ID_CONTEXT_KEY = "${DeviceIdWebFilter::class.java.packageName}.DEVICE_ID_CONTEXT_KEY"

class DeviceIdWebFilter(private val headerName: String): WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val deviceId = exchange.request.headers[headerName]?.first()?.let { headerValue ->
            if (isValid(headerValue)) {
                headerValue
            } else {
                val message = "device should be a valid UUID"
                val response = exchange.response
                response.statusCode = BAD_REQUEST
                response.headers.contentType = MediaType.APPLICATION_JSON

                return response
                    .setComplete()
            }
        }

        return chain.filter(exchange)
            .contextWrite { ctx -> deviceId?.let { ctx.put(DEVICE_ID_CONTEXT_KEY, deviceId) } ?: ctx }
    }

    private fun isValid(headerValue: String): Boolean {
        return UUID.fromString(headerValue) != null
    }

    companion object {
        const val EXTERNAL_HEADER = "deviceId"
        const val INTERNAL_HEADER = "x-device-id"
    }
}