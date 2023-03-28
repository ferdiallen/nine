package com.example.nineintelligence

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import androidx.navigation.compose.rememberNavController
import com.example.nineintelligence.navigation.RootNavigation
import com.example.nineintelligence.presentation.profile.ProfileScreen
import com.example.nineintelligence.presentation.tryout.TryoutInformation
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
                    /*GreetingReadingSubject(
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )*/
                    /*MainReadingSubject(
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )*/
                    RootNavigation()
                    /*ProfileScreen(
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp), onBackPress = {

                        }, onLogoutAction = {

                        }
                    )*/
                    /*NotificationTestScreen(
                        modifier = Modifier.fillMaxSize()
                    )*/
                    /*TryoutScreen()*/
                    /*ExamScreen(controller = rememberNavController(), typeOf = ExamType.TAKE_EXAMS)*/
                    /*TryoutInformation(
                        controller = rememberNavController(),
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )*/
                }
            }
        }
    }
}
