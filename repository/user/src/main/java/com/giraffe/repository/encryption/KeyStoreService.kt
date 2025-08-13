package com.giraffe.repository.encryption


import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.giraffe.user.encryption.IKeyStoreService
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class KeyStoreService : IKeyStoreService {

    override val transformation = "$ALGORITHM/$BLOCK_MODE/$PADDING"

    private val keyStore = KeyStore.getInstance(PROVIDER).apply { load(null) }

    override fun getSecretKey(keyAlias: String): SecretKey {
        val key = keyStore.getEntry(keyAlias, null) as? KeyStore.SecretKeyEntry
        return key?.secretKey ?: generateSecretKey(keyAlias)
    }

    private fun generateSecretKey(keyAlias: String): SecretKey {
        return KeyGenerator.getInstance(ALGORITHM, PROVIDER).apply {
            init(getKeyGenParameterSpec(keyAlias))
        }.generateKey()
    }

    private fun getKeyGenParameterSpec(keyAlias: String): KeyGenParameterSpec {
        return KeyGenParameterSpec.Builder(
            keyAlias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).apply {
            setKeySize(KEY_SIZE)
            setBlockModes(BLOCK_MODE)
            setEncryptionPaddings(PADDING)
            setRandomizedEncryptionRequired(true)
        }.build()
    }

    override fun isSecretKeyExist(keyAlias: String): Boolean {
        return keyStore.containsAlias(keyAlias)
    }

    override fun deleteSecretKey(keyAlias: String) {
        if (isSecretKeyExist(keyAlias)) keyStore.deleteEntry(keyAlias)
    }

    companion object {
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val PROVIDER = "AndroidKeyStore"
        private const val KEY_SIZE = 128
    }
}