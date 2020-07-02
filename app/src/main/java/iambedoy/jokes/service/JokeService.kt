package iambedoy.jokes.service

import iambedoy.jokes.response.JokeResponse
import retrofit2.http.GET

/**
 * Random Jokes
 *
 * Created by bedoy on 01/07/20.
 */
interface JokeService {
    @GET("random_joke")
    suspend fun getRandom() : JokeResponse
}