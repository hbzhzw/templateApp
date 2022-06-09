package io.github.hbzhzw.utils.common

import java.lang.RuntimeException

object ObjectHelper {
    fun requireNotNull(obj: Any?, assertMsg: String?) {
        if (obj == null) {
            throw RuntimeException(assertMsg ?: "the argument should not be null!")
        }
    }

    fun equals(o1: Any?, o2: Any?): Boolean {
        if (o1 == o2) {
            return true
        }

        if (o1 == null && o2 != null
            || o1 != null && o2 == null
            || o1 != null && o2 != null && o1.javaClass != o2.javaClass) {
            return false
        } else {
            return o1 == o2
        }
    }
}