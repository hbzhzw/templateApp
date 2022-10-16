package io.github.hbzhzw.utils.common

import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec

object CipherUtils {
    private const val DES = "DES"

    fun desEncrypt(data: ByteArray, passwd: String): ByteArray {
        val secretKey = generateSecreteKey(passwd)
        val cipher = Cipher.getInstance(DES).apply {
            init(Cipher.ENCRYPT_MODE, secretKey, SecureRandom())
        }
        return cipher.doFinal(data)
    }

    private fun generateSecreteKey(passwd: String): SecretKey {
        val specKey = DESKeySpec(passwd.toByteArray(Charsets.UTF_8))
        return SecretKeyFactory.getInstance(DES).generateSecret(specKey)
    }
}