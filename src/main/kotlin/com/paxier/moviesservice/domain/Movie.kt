package com.paxier.moviesservice.domain

data class Movie (
    val moviesInfo: MovieInfo,
    val reviews: List<Review>
)
