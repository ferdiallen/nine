package com.example.nineintelligence.presentation.subject

import android.provider.MediaStore.Audio.Radio
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.nineintelligence.R
import com.example.nineintelligence.core.CustomText
import com.example.nineintelligence.domain.models.SubjectModel
import com.example.nineintelligence.domain.models.SubjectReadingModel
import com.example.nineintelligence.domain.util.SubjectType
import com.example.nineintelligence.presentation.home.CustomPercentage
import com.example.nineintelligence.ui.theme.MainBlueColor
import com.example.nineintelligence.ui.theme.MainYellowColor
import com.example.nineintelligence.ui.theme.Poppins
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
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

private val subjetReadExample = listOf(
    SubjectReadingModel(
        "Introduction of Kotlin",
        "Kotlin is a general purpose, free," + " open source, statically typed “pragmatic” programming language initially designed " + "for the JVM (Java Virtual Machine) and Android, and combines object-oriented and " + "functional programming features. It is focused on interoperability, safety, clarity," + " and tooling support. Versions of Kotlin targeting JavaScript ES5.1 and native code " + "(using LLVM) for a number of processors are in production as well."
    ),
    SubjectReadingModel(
        "Kotlin Advantage of Java",
        "Null references are controlled by the type system.\n" + "No raw types.\n" + "Arrays in Kotlin are invariant.\n" + "Kotlin has proper function types, as opposed to Java's SAM-conversions.\n" + "Use-site variance without wildcards.\n" + "Kotlin does not have checked exceptions."
    ),
    SubjectReadingModel(
        "What is Lorem ipsum dolor",
        "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n" +
                "\n" +
                "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham."
    )
)

val dummyData = listOf("Introduction of Kotlin", "Variables in kotlin")


@OptIn(ExperimentalPagerApi::class)
@Composable
fun SubjectScreen(
    modifier: Modifier = Modifier, vm: SubjectViewModel = koinViewModel(), controller: NavController
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
        }, onMenuClick = {})
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalPager(count = 2, state = pagerState) {
            when (it) {
                0 -> {
                    MiddleScreen(subjectList = dummySubjectData,
                        mainTitleText = "Materi",
                        mainDescriptionText = "Terdapat banyak materi yang telah dikelompokkan" + " sesuai mata pelajaran yang ada di Sekolah Menengah Atas. Kalian " + "dapat memilih untuk mempelajari materi apa saja yang kalian inginkan.",
                        typeOf = SubjectType.MainSubject,
                        onTapSubject = { out ->
                            vm.setMainTitle(out)
                            scope.launch {
                                pagerState.animateScrollToPage(it + 1)
                            }
                        })
                }

                1 -> {
                    MiddleScreen(subjectList = subSubjectListExample,
                        mainTitleText = vm.mainTitleText,
                        mainDescriptionText = "Materi ${vm.mainTitleText} disini bisa terbilang cukup lengkap." + " Kalian dapat membeli materi ini secara langsung ataupun dapat " + "melalui media digital dengan cara berlangganan.",
                        typeOf = SubjectType.SubSubject,
                        onTapSubject = {

                        })
                }
            }
        }
    }
}

