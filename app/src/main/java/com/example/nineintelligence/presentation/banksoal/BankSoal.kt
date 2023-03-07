package com.example.nineintelligence.presentation.banksoal

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nineintelligence.navigation.NavigationHolder
import com.example.nineintelligence.ui.theme.DeliverCustomFonts
import com.example.nineintelligence.ui.theme.MainBlueColor
import com.example.nineintelligence.ui.theme.MainYellowColor
import com.example.nineintelligence.ui.theme.Poppins
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BankSoal(controller: NavController, modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    BackHandler {
        if (pagerState.currentPage > 0) {
            scope.launch {
                pagerState.animateScrollToPage(0)
            }
            return@BackHandler
        }
        controller.popBackStack()
    }
    DeliverCustomFonts(font = Poppins.fonts) { font ->
        Column(modifier = modifier) {
            TopBar(onBackPress = {
                if (pagerState.currentPage > 0) {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage-1)
                    }
                    return@TopBar
                }
                controller.popBackStack()
            }, font = font)
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Bank Soal",
                    fontFamily = font,
                    fontWeight = FontWeight.Bold,
                    color = MainBlueColor, fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Halaman bank soal berisi kumpulan latihan " +
                            "soal dari tiap mata pelajar. Soal-soal yang tersedia " +
                            "sudah mengikuti kurikum terbaru.",
                    fontFamily = font,
                    color = MainBlueColor, fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalPager(
                    count = 2, state = pagerState,
                    userScrollEnabled = false
                ) { out ->
                    when (out) {
                        0 -> {
                            LazyColumn(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(bottom = 12.dp)
                            ) {
                                items(6) { each ->
                                    BankSoalItemList(
                                        font = font,
                                        studyName = "Computer Science", indexOf = each + 1,
                                        onItemClick = {
                                            scope.launch {
                                                pagerState.animateScrollToPage(1)
                                            }
                                        }, showButton = false, onButtonClick = {

                                        }
                                    )
                                }
                            }
                        }

                        1 -> {
                            LazyColumn(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(bottom = 12.dp)
                            ) {
                                items(6) { each ->
                                    BankSoalItemList(
                                        font = font,
                                        studyName = "Bank Soal ${each+1}", indexOf = each + 1,
                                        onItemClick = {

                                        }, showButton = true, onButtonClick = {
                                            controller.navigate(NavigationHolder.ExamScreen.route)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TopBar(onBackPress: () -> Unit, font: FontFamily) {
    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            onBackPress.invoke()
        }) {
            Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = null)
        }
        Text(text = "Back", fontSize = 16.sp, color = MainBlueColor, fontFamily = font)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BankSoalItemList(
    font: FontFamily, studyName: String, indexOf: Int,
    onItemClick: () -> Unit, showButton: Boolean, onButtonClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(1F)
            .height(70.dp)
            .padding(horizontal = 4.dp), colors = CardDefaults.cardColors(
            MainYellowColor
        ), elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(12.dp), onClick = {
            onItemClick.invoke()
        }
    ) {
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
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
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
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            if (showButton) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            onButtonClick.invoke()
                        }, modifier = Modifier
                            .fillMaxWidth(0.6F)
                            .height(28.dp), colors = ButtonDefaults.buttonColors(MainBlueColor)
                    ) {
                        Text(
                            text = "Mulai", fontFamily = font,
                            fontSize = 10.sp, color = MainYellowColor
                        )
                    }

                }

            }
        }
    }
}
