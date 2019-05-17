package com.balistos.balistos.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Playlist(
    val id: Double,
    // used to map img_src from the JSON to imgSrcUrl in our class
    val title: String,
    val description: String) : Parcelable