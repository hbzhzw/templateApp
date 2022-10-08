package io.github.hbzhzw.utils.file

import io.github.hbzhzw.loger.logW
import java.io.*

object FileUtils {
    const val TAG = "FileUtils"
    fun readFileAsStr(filePath: String?): String? {
        return filePath?.let {
            File(it).readText(Charsets.UTF_8)
        }
    }

    fun writeFile(filePath: String, content: String?): Boolean {
        return content?.let {
            makeFile(filePath)?.run {
                writeText(it)
                true
            } == true
        } ?: false
    }

    fun writeObj(filePath: String, objData: Serializable?): Boolean {
        return if (objData != null) {
            makeFile(filePath)
        } else {
            null
        }?.let {
            var outputStream: OutputStream? = null
            try {
                outputStream = ObjectOutputStream(BufferedOutputStream(FileOutputStream(it)))
                outputStream.writeObject(objData)
                outputStream.flush()
            } catch (e: Exception) {
                logW(TAG) { "writeSerObj exception: $e" }
            } finally {
               closeStream(outputStream)
            }
            true
        } ?: false
    }

    inline fun <reified T> readObj(filePath: String?): T? {
        return filePath?.let {
            var resultObj: T? = null
            var inputStream: ObjectInputStream? = null
            try {
                inputStream = ObjectInputStream(BufferedInputStream(FileInputStream(filePath)))
                val obj = inputStream.readObject()
                resultObj = if (obj is T) {
                    obj
                } else {
                    null
                }
            } catch (e: Exception) {
                logW(TAG) { "readSerObj exception: $e" }
            } finally {
                closeStream(inputStream)
            }
            resultObj
        }
    }

    fun closeStream(stream: Closeable?) {
        stream?.apply {
            try {
                close()
            } catch (e: Exception) {
                logW(TAG) { "closeStream exception: $e" }
            }
        }
    }

    fun exist(filePath: String?): Boolean {
        return filePath?.let {
            File(it).exists()
        } ?: false
    }

    fun isDir(filePath: String?): Boolean {
        return filePath?.let {
            File(it).isDirectory
        } ?: false
    }

    fun isFile(filePath: String?): Boolean {
        return filePath?.let {
            File(it).isFile
        } ?: false
    }

    @Suppress("RedundantNullableReturnType")
    private fun makeFile(filePath: String): File? {
        val file = File(filePath)
        return file.parentFile?.let {
            if (it.exists()) {
                if (it.isDirectory) {
                    file
                } else {
                    null
                }
            } else {
                it.mkdirs()
                file
            }
        } ?: file
    }
}