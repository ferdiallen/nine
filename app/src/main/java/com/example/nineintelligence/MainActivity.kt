package com.example.nineintelligence

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import androidx.navigation.compose.rememberNavController
import com.example.nineintelligence.domain.util.ExamType
import com.example.nineintelligence.navigation.RootNavigation
import com.example.nineintelligence.presentation.discuss.DiscussionScreen
import com.example.nineintelligence.presentation.dummy.NotificationTestScreen
import com.example.nineintelligence.presentation.enter.LoginForm
import com.example.nineintelligence.presentation.exam.ExamScreen
import com.example.nineintelligence.presentation.profile.ProfileScreen
import com.example.nineintelligence.presentation.tryout.TryoutScreen
import com.example.nineintelligence.ui.theme.NineIntelligenceTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

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
                    /*RootNavigation()*/
                    ProfileScreen(
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp), onBackPress = {

                        }, onLogoutAction = {

                        }
                    )
                    /*NotificationTestScreen(
                        modifier = Modifier.fillMaxSize()
                    )*/
                    /*TryoutScreen()*/
                    /*ExamScreen(controller = rememberNavController(), typeOf = ExamType.TAKE_EXAMS)*/

                    /*DiscussionScreen(
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp),
                        subjectName = "Computer Science",
                        bankSoalOf = 1, typeOf = "Bank Soal", controller = rememberNavController()
                    )*/
                }
            }
        }
    }
}
