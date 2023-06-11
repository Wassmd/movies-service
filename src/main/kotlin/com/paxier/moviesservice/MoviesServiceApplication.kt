package com.paxier.moviesservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class MoviesServiceApplication

fun main(args: Array<String>) {
	runApplication<MoviesServiceApplication>(*args)
}
