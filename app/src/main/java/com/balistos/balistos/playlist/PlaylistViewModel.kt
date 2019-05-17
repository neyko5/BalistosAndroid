package com.balistos.balistos.playlist

/*
 *  Copyright 2018, The Android Open Source Project
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.balistos.balistos.network.Playlist

/**
 *  The [ViewModel] associated with the [DetailFragment], containing information about the selected
 *  [MarsProperty].
 */
class PlaylistViewModel(playlist: Playlist, app: Application) : AndroidViewModel(app) {
    private val _selectedPlaylist = MutableLiveData<Playlist>()

    // The external LiveData for the SelectedProperty
    val selectedPlaylist: LiveData<Playlist>
        get() = _selectedPlaylist

    // Initialize the _selectedProperty MutableLiveData
    init {
        _selectedPlaylist.value = playlist
    }

}
