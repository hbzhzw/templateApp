package io.github.hbzhzw.utils.common

class ListenerMgr<T> {
    private val mListeners: MutableList<T> = mutableListOf()

    fun addListener(listener: T?) {
        listener?.apply {
            synchronized(mListeners) {
                if (!contains(this)) {
                    mListeners.add(this)
                }
            }
        }
    }

    private fun contains(listener: T?): Boolean {
        return listener?.let {
            mListeners.contains(it)
        } ?: false
    }

    fun removeListener(listener: T?): Boolean {
        return listener?.let {
            synchronized(mListeners) {
                mListeners.remove(it)
            }
        } ?: false
    }

    fun addHeadListener(listener: T?) {
        listener?.apply {
            synchronized(mListeners) {
                if (contains(this)) {
                    mListeners.remove(this)
                }
                mListeners.add(0, this)
            }
        }
    }

    fun notifyListeners(block: ((T) -> Unit)) {
        synchronized(mListeners) {
            mListeners.forEach {
                block.invoke(it)
            }
        }
    }
}