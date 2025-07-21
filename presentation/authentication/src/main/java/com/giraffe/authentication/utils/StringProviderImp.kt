package com.giraffe.authentication.utils
import android.content.Context


class StringProviderImp (private val context: Context): StringProvider {
    override fun getString(resId: Int): String {
        return context.getString(resId)
    }
}