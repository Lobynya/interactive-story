package org.example.mpp.entity

import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize

@Parcelize
data class Content(
    val title: String,
    val id: Int
) : Parcelable