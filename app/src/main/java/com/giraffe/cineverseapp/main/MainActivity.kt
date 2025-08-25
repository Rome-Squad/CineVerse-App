package com.giraffe.cineverseapp.main

import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.giraffe.api.authentication.AuthenticationApi
import com.giraffe.api.home.HomeApi
import com.giraffe.presentation.profile.utils.FilePicker
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var authenticationApi: AuthenticationApi

    @Inject lateinit var homeApi: HomeApi

    private val mainViewModel: MainViewModel by viewModels()

    private val fileChooserLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            FilePicker.filePathCallback?.onReceiveValue(uri?.let { arrayOf(it) } ?: arrayOf())
            FilePicker.filePathCallback = null
        }

    override fun onCreate(savedInstanceState: Bundle?) {

        FilePicker.openFileChooser = {
            fileChooserLauncher.launch("image/*")
        }

        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition { true }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.splashState.collectLatest { splash ->
                    if (!splash.keepSplashVisible) {
                        splashScreen.setKeepOnScreenCondition { false }
                        return@collectLatest
                    }
                }
            }
        }


        setContent {
            val state by mainViewModel.state.collectAsState()

            if (state.isLoggedIn == true) {
                homeApi.launchHome(this)
            }
            CineVerseRoot(
                enableEdgeToEdge = ::enableEdgeToEdge,
                state = state,
                authenticationApi = authenticationApi
            )
        }
    }
}