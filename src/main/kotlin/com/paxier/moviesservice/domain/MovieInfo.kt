package com.paxier.moviesservice.domain

import java.time.LocalDate

data class MovieInfo(
    val id: String,
    val name: String,
    val year: Int,
    val cast: List<String>,
    val releaseDate: LocalDate
)
