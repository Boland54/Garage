package org.wit.garage.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GarageModel(var id: Long = 0,
                          var make: String = "",
                          var model: String = "",
                          var year:String= "",
                          var litre: String ="",
                          var doors:String = "",
                          var image: String = "",
                          var lat : Double = 0.0,
                          var lng: Double = 0.0,
                          var zoom: Float = 0f) : Parcelable
@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable