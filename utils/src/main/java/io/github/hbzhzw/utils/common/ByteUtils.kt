package io.github.hbzhzw.utils.common

import io.github.hbzhzw.loger.logI
import java.lang.StringBuilder

object ByteUtils {
    private const val TAG = "ByteUtils"
    private val HEX_CHAR = listOf('0', '1', '2', '3', '4', '5', '6',
        '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

    fun bytesToStr(data: ByteArray?): String {
        return data?.let {
            val strBuilder = StringBuilder(it.size * 2)
            it.forEach {
                val value = it.toUByte().toInt()
                strBuilder.append(HEX_CHAR.get(value ushr 4) )
                strBuilder.append(HEX_CHAR.get(value and 0x0F) )
            }
            strBuilder.toString()
        } ?: ""
    }

    fun strToBytes(data: String?): ByteArray? {
        return data?.let {
            val byteArray = ByteArray(it.length / 2) { 0.toByte() }
            it.forEachIndexed { idx, c ->
                val arrayIdx = idx / 2
                val isFirstHalf = (idx % 2) == 0
                val value: Int = HEX_CHAR.indexOf(c)
                if (isFirstHalf) {
                    byteArray[arrayIdx] = (value shl 4).toByte()
                } else {
                    byteArray[arrayIdx] = (byteArray[arrayIdx].toInt() or value).toByte()
                }
            }
            byteArray
        }
    }
}