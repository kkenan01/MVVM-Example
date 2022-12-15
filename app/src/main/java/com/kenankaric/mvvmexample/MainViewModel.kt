package com.kenankaric.mvvmexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    /**
     * Using mutable live data we're (as the name state) mutating the state (duh!)
     * which in our case is of type [String].
     *
     * It's important to keep such variables private since we don't want variables that mutate state
     * to leave the scope of the view model, or in any way leak into the UI.
     */
    private val _name: MutableLiveData<String> = MutableLiveData("")

    /**
     * We are exposing to the UI a **read only** [LiveData] which will contain our value
     * which we get by grabbing it from our mutating variable above.
     */
    val name: LiveData<String> = _name

    /**
     * In order to mutate the state (which will be observed in the UI),
     * we're exposing public method which has access to our mutable live data.
     *
     * This is okay, since user/UI can only access/change the value here, ie. still not directly.
     *
     * Value is dispatched on the main thread.
     */
    fun updateName(newName: String) {
        _name.postValue(newName)
    }
}