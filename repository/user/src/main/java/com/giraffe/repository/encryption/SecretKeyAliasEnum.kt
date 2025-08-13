package com.giraffe.repository.encryption

import com.giraffe.user.encryption.ISecretKeyAliasEnum

enum class SecretKeyAliasEnum(override val keyAlias: String) : ISecretKeyAliasEnum {
    SESSION_ID("encryption_session_id")
}