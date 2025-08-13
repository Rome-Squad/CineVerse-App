package com.giraffe.repository.encryption


import com.giraffe.user.encryption.IEncryptionService
import com.giraffe.user.encryption.IKeyStoreService
import com.giraffe.user.encryption.ISecretKeyAliasEnum
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec

class EncryptionService(
    private val keyStoreService: IKeyStoreService
) : IEncryptionService {

    private val cipher: Cipher = Cipher.getInstance(keyStoreService.transformation)

    override fun encrypt(keyAlias: ISecretKeyAliasEnum, data: ByteArray): ByteArray {
        cipher.init(Cipher.ENCRYPT_MODE, keyStoreService.getSecretKey(keyAlias.keyAlias))
        val iv = cipher.iv
        val encryptedData = cipher.doFinal(data)
        return iv + encryptedData
    }

    override fun decrypt(keyAlias: ISecretKeyAliasEnum, data: ByteArray): ByteArray {
        val iv = data.copyOfRange(0, cipher.blockSize)
        val encryptedData = data.copyOfRange(cipher.blockSize, data.size)
        cipher.init(
            Cipher.DECRYPT_MODE,
            keyStoreService.getSecretKey(keyAlias.keyAlias),
            IvParameterSpec(iv)
        )
        return cipher.doFinal(encryptedData)
    }

    override fun isSecretKeyExist(keyAlias: ISecretKeyAliasEnum): Boolean {
        return keyStoreService.isSecretKeyExist(keyAlias.keyAlias)
    }

    override fun deleteSecretKey(keyAlias: ISecretKeyAliasEnum) {
        keyStoreService.deleteSecretKey(keyAlias.keyAlias)
    }
}