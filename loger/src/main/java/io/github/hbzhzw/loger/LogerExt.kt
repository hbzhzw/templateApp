package io.github.hbzhzw.loger

inline fun <T> T.logV(tag: String, block: T.() -> String?) {
    if (Loger.isDebug) {
        apply {
            Loger.v(tag, block.invoke(this))
        }
    }
}

inline fun <T> T.logD(tag: String, block: T.() -> String?) {
    if (Loger.isDebug) {
        apply {
            Loger.d(tag, block.invoke(this))
        }
    }
}

inline fun <T> T.logI(tag: String, block: T.() -> String?) {
    apply {
        Loger.i(tag, block.invoke(this))
    }
}

inline fun <T> T.logW(tag: String, block: T.() -> String?) {
    apply {
        Loger.w(tag, block.invoke(this))
    }
}

inline fun <T> T.logE(tag: String, block: T.() -> String?) {
    apply {
        Loger.e(tag, block.invoke(this))
    }
}

