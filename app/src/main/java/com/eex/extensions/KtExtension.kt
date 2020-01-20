package com.eex.extensions

inline fun <T> T?.ifNull(generator: () -> T) : T{
  return this ?: generator()
}