package com.example.nineintelligence

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
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.domain.util.ExamType
import com.example.nineintelligence.navigation.RootNavigation
import com.example.nineintelligence.presentation.discuss.DiscussionScreen
import com.example.nineintelligence.presentation.dummy.SubmitTest
import com.example.nineintelligence.presentation.enter.LoginForm
import com.example.nineintelligence.presentation.enter.RegisterScreen
import com.example.nineintelligence.presentation.exam.ExamScreen
import com.example.nineintelligence.presentation.home.HomeScreen
import com.example.nineintelligence.presentation.profile.ProfileScreen
import com.example.nineintelligence.ui.theme.NineIntelligenceTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.android.ext.android.get

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
                    /*SubmitTest()*/
                    /*GreetingReadingSubject(
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )*/
                    /*MainReadingSubject(
                        modifier = Modifier.padding(horizontal = 12.dp) 
                    )*/
                     RootNavigation()
                    /*RegisterScreen(controller = rememberNavController())*/
                    /*ProfileScreen(
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp), onBackPress = {

                        }, onLogoutAction = {

                        }, controller = rememberNavController()
                    )*/
                    /*NotificationTestScreen(
                        modifier = Modifier.fillMaxSize()
                    )*/
                    /*TryoutScreen(
                        controller = rememberNavController()
                    )*/
                    /*ExamScreen(controller = rememberNavController(), typeOf = ExamType.TAKE_EXAMS)*/
                    /*TryoutInformation(
                        controller = rememberNavController(),
                        modifier = Modifier.padding(horizontal = 12.dp),
                        slugname = "testing-broww"
                    )*/
                    /*ExamScreen(
                        controller = rememberNavController(), modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 22.dp),
                        typeOf = ExamType.TAKE_EXAMS, slugName = "tryout-testing-1", time = 60
                    )*/
                    /*BankSoal(controller = rememberNavController(), modifier = Modifier.fillMaxSize())*/
                    /*HomeScreen(
                        systemUi = rememberSystemUiController(),
                        rootController = rememberNavController()
                    )*/
                   /* DiscussionScreen(typeOf = "", controller = rememberNavController())*/
                }
            }
        }
    }
}
