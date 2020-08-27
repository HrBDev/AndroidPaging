package com.ftadev.androidpaging

fun decreaseSize(url: String): String {
    val urlSplit: MutableList<String> = url.split("/").toMutableList()
    var newUrl = ""
    urlSplit.removeAt(urlSplit.size - 1)
    urlSplit.removeAt(urlSplit.size - 1)
    for(e in urlSplit)
        newUrl += "$e/"
    newUrl += "600/600"
    return newUrl
}