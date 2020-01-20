package com.eex.extensions

fun <T> List<T>.limit(limit: Int) = if (size > limit) subList(0, limit) else this