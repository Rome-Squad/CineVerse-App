package com.giraffe.repository.utils

import android.util.Base64

internal fun ByteArray.base64Encode(): String {
    return Base64.encodeToString(this, Base64.NO_WRAP)
}

internal fun String.base64Decode(): ByteArray {
    return Base64.decode(this, Base64.NO_WRAP)
}