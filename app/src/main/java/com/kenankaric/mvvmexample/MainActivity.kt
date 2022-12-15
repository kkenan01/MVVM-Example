package com.kenankaric.mvvmexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.kenankaric.mvvmexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    /**
     * Using [ActivityMainBinding] we're providing safe access to our UI components
     * by view binding.
     */
    private lateinit var binding: ActivityMainBinding

    /**
     * We are lazily injecting our [MainViewModel].
     * Meaning, it'll only be called when it's actually needed, so it doesn't waste resources.
     */
    private val viewModel: MainViewModel by viewModels()

    companion object {
        /**
         * This is for consistency. It clearly indicates that our message on the UI will either
         * contain name or not. It makes code easier to read.
         */
        private const val EMPTY_NAME = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * Here we're inflating our layout.
         * [binding] magically knows which layout belongs to which activity/fragment.
         */
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        updateGreetingMessage()
    }

    private fun setupUI() {
        /**
         * We set initial user message to be rendered on the screen.
         * We do this because our string [greeting_message] contains placeholder for the name.
         *
         * Therefore we need to provide format arguments to [getString] method
         * (we provide a const value [EMPTY_NAME]).
         *
         * We could use empty string directly, like so >> getString(R.string.greeting_message, "").
         * However this is for both consistency and clarity sake. So we're either passing
         * name or not to the string as a format argument.
         */
        binding.lblGreetingMessage.text = getString(R.string.greeting_message, EMPTY_NAME)

        /**
         * We collect value from the [MainViewModel], which we made to be **read-only** observable.
         * This is important because we want data to flow in one direction, ie. from [MainViewModel] to UI.
         *
         * We then update our [greeting_message] with format argument of our newly written name.
         *
         * We say that our lifecycle owner is [MainActivity]. Once that is destroyed,
         * we'll stop observing the data.
         */
        viewModel.name.observe(this@MainActivity) { name ->
            binding.lblGreetingMessage.text = getString(R.string.greeting_message, name)
        }
    }

    /**
     * Here we're listening to any change that is happening to our input field,
     * making it send data to the [MainViewModel] to update the state,
     * which we're observing/listening above.
     */
    private fun updateGreetingMessage() {
        binding.etName.addTextChangedListener { text: Editable? ->
            viewModel.updateName(text.toString())
        }
    }
}