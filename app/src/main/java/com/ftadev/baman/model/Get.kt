package com.ftadev.baman.model

data class SampleList(
    val code: Int,
    val data: Data
)

data class Data(
    val id: String,
    val list: List<Item>,
    val title: String
)

data class Item(
    val createDate: Long,
    val description: String,
    val id: String,
    val imageUrl: String,
    val name: String,
    val shareUrl: String?
)

data class SampleItem(
    val code: Int,
    val data: Item
)
