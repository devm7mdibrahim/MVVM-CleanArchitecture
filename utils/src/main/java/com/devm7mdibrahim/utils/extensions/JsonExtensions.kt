package com.devm7mdibrahim.utils.extensions

import com.google.gson.Gson


fun <T> T.toJson(): String {
    return Gson().toJson(this)
}

inline fun <reified T : Any> String.fromJson(): T {
    return Gson().fromJson(this, T::class.java)
}

inline fun <reified T : Any> fromJsonToList(jsonString: String): List<T> {
    return Gson().fromJson(jsonString, emptyArray<T>().javaClass).toList()
}