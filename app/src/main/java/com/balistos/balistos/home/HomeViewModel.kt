package com.balistos.balistos.home

import android.util.Log
import com.balistos.balistos.network.BalistosApi
import com.balistos.balistos.network.Playlist

/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class BalistosApiStatus { LOADING, ERROR, DONE }
/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class HomeViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<BalistosApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<BalistosApiStatus>
        get() = _status

    // Internally, we use a MutableLiveData, because we will be updating the List of MarsProperty
    // with new values
    private val _playlists = MutableLiveData<List<Playlist>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val properties: LiveData<List<Playlist>>
        get() = _playlists

    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedPlaylist = MutableLiveData<Playlist>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedPlaylist: LiveData<Playlist>
        get() = _navigateToSelectedPlaylist

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getBalistosPlaylists()
    }

    /**
     * Gets filtered Mars real estate property information from the Mars API Retrofit service and
     * updates the [MarsProperty] [List] and [MarsApiStatus] [LiveData]. The Retrofit service
     * returns a coroutine Deferred, which we await to get the result of the transaction.
     * @param filter the [MarsApiFilter] that is sent as part of the web server request
     */
    private fun getBalistosPlaylists() {
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            var getPropertiesDeferred = BalistosApi.retrofitService.getPlaylists()
            try {
                _status.value = BalistosApiStatus.LOADING
                // this will run on a thread managed by Retrofit
                val listResult = getPropertiesDeferred.await()
                _status.value = BalistosApiStatus.DONE
                _playlists.value = listResult
            } catch (e: Exception) {
                _status.value = BalistosApiStatus.ERROR
                _playlists.value = ArrayList()
            }
        }
    }

    private fun searchBalistosPlaylists(filter: String) {
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            var getPropertiesDeferred = BalistosApi.retrofitService.searchPlaylists(filter)
            try {
                _status.value = BalistosApiStatus.LOADING
                // this will run on a thread managed by Retrofit
                val listResult = getPropertiesDeferred.await()
                _status.value = BalistosApiStatus.DONE
                _playlists.value = listResult
            } catch (e: Exception) {
                Log.e("RRR", e.toString())
                _status.value = BalistosApiStatus.ERROR
                _playlists.value = ArrayList()
            }
        }
    }

    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * When the property is clicked, set the [_navigateToSelectedProperty] [MutableLiveData]
     * @param marsProperty The [MarsProperty] that was clicked on.
     */
    fun displayPropertyDetails(playlist: Playlist) {
        _navigateToSelectedPlaylist.value = playlist
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     */
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedPlaylist.value = null
    }

    fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        Log.w("tag", "onTextChanged $s")
        searchBalistosPlaylists(s.toString())
    }

}