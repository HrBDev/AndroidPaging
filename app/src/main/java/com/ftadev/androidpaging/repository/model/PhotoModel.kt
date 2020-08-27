package com.ftadev.androidpaging.repository.model

class PhotoList : ArrayList<PhotoItem>()

data class PhotoItem(
    val author: String,
    val download_url: String,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)