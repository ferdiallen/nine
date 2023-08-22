package com.example.nineintelligence

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.media3.common.util.UnstableApi
import androidx.navigation.compose.rememberNavController
import com.example.nineintelligence.domain.util.ExamType
import com.example.nineintelligence.navigation.RootNavigation
import com.example.nineintelligence.presentation.exam.ExamScreen
import com.example.nineintelligence.presentation.packagescreen.PackageScreen
import com.example.nineintelligence.presentation.profile.ProfileScreen
import com.example.nineintelligence.ui.theme.NineIntelligenceTheme

@UnstableApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NineIntelligenceTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                   RootNavigation()
                }
            }
        }
    }
}
