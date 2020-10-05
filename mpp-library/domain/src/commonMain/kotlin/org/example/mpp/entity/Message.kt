package org.example.mpp.entity

data class Message(
    val author: String,
    val message: String,
    val image: String,
    val isRight: Boolean,
    val id: Long
)