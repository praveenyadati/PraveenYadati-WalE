package com.walmart.test.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class NasaResponse(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val copyright: String,
    val date: String,
    val explanation: String,
    val hdurl: String,
    val title: String,
    val url: String,
    var notAvailable : Boolean = false
) : Parcelable

    