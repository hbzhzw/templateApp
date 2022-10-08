package io.github.hbzhzw.utils.bytespool

import androidx.core.util.Pools

object BytesPools {
//    private const val KB: Int  = 1024
    private const val TWO_KB: Int  = 2048
    private const val FOUR_KB: Int = 4096

    private val twoBytesPool: BytesPool by lazy {
        BytesPool(TWO_KB, 8)
    }

    private val fourBytesPool: BytesPool by lazy {
        BytesPool(FOUR_KB)
    }

    fun acquire(): ByteArray {
        return twoBytesPool.acquire()
    }

    fun release(byteArray: ByteArray?) {
        if (byteArray?.size == TWO_KB) {
            twoBytesPool.release(byteArray)
        }
    }

    fun acquire4Kb(): ByteArray {
        return fourBytesPool.acquire()
    }

    fun release4Kb(byteArray: ByteArray?) {
        if (byteArray?.size == FOUR_KB) {
            fourBytesPool.release(byteArray)
        }
    }

    internal class BytesPool(val size: Int, capacity: Int = 4)
        : Pools.SynchronizedPool<ByteArray>(capacity) {
        override fun acquire(): ByteArray {
            return super.acquire() ?: ByteArray(size)
        }
    }
}