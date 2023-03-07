package com.example.nineintelligence.presentation.exam

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nineintelligence.R
import com.example.nineintelligence.data.ExamModel
import com.example.nineintelligence.navigation.NavigationHolder
import com.example.nineintelligence.ui.theme.MainBlueColor
import com.example.nineintelligence.ui.theme.MainYellowColor
import com.example.nineintelligence.util.CustomText
import com.example.nineintelligence.util.ExamType
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


val questionAnswerDummyData = listOf(
    ExamModel(
        1, 1, 1, "Siapakah Pencipta Playstation", listOf(
            "Sony",
            "Microsoft",
            "LG",
            "Viewsonic",
        ), "Sony"
    ),
    ExamModel(
        1, 1, 1, "Snapdragon Menggunakan Arsitektur CPU apa?", listOf(
            "x86",
            "Arm",
            "Risc-V",
            "PPC",
        ), "Arm"
    ),
    ExamModel(
        1, 1, 1, "Apa sebutan Cache Besar milik AMD", listOf(
            "3D-Vcache",
            "Ultra Cache",
            "AMD-V",
            "TSX",
        ), "3D-Vcache"
    ),
    ExamModel(
        1, 1, 1, "RTX Nvidia Terbaru memiliki kode nama", listOf(
            "Ada Lovelace",
            "Fenrir",
            "TileGX",
            "fermi",
        ), "Ada Lovelace"
    ),
)

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ExamScreen(
    modifier: Modifier = Modifier,
    controller: NavController,
    vm: ExamViewModel = viewModel(),
    typeOf: ExamType
) {
    var shouldShowQuestionList by remember {
        mutableStateOf(false)
    }
    var parentSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    var shouldShowDialogOver by remember {
        mutableStateOf(false)
    }
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val savedAnswerViewModel by vm.savedAnswerStateFlow.collectAsStateWithLifecycle()
    val currentPage by remember {
        derivedStateOf {
            pagerState.currentPage
        }
    }
    Column(modifier = modifier.onSizeChanged {
        parentSize = it
    }) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(12.dp))
            TopBar(onBackPress = {
                controller.popBackStack()
            })
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomText(
                    text = "Soal (${pagerState.currentPage + 1}/${pagerState.pageCount})",
                    fontWeight = FontWeight.Bold,
                    color = MainBlueColor
                )

                Spacer(modifier = Modifier.weight(1F))
                IconButton(onClick = {
                    shouldShowQuestionList = !shouldShowQuestionList
                }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = MainBlueColor
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomText(text = "--:--", fontWeight = FontWeight.Bold, color = MainBlueColor)
            }
        }
        Column(
            modifier = Modifier
                .verticalScroll(state = scrollState)
                .padding(bottom = 12.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalPager(
                count = questionAnswerDummyData.size,
                state = pagerState,
                userScrollEnabled = false
            ) { out ->
                QuestionArea(
                    questionText = questionAnswerDummyData[out].soalText,
                    userAnswer = savedAnswerViewModel?.find {
                        it.first == out
                    }?.second ?: "",
                    questionAnswerList = questionAnswerDummyData[out].answer,
                    onClickedAnswer = { _, answer ->
                        vm.stateFlowMethodSaveAnswer(out, answer)
                    }, selectedAnswerIndex = savedAnswerViewModel?.find {
                        it.first == out
                    }?.second,
                    parentScreenSize = parentSize,
                    isClickable = typeOf == ExamType.TAKE_EXAMS,
                    showRightWrongAnswer = typeOf == ExamType.DISCUSSION,
                    rightAnswer = questionAnswerDummyData[out].correctAnswer
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        if (pagerState.currentPage == 0) {
                            return@Button
                        }
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }, modifier = Modifier
                        .fillMaxWidth(0.3F)
                        .height(35.dp),
                    shape = RoundedCornerShape(4.dp), colors = ButtonDefaults.buttonColors(
                        MainBlueColor
                    )
                ) {
                    CustomText(text = "Previous", fontSize = 12.sp, color = MainYellowColor)
                }
                Spacer(modifier = Modifier.weight(1F))
                AnimatedVisibility(
                    visible = currentPage < questionAnswerDummyData.size - 1,
                    enter = fadeIn(tween(200)),
                    exit = fadeOut(tween(200))
                    ) {
                    Button(
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }, modifier = Modifier
                            .fillMaxWidth(0.4F)
                            .height(35.dp),
                        shape = RoundedCornerShape(4.dp), colors = ButtonDefaults.buttonColors(
                            MainBlueColor
                        )
                    ) {
                        CustomText(text = "Next", fontSize = 12.sp, color = MainYellowColor)
                    }
                }

            }
        }
    }
    if (shouldShowQuestionList) {
        ListQuestionHolder(onDismiss = {
            shouldShowQuestionList = false
        }) {
            when (typeOf) {
                ExamType.DISCUSSION -> {
                    QuestionDiscussionListSelector(
                        questionData = questionAnswerDummyData,
                        onSubmitClick = {
                            controller.navigate(NavigationHolder.DiscussionScreen.route)
                        },
                        onGoToSelectedIndex = {
                            scope.launch {
                                pagerState.scrollToPage(it)
                            }
                        },
                        allAnswer = savedAnswerViewModel?.map {
                            it.second
                        } ?: emptyList()
                    )
                }

                ExamType.TAKE_EXAMS -> {
                    QuestionListSelector(
                        questionNumber = questionAnswerDummyData.size,
                        onSubmitClick = {
                            shouldShowDialogOver = !shouldShowDialogOver
                        },
                        onGoToSelectedIndex = {
                            scope.launch {
                                pagerState.scrollToPage(it)
                            }
                        },
                        hasAnswer = savedAnswerViewModel?.map {
                            it.first
                        } ?: emptyList()
                    )
                }
            }
        }
    }

    if (shouldShowDialogOver) {
        Dialog(onDismissRequest = { shouldShowDialogOver = false }) {
            DialogIsOver(onSubmitClick = {
                shouldShowDialogOver = false
                controller.navigate(NavigationHolder.QuestionDiscussion.route) {
                    popUpTo(NavigationHolder.BankSoalScreen.route)
                }
            }, onCancelClick = {
                shouldShowDialogOver = false
            })
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuestionArea(
    questionText: String,
    userAnswer: String,
    questionAnswerList: List<String>,
    onClickedAnswer: (Int, String) -> Unit,
    selectedAnswerIndex: String? = null,
    parentScreenSize: IntSize, isClickable: Boolean,
    showRightWrongAnswer: Boolean = false,
    rightAnswer: String
) {
    val density = LocalDensity.current
    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
        Card(elevation = CardDefaults.cardElevation(4.dp)) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                colors = CardDefaults.cardColors(MainYellowColor.copy(0.4F))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CustomText(
                        text = questionText,
                        color = MainBlueColor
                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(20.dp))
        val availableAnswer = remember {
            ('A'..'D').toList()
        }
        LazyColumn(modifier = Modifier.height(
            with(density) {
                parentScreenSize.height.toFloat().toDp() / 2.5F
            }
        )) {
            itemsIndexed(questionAnswerList) { index, out ->
                Card(colors = when (showRightWrongAnswer) {
                    true -> {
                        CardDefaults.cardColors(
                            if (rightAnswer == userAnswer && out == rightAnswer)
                                Color.Green
                            else if (out == rightAnswer) Color.Green
                            else if (userAnswer == out) Color.Red
                            else Color.Transparent
                        )
                    }

                    false -> {
                        CardDefaults.cardColors(
                            if (selectedAnswerIndex == out)
                                MainBlueColor else Color.Transparent
                        )
                    }
                },
                    border = BorderStroke(1.dp, Color.Black),
                    modifier = Modifier.height(70.dp),
                    onClick = {
                        if (isClickable) {
                            onClickedAnswer.invoke(index, out)
                        } else {
                            return@Card
                        }
                    }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            shape = CircleShape,
                            modifier = Modifier.size(30.dp),
                            colors = CardDefaults.cardColors(
                                if (selectedAnswerIndex == out)
                                    MainYellowColor else Color.LightGray
                            )
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CustomText(
                                    text = availableAnswer[index].toString(),
                                    fontWeight = FontWeight.Bold,
                                    color = if (selectedAnswerIndex == out)
                                        MainBlueColor else Color.Gray
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        CustomText(
                            text = out,
                            color = if (selectedAnswerIndex == out)
                                MainYellowColor else MainBlueColor
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }


    }
}

@Composable
private fun TopBar(onBackPress: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .offset((-20).dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            onBackPress.invoke()
        }) {
            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = null,
                modifier = Modifier.size(25.dp)
            )
        }
        CustomText(text = "Back", fontSize = 20.sp, color = MainBlueColor)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionListSelector(
    questionNumber: Int,
    onSubmitClick: () -> Unit,
    onGoToSelectedIndex: (Int) -> Unit,
    hasAnswer: List<Int>
) {
    var parentSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    val density = LocalDensity.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.55F)
            .padding(12.dp)
            .onSizeChanged {
                parentSize = it
            }
            .clickable(MutableInteractionSource(), indication = null, enabled = false, onClick = {

            })
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 12.dp)
                .padding(top = 12.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(start = 12.dp), modifier = Modifier.height(
                    with(density) {
                        parentSize.height.toFloat().toDp() / 1.5F
                    }
                )
            ) {
                items(questionNumber) {
                    Card(
                        border = BorderStroke(1.dp, Color.Black),
                        modifier = Modifier
                            .height(50.dp)
                            .padding(end = 12.dp),
                        onClick = {
                            onGoToSelectedIndex.invoke(it)
                        },
                        colors = CardDefaults.cardColors(
                            if (hasAnswer.contains(it))
                                MainBlueColor else Color.Transparent
                        )
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CustomText(
                                text = (it + 1).toString(),
                                fontWeight = FontWeight.Bold,
                                color = MainYellowColor, fontSize = 20.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(60.dp))
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 12.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        onSubmitClick.invoke()
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9F)
                        .height(45.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(MainBlueColor)
                ) {
                    CustomText(
                        text = "Submit",
                        color = MainYellowColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ListQuestionHolder(
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
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
            visible = animateBackground.value == 0.7F, enter = slideInHorizontally(
                tween(250), initialOffsetX = {
                    it * 2
                }
            ), exit = slideOutHorizontally(tween(250), targetOffsetX = {
                it - 50 * 2
            })
        ) {
            content.invoke()
        }
    }
}

@Composable
fun DialogIsOver(onSubmitClick: () -> Unit, onCancelClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.57F)
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 24.dp)
                .padding(top = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomText(
                text = "Apakah anda sudah yakin dengan semua jawaban?",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                color = MainBlueColor,
                fontSize = 16.sp,
                letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.height(32.dp))
            Image(painter = painterResource(id = R.drawable.puzzled), contentDescription = null)
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(bottom = 12.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.Bottom) {
                    Button(
                        onClick = {
                            onCancelClick.invoke()
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            MainBlueColor
                        )
                    ) {
                        CustomText(
                            text = "Cancel",
                            fontWeight = FontWeight.Bold,
                            color = MainYellowColor
                        )
                    }
                    Spacer(modifier = Modifier.weight(1F))
                    Button(
                        onClick = {
                            onSubmitClick.invoke()
                        }, shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            MainBlueColor
                        )
                    ) {
                        CustomText(
                            text = "Submit",
                            fontWeight = FontWeight.Bold,
                            color = MainYellowColor
                        )
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionDiscussionListSelector(
    questionData: List<ExamModel>,
    onSubmitClick: () -> Unit,
    onGoToSelectedIndex: (Int) -> Unit,
    allAnswer: List<String>
) {
    var parentSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    var countRightAnswer by remember {
        mutableStateOf(0)
    }
    var countWrongAnswer by remember {
        mutableStateOf(0)
    }
    var finalResult by remember {
        mutableStateOf(0)
    }
    LaunchedEffect(key1 = Unit ) {
        questionData.forEach {
            if (allAnswer.contains(it.correctAnswer)) countRightAnswer++ else countWrongAnswer++
        }
    }
    LaunchedEffect(key1 = Unit, block = {
        finalResult = (100 / questionData.size) * countRightAnswer
    })
    val scrollState = rememberScrollState()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(12.dp)
            .onSizeChanged {
                parentSize = it
            }
            .clickable(MutableInteractionSource(), indication = null, enabled = false, onClick = {

            })
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp)
                .padding(top = 12.dp)
                .verticalScroll(scrollState)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(start = 2.dp, bottom = 4.dp),
                modifier = Modifier.heightIn(50.dp, 300.dp)
            ) {
                itemsIndexed(questionData) { index, data ->
                    Card(
                        border = BorderStroke(1.dp, Color.Black),
                        modifier = Modifier
                            .height(50.dp)
                            .padding(end = 12.dp),
                        onClick = {
                            onGoToSelectedIndex.invoke(index)
                        },
                        colors = CardDefaults.cardColors(
                            if (allAnswer.contains(data.correctAnswer))
                                Color.Green else Color.Red
                        )
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CustomText(
                                text = (index + 1).toString(),
                                fontWeight = FontWeight.Bold,
                                color = MainYellowColor, fontSize = 20.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(60.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                onSubmitClick.invoke()
            },
            modifier = Modifier
                .fillMaxWidth(1F)
                .height(45.dp)
                .padding(horizontal = 12.dp),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(MainBlueColor)
        ) {
            CustomText(
                text = "Pembahasan",
                color = MainYellowColor,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp), border = BorderStroke(1.dp, Color.Black)
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 12.dp)
            ) {
                CustomText(
                    text = "Detail",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = MainBlueColor,
                    modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(0.dp),
                    colors = CardDefaults.cardColors(containerColor = MainBlueColor)
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .padding(top = 12.dp)
                        ) {
                            CustomText(
                                text = "Soal Benar",
                                fontWeight = FontWeight.Bold,
                                color = MainYellowColor
                            )
                            Spacer(modifier = Modifier.weight(1F))
                            CustomText(
                                text = "$countRightAnswer",
                                fontWeight = FontWeight.Bold,
                                color = MainYellowColor
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .padding(top = 12.dp)
                        ) {
                            CustomText(
                                text = "Soal Salah",
                                fontWeight = FontWeight.Bold,
                                color = MainYellowColor
                            )
                            Spacer(modifier = Modifier.weight(1F))
                            CustomText(
                                text = "$countWrongAnswer",
                                fontWeight = FontWeight.Bold,
                                color = MainYellowColor
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                ) {
                    CustomText(
                        text = "Nilai",
                        fontWeight = FontWeight.Bold,
                        color = MainBlueColor
                    )
                    Spacer(modifier = Modifier.weight(1F))
                    CustomText(
                        text = "$finalResult",
                        fontWeight = FontWeight.Bold,
                        color = MainYellowColor
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
    }

}