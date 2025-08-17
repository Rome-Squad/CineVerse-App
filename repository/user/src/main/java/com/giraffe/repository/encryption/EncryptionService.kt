package com.giraffe.repository.encryption


import com.giraffe.repository.utils.mapToDomainException
import com.giraffe.user.encryption.IEncryptionService
import com.giraffe.user.encryption.IKeyStoreService
import com.giraffe.user.encryption.ISecretKeyAliasEnum
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec

class EncryptionService(
    private val keyStoreService: IKeyStoreService
) : IEncryptionService {


    override fun encrypt(keyAlias: ISecretKeyAliasEnum, data: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(keyStoreService.transformation)
        cipher.init(Cipher.ENCRYPT_MODE, keyStoreService.getSecretKey(keyAlias.keyAlias))
        val iv = cipher.iv
        val encryptedData = cipher.doFinal(data)
        return iv + encryptedData
    }

    override fun decrypt(keyAlias: ISecretKeyAliasEnum, data: ByteArray): ByteArray {
        return try {
            val cipher = Cipher.getInstance(keyStoreService.transformation)
            val iv = data.copyOfRange(0, cipher.blockSize)
            val encryptedData = data.copyOfRange(cipher.blockSize, data.size)
            cipher.init(
                Cipher.DECRYPT_MODE,
                keyStoreService.getSecretKey(keyAlias.keyAlias),
                IvParameterSpec(iv)
            )
            cipher.doFinal(encryptedData)
        } catch (e: Exception) {
            throw mapToDomainException(e)
        }
    }

    override fun isSecretKeyExist(keyAlias: ISecretKeyAliasEnum): Boolean {
        return keyStoreService.isSecretKeyExist(keyAlias.keyAlias)
    }

    override fun deleteSecretKey(keyAlias: ISecretKeyAliasEnum) {
        keyStoreService.deleteSecretKey(keyAlias.keyAlias)
    }
}