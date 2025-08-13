package com.giraffe.user.encryption


interface IEncryptionService {
    fun encrypt(keyAlias: ISecretKeyAliasEnum, data: ByteArray): ByteArray
    fun decrypt(keyAlias: ISecretKeyAliasEnum, data: ByteArray): ByteArray
    fun isSecretKeyExist(keyAlias: ISecretKeyAliasEnum): Boolean
    fun deleteSecretKey(keyAlias: ISecretKeyAliasEnum)
}