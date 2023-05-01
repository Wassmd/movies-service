package com.paxier.moviesservice.downstream

import org.springframework.http.HttpMethod

data class ServiceEndpoint (
    val method: HttpMethod,
    val baseUrl: String,
    val path: String,
    val query: String?
    )
