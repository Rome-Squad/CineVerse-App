package com.giraffe.presentation.profile.utils

import android.net.Uri
import android.webkit.ValueCallback

object FilePicker {
    var filePathCallback: ValueCallback<Array<Uri>>? = null
    var openFileChooser: (() -> Unit)? = null
}