package iambedoy.jokes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import iambedoy.jokes.fragment.JokeFragment
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val jokeFragment : JokeFragment by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, jokeFragment)
            .commit()
    }
}
