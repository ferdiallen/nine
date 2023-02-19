package com.example.nineintelligence

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.view.WindowCompat
import com.example.nineintelligence.navigation.RootNavigation
import com.example.nineintelligence.presentation.enter.RegisterScreen
import com.example.nineintelligence.presentation.home.HomeScreen
import com.example.nineintelligence.presentation.profile.ProfileEdit
import com.example.nineintelligence.presentation.profile.ProfileScreen
import com.example.nineintelligence.ui.theme.NineIntelligenceTheme
import com.example.nineintelligence.ui.theme.Poppins

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            NineIntelligenceTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    var currentDialogSize by remember {
                        mutableStateOf(0.dp)
                    }
                    val density = LocalDensity.current
                    /*ProfileScreen(
                        Modifier
                            .fillMaxSize()
                            .padding()
                            .padding(horizontal = 20.dp)
                    )*/
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .statusBarsPadding()
                            .navigationBarsPadding()
                            .imePadding()
                            .onSizeChanged { out ->
                                with(density) {
                                    currentDialogSize = out.height.toDp()
                                }
                            }
                    ) {
                        Dialog(
                            onDismissRequest = {}
                        ) {
                            ProfileEdit(
                                Modifier
                                    .fillMaxWidth()
                                    .heightIn(0.dp, currentDialogSize),
                                onSaved = {},
                                Poppins.fonts
                            )
                        }
                    }
                }
            }
        }
    }
}
