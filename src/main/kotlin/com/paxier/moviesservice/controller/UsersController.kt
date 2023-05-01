package com.paxier.moviesservice.controller

import com.paxier.moviesservice.client.UsersClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/movies-users")
class UsersController(
    val usersClient: UsersClient
) {
    @GetMapping("/all")
   fun getUsers(): Mono<Any> {
        return usersClient.getAllUsers()
    }
}
