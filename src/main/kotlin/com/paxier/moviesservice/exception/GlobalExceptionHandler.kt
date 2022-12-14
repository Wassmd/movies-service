package com.paxier.moviesservice.exception

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class GlobalExceptionHandler(val objectMapper: ObjectMapper): ErrorWebExceptionHandler {
    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        val response = exchange.response

        if (ex is MovieInfoClientException) {
            response.statusCode = HttpStatus.NOT_FOUND
        }

        if (ex is MovieInfoClientException) {
            response.statusCode = HttpStatus.INTERNAL_SERVER_ERROR
        }

        val buffer = response.bufferFactory().wrap(objectMapper.writeValueAsBytes(ex.message))

        return exchange.response.writeWith(Mono.just(buffer))
    }
}