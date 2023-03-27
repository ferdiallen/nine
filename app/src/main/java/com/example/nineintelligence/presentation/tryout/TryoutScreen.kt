package com.example.nineintelligence.presentation.tryout

import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.nineintelligence.R
import com.example.nineintelligence.core.CustomText
import com.example.nineintelligence.core.toPreferrableFormatDate
import com.example.nineintelligence.core.toSimpleDate
import com.example.nineintelligence.domain.models.TryoutDataModel
import com.example.nineintelligence.ui.theme.MainBlueColor
import com.example.nineintelligence.ui.theme.MainYellowColor
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TryoutScreen(
    modifier: Modifier = Modifier,
    viewModel: TryoutViewModel = koinViewModel(),
    controller: NavController
) {
    val tryoutData by viewModel.tryoutState.collectAsStateWithLifecycle()
    val selected = remember {
        mutableStateListOf<Int?>()
    }
    Column(modifier) {
        TopBarMain {
            controller.popBackStack()
        }
        Spacer(modifier = Modifier.height(12.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomText(
                text = stringResource(R.string.tryout_screen_title),
                fontWeight = FontWeight.Bold,
                color = MainBlueColor,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            CustomText(
                text = stringResource(R.string.tryout_screen_desc),
                color = MainBlueColor, textAlign = TextAlign.Justify, letterSpacing = 0.1.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 12.dp)
            ) {
                itemsIndexed(tryoutData) { index, it ->
                    TryOutItem(data = it, onSignClick = {
                        selected.add(index)
                    }, hasIndex = selected.contains(index))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { }, colors = ButtonDefaults.buttonColors(MainYellowColor),
                shape = RoundedCornerShape(10.dp)
            ) {
                CustomText(
                    text = "Daftar",
                    color = MainBlueColor,
                    modifier = Modifier.padding(horizontal = 20.dp), fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun TopBarMain(onBackPress: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            onBackPress.invoke()
        }) {
            Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = null)
        }
        CustomText(text = "Back", fontSize = 16.sp, color = MainBlueColor)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TryOutItem(
    data: TryoutDataModel,
    onSignClick: () -> Unit, hasIndex: Boolean = false
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(), colors = CardDefaults.cardColors(MainBlueColor),
        onClick = {

        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
                .padding(vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(2F)
                        .padding(end = 10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box {
                            Card(modifier = Modifier.size(80.dp, 70.dp)) {

                            }
                            Card(
                                modifier = Modifier.size(80.dp, 70.dp),
                                colors = CardDefaults.cardColors(
                                    MainYellowColor.copy(0.5F)
                                )
                            ) {

                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(

                        ) {
                            CustomText(
                                text = data.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp, color = MainYellowColor
                            )
                            CustomText(
                                text = data.startTime.toPreferrableFormatDate(),
                                fontSize = 12.sp, color = MainYellowColor
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    CustomText(
                        text = "Tryout hanya dapat dilakukan mulai " +
                                "jam 00.01 WIB - 23.59 WIB tanggal ${data.startTime.toSimpleDate()}",
                        color = MainYellowColor,
                        fontSize = 11.sp,
                        textAlign = TextAlign.Justify,
                        letterSpacing = 0.01.sp
                    )
                }
                Button(
                    onClick = {
                              onSignClick.invoke()
                    },
                    enabled = !hasIndex,
                    colors = ButtonDefaults.buttonColors(
                        MainYellowColor,
                        disabledContainerColor = Color.Gray
                    ),
                    modifier = Modifier.weight(0.9F)
                ) {
                    CustomText(text = "Daftar", fontWeight = FontWeight.Bold, color = MainBlueColor)
                }
            }
        }
    }
}