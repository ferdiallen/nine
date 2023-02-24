package com.example.nineintelligence.presentation.exam

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nineintelligence.ui.theme.MainBlueColor
import com.example.nineintelligence.ui.theme.MainYellowColor
import com.example.nineintelligence.util.CustomText
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ExamScreen(modifier: Modifier = Modifier, controller: NavController) {
    var shouldShowQuestionList by remember {
        mutableStateOf(false)
    }
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    Column(modifier = modifier) {
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
            var savedAnswer by remember {
                mutableStateOf<String?>(null)
            }
            val listAnswer = remember {
                listOf("Atreus", "Kratos", "Zeus", "Gaia")
            }

            HorizontalPager(count = 10, state = pagerState, userScrollEnabled = false) {
                QuestionArea(
                    questionText = "Who is Kratos $it",
                    questionAnswerList = listAnswer,
                    onClickedAnswer = { index, answer ->
                        savedAnswer = answer
                    }, selectedAnswerIndex = savedAnswer ?: ""
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

            if (shouldShowQuestionList) {
                ListQuestionHolder(onDismiss = {
                    shouldShowQuestionList = false
                })
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuestionArea(
    questionText: String,
    questionAnswerList: List<String>,
    onClickedAnswer: (Int, String) -> Unit,
    selectedAnswerIndex: String? = null
) {
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
        availableAnswer.forEachIndexed { index, out ->
            Card(colors = CardDefaults.cardColors(
                if (selectedAnswerIndex == out.toString())
                    MainBlueColor else Color.Transparent
            ),
                border = BorderStroke(1.dp, Color.Black),
                modifier = Modifier.height(70.dp),
                onClick = {
                    onClickedAnswer.invoke(index, out.toString())
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
                            if (selectedAnswerIndex == out.toString())
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
                                color = if (selectedAnswerIndex == out.toString())
                                    MainBlueColor else Color.Gray
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    CustomText(
                        text = out.toString(),
                        color = if (selectedAnswerIndex == out.toString())
                            MainYellowColor else MainBlueColor
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
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
fun QuestionListSelector() {
    val selected = remember {
        mutableStateListOf<Int>()
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.55F)
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
                .padding(top = 12.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(start = 12.dp)
            ) {
                items(20) {
                    Card(
                        border = BorderStroke(1.dp, Color.Black),
                        modifier = Modifier
                            .height(50.dp)
                            .padding(end = 12.dp),
                        onClick = {
                            selected.add(it)
                        },
                        colors = CardDefaults.cardColors(
                            if (selected.contains(it))
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
                    onClick = { },
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
private fun ListQuestionHolder(onDismiss: () -> Unit) {
    var animatedFloat by remember {
        mutableStateOf(0.01F)
    }
    val animateBackground = animateFloatAsState(
        targetValue = animatedFloat, tween(500)
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
                tween(500), initialOffsetX = {
                    it * 2
                }
            ), exit = slideOutHorizontally(tween(500), targetOffsetX = {
                it - 50 * 2
            })
        ) {
            QuestionListSelector()
        }
    }
}