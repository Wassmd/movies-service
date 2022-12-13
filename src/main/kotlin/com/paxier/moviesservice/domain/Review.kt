package com.paxier.moviesservice.domain

data class Review(
    val id: String,
    val movieInfoId: String,
    val comment: String,
    val rating: Double
)