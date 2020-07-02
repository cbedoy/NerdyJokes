package iambedoy.jokes.module

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import iambedoy.jokes.fragment.JokeFragment
import iambedoy.jokes.repository.JokeRepository
import iambedoy.jokes.service.JokeService
import iambedoy.jokes.viewmodel.JokeViewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Random Jokes
 *
 * Created by bedoy on 01/07/20.
 */

private val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
    .baseUrl("https://official-joke-api.appspot.com/")
    .build()

val fragment = module {
    factory {
        JokeFragment()
    }
}

val viewModel = module {
    factory {
        JokeViewModel(get())
    }
}

val repository = module {
    single {
        JokeRepository(get())
    }
}

val service = module {
    single {
        retrofit.create(JokeService::class.java)
    }
}

val appModule = fragment + viewModel + repository + service