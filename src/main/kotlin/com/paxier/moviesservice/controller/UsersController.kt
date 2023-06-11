package com.paxier.moviesservice.controller

import com.paxier.moviesservice.client.UsersClient
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/movies-users")
class UsersController(
    val usersClient: UsersClient
) {

    @Cacheable("userCache")
    @GetMapping("/all")
   fun getUsers(): Mono<Any> {
        return usersClient.getAllUsers()
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Int): Mono<Any> {
        return usersClient.getUsersById(id)
    }

    @GetMapping("/error")
    fun getError(): Mono<Any> {
        return usersClient.getError()
    }
}
