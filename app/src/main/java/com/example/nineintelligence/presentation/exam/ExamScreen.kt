package com.example.nineintelligence.presentation.exam

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.requiredHeight
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.nineintelligence.R
import com.example.nineintelligence.core.CustomText
import com.example.nineintelligence.domain.models.DiscussModel
import com.example.nineintelligence.domain.models.SubmitModel
import com.example.nineintelligence.domain.models.UserAnswerData
import com.example.nineintelligence.domain.util.DiscussionType
import com.example.nineintelligence.domain.util.ExamType
import com.example.nineintelligence.navigation.NavigationHolder
import com.example.nineintelligence.ui.theme.CorrectAnswerColor
import com.example.nineintelligence.ui.theme.IncorrectAnswerColor
import com.example.nineintelligence.ui.theme.MainBlueColor
import com.example.nineintelligence.ui.theme.MainYellowColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.time.Duration.Companion.hours


@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ExamScreen(
    modifier: Modifier = Modifier,
    controller: NavController,
    vm: ExamViewModel = koinViewModel(),
    typeOf: ExamType,
    slugName: String = "",
    time: Int,
    discussionType: DiscussionType? = null
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
    LaunchedEffect(key1 = Unit, block = {
        when (typeOf) {
            ExamType.TAKE_EXAMS -> {
                vm.retrieveSoalList(slugName, DiscussionType.EXAM_DISCUSSION)
                if (time > 0) {
                    vm.setLengthTime(time)
                }
            }

            ExamType.DISCUSSION -> {
                discussionType?.let {
                    vm.retrieveSoalList(slugName, it)
                    vm.getPembahasan(slugName, it)
                }
            }

            ExamType.BANK_SOAL -> {
                vm.retrieveBankSoal(slugName)
            }
        }
    })
    val currentTime by vm.savedTime.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val savedAnswerViewModel by vm.savedAnswerStateFlow.collectAsStateWithLifecycle()
    val retrievedSoal by vm.listQuestion.collectAsStateWithLifecycle()
    val discussionResponse by vm.discussionResponse.collectAsStateWithLifecycle()
    val resultSubmit by vm.resultSubmit.collectAsStateWithLifecycle()
    val bankSoalSubmitResponse by vm.bankSoalSubmitResponse.collectAsStateWithLifecycle()
    val bankSoalList by vm.bankSoalListQuestion.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = resultSubmit, key2 = bankSoalSubmitResponse, block = {
        if (typeOf == ExamType.TAKE_EXAMS) {
            resultSubmit?.let {
                shouldShowDialogOver = false
                controller.navigate(NavigationHolder.QuestionDiscussion.route + "/$slugName") {
                    popUpTo(
                        NavigationHolder.ExamScreen.route + "/$slugName" +
                                "/${DiscussionType.EXAM_DISCUSSION.name}"
                    ) {
                        inclusive = true
                    }
                }
            }
        } else if (typeOf == ExamType.BANK_SOAL) {
            bankSoalSubmitResponse?.let {
                controller.navigate(
                    NavigationHolder.QuestionDiscussion.route + "/$slugName" +
                            "/${DiscussionType.BANKSOAL_DISCUSSION.name}"
                )
                shouldShowDialogOver = false
            }
        }
    })
    BackHandler {
        controller.navigate(NavigationHolder.ProfileScreenChild.route) {
            popUpTo(NavigationHolder.ProfileScreenChild.route) {
                inclusive = true
            }
        }
    }
    var formattedCurrentTime: String? by remember {
        mutableStateOf(null)
    }
    val listSize by remember {
        derivedStateOf {
            if (typeOf == ExamType.TAKE_EXAMS || typeOf == ExamType.DISCUSSION) retrievedSoal.size
            else bankSoalList.size
        }
    }
    val currentPage by remember {
        derivedStateOf {
            pagerState.currentPage
        }
    }
    LaunchedEffect(key1 = currentTime, block = {
        currentTime.toComponents { hours, minutes, seconds, _ ->
            if (hours == 0L && minutes == 0 && seconds == 0) {
                return@toComponents
            }
            formattedCurrentTime =
                (if (hours.hours.inWholeHours > 0) "${hours.hours.inWholeHours}:" else "") +
                        "$minutes:$seconds${if (seconds < 10) 0 else ""}"
        }
    })
    Column(modifier = modifier.onSizeChanged {
        parentSize = it
    }) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(12.dp))
            TopBar(onBackPress = {
                controller.navigate(NavigationHolder.ProfileScreenChild.route) {
                    popUpTo(NavigationHolder.ProfileScreenChild.route) {
                        inclusive = true
                    }
                }
            })
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                CustomText(
                    text = "Soal (${pagerState.currentPage + 1}/${pagerState.pageCount})",
                    fontWeight = FontWeight.Bold,
                    color = MainBlueColor,
                    modifier = Modifier.weight(1F)
                )

                IconButton(onClick = {
                    shouldShowQuestionList = !shouldShowQuestionList
                }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .weight(1F),
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
                CustomText(
                    text = formattedCurrentTime ?: "--:--",
                    fontWeight = FontWeight.Bold,
                    color = MainBlueColor,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
        if (typeOf == ExamType.DISCUSSION) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomText(text = "TPS", fontWeight = FontWeight.SemiBold, color = MainBlueColor)
                Spacer(modifier = Modifier.width(12.dp))
                CustomText(
                    text = "Penalaran Umum",
                    fontWeight = FontWeight.SemiBold,
                    color = MainYellowColor
                )
            }

        }
        Column(
            modifier = Modifier
                .padding(bottom = 12.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalPager(
                count = listSize, state = pagerState, userScrollEnabled = false
            ) { out ->
                QuestionArea(
                    questionText = if (typeOf == ExamType.TAKE_EXAMS || typeOf == ExamType.DISCUSSION)
                        retrievedSoal[out].content
                            ?: "" else bankSoalList[out].content ?: "",
                    userAnswer = if (typeOf == ExamType.TAKE_EXAMS) {
                        savedAnswerViewModel?.find {
                            it.first == out
                        }?.second?.answer ?: ""
                    } else if (typeOf == ExamType.BANK_SOAL) {
                        savedAnswerViewModel?.find {
                            it.first == out
                        }?.second?.answer ?: ""
                    } else {
                        if (discussionResponse.isNotEmpty()) {
                            discussionResponse.find {
                                it.soalDetail?.soalId == retrievedSoal[out].idSoal
                            }?.userAnswer ?: ""
                        } else {
                            ""
                        }
                    },
                    questionAnswerList = if (typeOf == ExamType.TAKE_EXAMS || typeOf == ExamType.DISCUSSION)
                        retrievedSoal[out].answers as? List<String>
                            ?: emptyList() else bankSoalList[out].answers ?: emptyList(),
                    onClickedAnswer = { _, answer ->
                        vm.stateFlowMethodSaveAnswer(
                            out,
                            UserAnswerData(
                                if (typeOf == ExamType.TAKE_EXAMS) retrievedSoal[out].idSoal
                                    ?: 0 else bankSoalList[out].soalId ?: 0, answer
                            )
                        )
                    },
                    selectedAnswerIndex = savedAnswerViewModel?.find {
                        it.first == out
                    }?.second?.answer ?: "",
                    isClickable = typeOf == ExamType.TAKE_EXAMS || typeOf == ExamType.BANK_SOAL,
                    showRightWrongAnswer = typeOf == ExamType.DISCUSSION,
                    rightAnswer = if (discussionResponse.isNotEmpty())
                        discussionResponse.find {
                            it.soalDetail?.soalId == retrievedSoal[out].idSoal
                        }?.soalDetail?.correctAns ?: "" else ""
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp)
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier.size(50.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(Color.Gray), onClick = {
                    if (pagerState.currentPage == 0) {
                        return@Card
                    }
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                }
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
            Spacer(modifier = Modifier.weight(1F))
            AnimatedVisibility(
                visible = currentPage < listSize - 1,
                enter = fadeIn(tween(200)),
                exit = fadeOut(tween(200))
            ) {
                Card(
                    modifier = Modifier.size(50.dp),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(Color.Gray), onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = null,
                            tint = Color.White, modifier = Modifier.rotate(180F)
                        )
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
                    QuestionDiscussionListSelector(questionData = discussionResponse.toPersistentList(),
                        onSubmitClick = {
                            controller.navigate(NavigationHolder.DiscussionScreen.route)
                        }, onGoToSelectedIndex = {
                            scope.launch {
                                pagerState.scrollToPage(it)
                            }
                        }, soalSize = { listSize })
                }

                ExamType.TAKE_EXAMS -> {
                    QuestionListSelector(questionNumber = retrievedSoal.size, onSubmitClick = {
                        shouldShowDialogOver = !shouldShowDialogOver
                    }, onGoToSelectedIndex = {
                        scope.launch {
                            pagerState.scrollToPage(it)
                        }
                    }, hasAnswer = savedAnswerViewModel?.map {
                        it.first
                    } ?: emptyList(), currentSelected = currentPage)
                }

                ExamType.BANK_SOAL -> {
                    QuestionListSelector(questionNumber = bankSoalList.size, onSubmitClick = {
                        shouldShowDialogOver = !shouldShowDialogOver
                    }, onGoToSelectedIndex = {
                        scope.launch {
                            pagerState.scrollToPage(it)
                        }
                    }, hasAnswer = savedAnswerViewModel?.map {
                        it.first
                    } ?: emptyList(), currentSelected = currentPage)
                }
            }
        }
    }

    if (shouldShowDialogOver) {
        Dialog(onDismissRequest = { shouldShowDialogOver = false }) {
            DialogIsOver(onSubmitClick = {
                vm.saveAnswer(SubmitModel(savedAnswerViewModel?.map {
                    it.second
                } ?: return@DialogIsOver), slugName, typeOf)

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
    isClickable: Boolean,
    showRightWrongAnswer: Boolean = false,
    rightAnswer: String
) {
    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
        val availableAnswer = remember {
            ('A'..'E').toList()
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Card(elevation = CardDefaults.cardElevation(4.dp)) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .requiredHeight(250.dp),
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
            }
            itemsIndexed(questionAnswerList) { index, out ->
                Card(colors = when (showRightWrongAnswer) {
                    true -> {
                        CardDefaults.cardColors(
                            if (rightAnswer == userAnswer && out == rightAnswer) CorrectAnswerColor
                            else if (out == rightAnswer) CorrectAnswerColor
                            else if (userAnswer == out) IncorrectAnswerColor
                            else Color.Transparent
                        )
                    }

                    false -> {
                        CardDefaults.cardColors(
                            if (selectedAnswerIndex == out) MainBlueColor else Color.Transparent
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
                                if (selectedAnswerIndex == out) MainYellowColor else Color.LightGray
                            )
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CustomText(
                                    text = availableAnswer[index].toString(),
                                    fontWeight = FontWeight.Bold,
                                    color = if (selectedAnswerIndex == out) MainBlueColor else Color.Gray
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        CustomText(
                            text = out,
                            color = if (selectedAnswerIndex == out) MainYellowColor else MainBlueColor
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
    hasAnswer: List<Int>,
    currentSelected: Int? = null
) {
    var parentSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    val density = LocalDensity.current
    Card(modifier = Modifier
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
                contentPadding = PaddingValues(start = 12.dp),
                modifier = Modifier.height(with(density) {
                    parentSize.height.toFloat().toDp() / 1.5F
                })
            ) {
                items(questionNumber) {
                    Card(
                        border = if (currentSelected == it) BorderStroke(
                            2.dp, MainBlueColor
                        ) else BorderStroke(1.dp, Color.Black),
                        modifier = Modifier
                            .height(50.dp)
                            .padding(end = 12.dp),
                        onClick = {
                            onGoToSelectedIndex.invoke(it)
                        },
                        colors = CardDefaults.cardColors(
                            if (hasAnswer.contains(it) && currentSelected != it)
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
                                color = if (currentSelected == it) MainBlueColor else MainYellowColor,
                                fontSize = 20.sp
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
                        text = "Submit", color = MainYellowColor, fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ListQuestionHolder(
    onDismiss: () -> Unit, content: @Composable () -> Unit
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
                        }, shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(
                            MainBlueColor
                        )
                    ) {
                        CustomText(
                            text = "Cancel", fontWeight = FontWeight.Bold, color = MainYellowColor
                        )
                    }
                    Spacer(modifier = Modifier.weight(1F))
                    Button(
                        onClick = {
                            onSubmitClick.invoke()
                        }, shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(
                            MainBlueColor
                        )
                    ) {
                        CustomText(
                            text = "Submit", fontWeight = FontWeight.Bold, color = MainYellowColor
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
    questionData: ImmutableList<DiscussModel>,
    onSubmitClick: () -> Unit,
    onGoToSelectedIndex: (Int) -> Unit,
    soalSize: () -> Int
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
    LaunchedEffect(key1 = Unit) {
        questionData.forEach {
            if (it.userAnswer == it.soalDetail?.correctAns) countRightAnswer++ else countWrongAnswer++
        }
    }
    LaunchedEffect(key1 = Unit, block = {
        if (questionData.isEmpty()) {
            return@LaunchedEffect
        }
        finalResult = (100 / questionData.size) * countRightAnswer
    })
    val scrollState = rememberScrollState()
    Card(modifier = Modifier
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
                items(soalSize()) { index ->
                    val rightAnswer = remember {
                        return@remember if (index + 1 > questionData.size) {
                            false 
                        } else {
                            questionData[index].userAnswer == questionData[index].soalDetail?.correctAns
                        }
                    }
                    Card(
                        border = BorderStroke(1.dp, Color.Black),
                        modifier = Modifier
                            .height(50.dp)
                            .padding(end = 12.dp),
                        onClick = {
                            onGoToSelectedIndex.invoke(index)
                        },
                        colors = CardDefaults.cardColors(
                            if (rightAnswer) CorrectAnswerColor else IncorrectAnswerColor
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
                                color = MainYellowColor,
                                fontSize = 20.sp
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
                text = "Pembahasan", color = MainYellowColor, fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Column(
                modifier = Modifier.padding(top = 12.dp)
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
                    modifier = Modifier.padding(horizontal = 12.dp)
                ) {
                    CustomText(
                        text = "Nilai", fontWeight = FontWeight.Bold, color = MainBlueColor
                    )
                    Spacer(modifier = Modifier.weight(1F))
                    CustomText(
                        text = "$finalResult", fontWeight = FontWeight.Bold, color = MainYellowColor
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
    }

}