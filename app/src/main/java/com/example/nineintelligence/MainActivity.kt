package com.example.nineintelligence

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.compose.rememberNavController
import com.example.nineintelligence.navigation.RootNavigation
import com.example.nineintelligence.presentation.enter.RegisterScreen
import com.example.nineintelligence.presentation.exam.DialogIsOver
import com.example.nineintelligence.presentation.exam.ExamScreen
import com.example.nineintelligence.presentation.profile.ProfileScreen
import com.example.nineintelligence.ui.theme.NineIntelligenceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NineIntelligenceTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    ExamScreen(controller = rememberNavController())
                }
            }
        }
    }
}
