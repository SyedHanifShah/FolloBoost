package com.hanisoft.bestar

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hanisoft.bestar.domain.repository.BeStarRepository
import com.hanisoft.bestar.ui.MainScreen
import com.hanisoft.bestar.ui.home.HomeViewModel
import com.hanisoft.bestar.ui.theme.BeStarTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val vm :HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            lifecycle.addObserver(vm)
            BeStarTheme {
           MainScreen()
            }
        }

    }

}

