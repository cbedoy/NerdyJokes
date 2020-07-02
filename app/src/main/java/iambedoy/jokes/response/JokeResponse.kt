package iambedoy.jokes.response

import com.squareup.moshi.JsonClass

/**
 * Random Jokes
 *
 * Created by bedoy on 01/07/20.
 */
@JsonClass(generateAdapter = true)
data class JokeResponse(
    val id: String = "",
    val type: String = "",
    val setup: String = "",
    val punchline: String = ""
)