package com.example.nineintelligence.presentation.subject

import android.provider.MediaStore.Audio.Radio
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nineintelligence.core.CustomText
import com.example.nineintelligence.domain.models.SubjectModel
import com.example.nineintelligence.domain.util.SubjectType
import com.example.nineintelligence.presentation.home.CustomPercentage
import com.example.nineintelligence.ui.theme.MainBlueColor
import com.example.nineintelligence.ui.theme.MainYellowColor
import com.example.nineintelligence.ui.theme.Poppins
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

private val dummySubjectData = listOf(
    SubjectModel(1, "Math", 0.7F),
    SubjectModel(2, "IPA", 0.2F),
    SubjectModel(3, "IPS", 0.4F),
    SubjectModel(4, "Inggris", 0.6F),
)

private val subSubjectListExample = listOf(
    SubjectModel(1, "Aritmatika", 0.7F),
    SubjectModel(2, "Logaritmik", 0.2F),
    SubjectModel(3, "Sudut", 0.4F),
    SubjectModel(4, "Probabilitas", 0.6F)
)


@OptIn(ExperimentalPagerApi::class)
@Composable
fun SubjectScreen(
    modifier: Modifier = Modifier,
    vm: SubjectViewModel = koinViewModel(),
    controller: NavController
) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val currentPage by remember {
        derivedStateOf {
            pagerState.currentPage
        }
    }
    BackHandler {
        if (currentPage > 0) {
            scope.launch {
                pagerState.animateScrollToPage(currentPage - 1)
            }
            return@BackHandler
        }
        controller.popBackStack()
    }
    Column(modifier) {
        TopBarMain(font = Poppins.fonts, onBackPress = {
            if (currentPage > 0) {
                scope.launch {
                    pagerState.animateScrollToPage(currentPage - 1)
                }
                return@TopBarMain
            }
            controller.popBackStack()
        })
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalPager(count = 2, state = pagerState) {
            when (it) {
                0 -> {
                    MiddleScreen(
                        subjectList = dummySubjectData,
                        mainTitleText = "Materi",
                        mainDescriptionText = "Terdapat banyak materi yang telah dikelompokkan" +
                                " sesuai mata pelajaran yang ada di Sekolah Menengah Atas. Kalian " +
                                "dapat memilih untuk mempelajari materi apa saja yang kalian inginkan.",
                        typeOf = SubjectType.MainSubject, onTapSubject = { out ->
                            vm.setMainTitle(out)
                            scope.launch {
                                pagerState.animateScrollToPage(it + 1)
                            }
                        }
                    )
                }

                1 -> {
                    MiddleScreen(
                        subjectList = subSubjectListExample,
                        mainTitleText = vm.mainTitleText,
                        mainDescriptionText = "Materi ${vm.mainTitleText} disini bisa terbilang cukup lengkap." +
                                " Kalian dapat membeli materi ini secara langsung ataupun dapat " +
                                "melalui media digital dengan cara berlangganan.",
                        typeOf = SubjectType.SubSubject, onTapSubject = {

                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun TopBarMain(font: FontFamily, onBackPress: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            onBackPress.invoke()
        }) {
            Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = null)
        }
        Text(text = "Back", fontFamily = font, fontSize = 16.sp, color = MainBlueColor)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MiddleScreen(
    subjectList: List<SubjectModel>,
    mainTitleText: String = "",
    mainDescriptionText: String = "",
    typeOf: SubjectType, onTapSubject: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        CustomText(text = mainTitleText, fontWeight = FontWeight.Bold, color = MainBlueColor)
        CustomText(
            text = mainDescriptionText,
            color = MainBlueColor, fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxSize()
        ) {
            if (typeOf == SubjectType.SubSubject) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp), colors = CardDefaults.cardColors(Color.White),
                        elevation = CardDefaults.cardElevation(12.dp), onClick = {

                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 12.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row {
                                CustomText(
                                    text = "Pembelian Materi (Fisik)",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = MainBlueColor
                                )
                                Spacer(modifier = Modifier.weight(1F))
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth(0.75F)
                                        .height(25.dp), colors = CardDefaults.cardColors(
                                        MainBlueColor
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        CustomText(
                                            text = "Beli", color = MainYellowColor,
                                            fontWeight = FontWeight.Bold, fontSize = 12.sp
                                        )

                                    }
                                }
                            }
                        }
                    }
                }
            }
            items(subjectList) {
                ProgressCard(
                    font = Poppins.fonts, studyName = it.subjectName,
                    percentage = it.progress, indexOf = it.id, onItemClick = {
                        onTapSubject.invoke(it.subjectName)
                    }, progressVisibility = typeOf == SubjectType.MainSubject
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProgressCard(
    font: FontFamily, studyName: String, percentage: Float, indexOf: Int,
    onItemClick: () -> Unit, progressVisibility: Boolean = false
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp), colors = CardDefaults.cardColors(
            MainYellowColor
        ), elevation = CardDefaults.cardElevation(8.dp), shape = RoundedCornerShape(12.dp),
        onClick = {
            onItemClick.invoke()
        }
    ) {
        Box {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    shape = CircleShape,
                    modifier = Modifier.size(30.dp),
                    colors = CardDefaults.cardColors(MainBlueColor)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = indexOf.toString(),
                            fontSize = 16.sp,
                            fontFamily = font,
                            fontWeight = FontWeight.Bold,
                            color = MainYellowColor
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = studyName,
                    fontFamily = font,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            if (progressVisibility) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomPercentage(
                        percentage,
                        inactiveColor = Color.White,
                        activeColor = MainBlueColor,
                        modifier = Modifier.fillMaxWidth(0.5F)
                    )
                }

            }

        }
    }
}



