package iambedoy.jokes.repository

import iambedoy.jokes.response.JokeResponse
import iambedoy.jokes.service.JokeService

/**
 * Random Jokes
 *
 * Created by bedoy on 01/07/20.
 */
class JokeRepository (
    private val service: JokeService
) {
    suspend fun requestJoke(): JokeResponse {
        return service.getRandom()
    }

}