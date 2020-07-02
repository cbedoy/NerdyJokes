package iambedoy.jokes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import iambedoy.jokes.repository.JokeRepository
import iambedoy.jokes.response.JokeResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Random Jokes
 *
 * Created by bedoy on 01/07/20.
 */
class JokeViewModel (
    private val repository: JokeRepository
): ViewModel(){
    private val scope = CoroutineScope(Job() + Dispatchers.IO)

    private val _currentJoke =  MutableLiveData<JokeResponse>()

    val currentJoke : LiveData<JokeResponse>
        get() = _currentJoke


    fun takeRandomJoke() {
        scope.launch {
            val joke = repository.requestJoke()

            _currentJoke.postValue(joke)
        }
    }
}