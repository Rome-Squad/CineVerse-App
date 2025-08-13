package com.giraffe.user.encryption


import javax.crypto.SecretKey

interface IKeyStoreService {
    val transformation: String
    fun getSecretKey(keyAlias: String): SecretKey
    fun isSecretKeyExist(keyAlias: String): Boolean
    fun deleteSecretKey(keyAlias: String)
}