package iambedoy.jokes

import android.app.Application
import iambedoy.jokes.module.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Random Jokes
 *
 * Created by bedoy on 01/07/20.
 */
class JokeApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@JokeApplication)
            modules(appModule)
        }
    }
}