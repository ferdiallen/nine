package com.example.nineintelligence

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import androidx.navigation.compose.rememberNavController
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.domain.util.ExamType
import com.example.nineintelligence.navigation.RootNavigation
import com.example.nineintelligence.presentation.banksoal.BankSoal
import com.example.nineintelligence.presentation.dummy.DateTimeFormatExample
import com.example.nineintelligence.presentation.exam.ExamScreen
import com.example.nineintelligence.presentation.profile.ProfileScreen
import com.example.nineintelligence.presentation.tryout.TryoutInformation
import com.example.nineintelligence.presentation.tryout.TryoutScreen
import com.example.nineintelligence.ui.theme.NineIntelligenceTheme
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

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
                    /* DateTimeFormatExample()*/
                    ProfileScreen(
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp), onBackPress = {

                        }, onLogoutAction = {

                        }, controller = rememberNavController()
                    )
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
                        typeOf = ExamType.TAKE_EXAMS, slugName = "brush-teeth",time = 0
                    )*/
                    /*BankSoal(controller = rememberNavController(), modifier = Modifier.fillMaxSize())*/
                }
            }
        }
    }
}