@Composable
private fun TopBarMain(
    font: FontFamily,
    onBackPress: () -> Unit,
    shouldShowMenuButton: Boolean = false,
    onMenuClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            onBackPress.invoke()
        }) {
            Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = null)
        }
        Text(text = "Back", fontFamily = font, fontSize = 16.sp, color = MainBlueColor)
        Spacer(modifier = Modifier.weight(1F))
        if (shouldShowMenuButton) {
            IconButton(onClick = {
                onMenuClick.invoke()
            }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MiddleScreen(
    subjectList: List<SubjectModel>,
    mainTitleText: String = "",
    mainDescriptionText: String = "",
    typeOf: SubjectType,
    onTapSubject: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        CustomText(text = mainTitleText, fontWeight = FontWeight.Bold, color = MainBlueColor)
        CustomText(
            text = mainDescriptionText, color = MainBlueColor, fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxSize()
        ) {
            if (typeOf == SubjectType.SubSubject) {
                item {
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp),
                        colors = CardDefaults.cardColors(Color.White),
                        elevation = CardDefaults.cardElevation(12.dp),
                        onClick = {

                        }) {
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
                                        .height(25.dp),
                                    colors = CardDefaults.cardColors(
                                        MainBlueColor
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        CustomText(
                                            text = "Beli",
                                            color = MainYellowColor,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp
                                        )

                                    }
                                }
                            }
                        }
                    }
                }
            }
            items(subjectList) {
                var currentPercentage by remember {
                    mutableStateOf(0F)
                }
                LaunchedEffect(key1 = Unit, block = {
                    currentPercentage = it.progress
                })
                val animateProgress by animateFloatAsState(
                    targetValue = currentPercentage,
                    tween(400, delayMillis = 100), label = ""
                )
                ProgressCard(
                    font = Poppins.fonts,
                    studyName = it.subjectName,
                    percentage = animateProgress,
                    indexOf = it.id,
                    onItemClick = {
                        onTapSubject.invoke(it.subjectName)
                    },
                    progressVisibility = typeOf == SubjectType.MainSubject
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProgressCard(
    font: FontFamily,
    studyName: String,
    percentage: Float,
    indexOf: Int,
    onItemClick: () -> Unit,
    progressVisibility: Boolean = false
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(90.dp),
        colors = CardDefaults.cardColors(
            MainYellowColor
        ),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = {
            onItemClick.invoke()
        }) {
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

@Composable
fun GreetingReadingSubject(
    modifier: Modifier = Modifier
) {
    var shouldShowUserNotBuySubjectYet by remember {
        mutableStateOf(false)
    }
    Column(modifier) {
        TopBarMain(font = Poppins.fonts, onBackPress = {}, onMenuClick = {

        })
        Spacer(modifier = Modifier.height(12.dp))
        Column(modifier = Modifier.padding(horizontal = 12.dp)) {
            CustomText(
                text = subjetReadExample.first().title,
                fontWeight = FontWeight.Bold,
                color = MainBlueColor
            )
            CustomText(
                text = subjetReadExample.first().content
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 20.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            shouldShowUserNotBuySubjectYet = !shouldShowUserNotBuySubjectYet
                        },
                        colors = ButtonDefaults.buttonColors(MainBlueColor),
                        modifier = Modifier
                            .fillMaxWidth(0.5F)
                            .weight(1F)
                    ) {
                        CustomText(
                            text = stringResource(R.string.download_btn_title),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.weight(1F))
                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(MainBlueColor),
                        modifier = Modifier
                            .fillMaxWidth(0.5F)
                            .weight(1F)
                    ) {
                        CustomText(
                            text = stringResource(R.string.start_btn_subject),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
    if (shouldShowUserNotBuySubjectYet) {
        CustomDialogTransitionSubjectScreen(onDismiss = {
            shouldShowUserNotBuySubjectYet = false
        }) {
            DialogNotSubscriptionYet(
                modifier = Modifier
                    .fillMaxWidth(0.7F)
                    .fillMaxHeight(0.3F),
                onBackPress = {
                    it.invoke()
                }
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun MainReadingSubject(
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    var shouldShowSideSelectorMenu by remember {
        mutableStateOf(false)
    }
    val currentPage by remember {
        derivedStateOf {
            pagerState.currentPage
        }
    }

    Column(modifier) {
        TopBarMain(font = Poppins.fonts, onBackPress = {

        }, shouldShowMenuButton = true, onMenuClick = {
            shouldShowSideSelectorMenu = !shouldShowSideSelectorMenu
        })
        Spacer(modifier = Modifier.height(14.dp))
        HorizontalPager(count = subjetReadExample.size, state = pagerState) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp)
            ) {
                CustomText(
                    text = subjetReadExample[it].title,
                    fontWeight = FontWeight.Bold,
                    color = MainBlueColor
                )
                Spacer(modifier = Modifier.height(12.dp))
                CustomText(text = subjetReadExample[it].content)

            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 32.dp)
            .padding(horizontal = 32.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            if (currentPage > 0) {
                Card(
                    modifier = Modifier.size(50.dp),
                    shape = CircleShape,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(currentPage - 1)
                        }
                    },
                    colors = CardDefaults.cardColors(MainBlueColor.copy(0.4F))
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1F))
            if (currentPage < subjetReadExample.size - 1) {
                Card(
                    modifier = Modifier.size(50.dp),
                    shape = CircleShape,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(currentPage + 1)
                        }
                    },
                    colors = CardDefaults.cardColors(MainBlueColor.copy(0.4F))
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = null,
                            modifier = Modifier.rotate(180F),
                            tint = Color.White
                        )
                    }
                }

            }
        }
    }
    if (shouldShowSideSelectorMenu) {
        CustomDialogTransitionSubjectScreen(onDismiss = {
            shouldShowSideSelectorMenu = false
        }, content = { invocation ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SubjectSelector(
                    subjetReadExample.map {
                        it.title
                    },
                    currentSelectedSubject = currentPage,
                    onSubjectSelected = {
                        invocation.invoke()
                        if (it <= subjetReadExample.size - 1) {
                            scope.launch {
                                pagerState.animateScrollToPage(it)
                            }
                        }
                    })
            }
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectSelector(
    subjectList: List<String>,
    currentSelectedSubject: Int,
    onSubjectSelected: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.7F)
            .fillMaxHeight(0.5F)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .padding(top = 16.dp)
        ) {
            CustomText(text = "List Materi", fontWeight = FontWeight.Bold, color = MainBlueColor)
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                itemsIndexed(subjectList) { index, out ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        border = BorderStroke(1.dp, Color.Black),
                        onClick = {
                            onSubjectSelected.invoke(index)
                        },
                        colors = CardDefaults.cardColors(
                            if (currentSelectedSubject == index)
                                MainYellowColor else Color.Transparent
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 12.dp)
                                .padding(vertical = 12.dp), verticalArrangement = Arrangement.Center
                        ) {
                            CustomText(text = out, color = MainBlueColor)
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun CustomDialogTransitionSubjectScreen(
    onDismiss: () -> Unit,
    content: @Composable (onCallBack: () -> Unit) -> Unit
) {
    var animatedFloat by remember {
        mutableStateOf(0.01F)
    }
    val animateBackground = animateFloatAsState(
        targetValue = animatedFloat, tween(250), label = ""
    )
    LaunchedEffect(key1 = Unit, block = {
        delay(250)
        animatedFloat = 0.7F
    })
    LaunchedEffect(key1 = animateBackground.value, block = {
        if (animateBackground.value == 0F) {
            onDismiss.invoke()
        }
    })
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(animateBackground.value))
            .clickable(MutableInteractionSource(), indication = null, onClick = {
                animatedFloat = 0F
            })
    ) {
        AnimatedVisibility(
            visible = animateBackground.value == 0.7F,
            enter = slideInHorizontally(tween(250), initialOffsetX = {
                it * 2
            }),
            exit = slideOutHorizontally(tween(250), targetOffsetX = {
                it - 50 * 2
            })
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content.invoke {
                    animatedFloat = 0F
                }
            }
        }
    }
}

@Composable
fun DialogNotSubscriptionYet(
    modifier: Modifier = Modifier,
    onBackPress: () -> Unit
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .padding(top = 12.dp)
        ) {
            CustomText(
                text = "Anda harus berlangganan terlebih dahulu " +
                        "agar dapat mengunduh file materi",
                color = MainBlueColor,
                fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
            ) {
                Button(
                    onClick = {
                        onBackPress.invoke()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    CustomText(text = "Kembali", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}