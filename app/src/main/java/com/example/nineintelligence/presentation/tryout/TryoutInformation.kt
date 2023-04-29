package com.example.nineintelligence.presentation.tryout

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.AvTimer
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.nineintelligence.R
import com.example.nineintelligence.core.CustomText
import com.example.nineintelligence.core.calculateTwoDaysBetween
import com.example.nineintelligence.core.toPreferrableFormatDate
import com.example.nineintelligence.domain.models.TryoutInfoDetail
import com.example.nineintelligence.domain.models.TryoutInfoModel
import com.example.nineintelligence.domain.models.calculateMapelAndData
import com.example.nineintelligence.navigation.NavigationHolder
import com.example.nineintelligence.ui.theme.MainBlueColor
import com.example.nineintelligence.ui.theme.MainYellowColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun TryoutInformation(
    modifier: Modifier = Modifier,
    controller: NavController,
    viewModel: TryOutInformationViewModel = koinViewModel(),
    slugname: String
) {
    var resultTimeBetween by remember {
        mutableStateOf(TryoutInfoModel())
    }
    LaunchedEffect(key1 = Unit, block = {
        viewModel.readTryOutRequiredInformation(slugname)
        viewModel.readSoalList(slugname)
    })
    val soalList by viewModel.getSoal.collectAsStateWithLifecycle()
    val tryoutInfo by viewModel.takenTryOutInformation.collectAsStateWithLifecycle()
    var tryOutSoalInfo: TryoutInfoDetail? by remember {
        mutableStateOf(null)
    }
    LaunchedEffect(key1 = soalList, block = {
        tryOutSoalInfo = calculateMapelAndData(soalList)
    })
    LaunchedEffect(key1 = true, block = {
        launch(Dispatchers.IO) {
            while (true) {
                delay(1000L)
                val res = calculateTwoDaysBetween(
                    tryoutInfo?.tryoutDetails?.startsAt ?: return@launch
                )
                resultTimeBetween = TryoutInfoModel(res[0], res[1], res[2])
            }
        }
    })
    Column(modifier) {
        TopBarMain(onBackPress = {
            controller.popBackStack()
        })
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(MainBlueColor),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomText(
                    text = stringResource(R.string.detail_tryout_title),
                    color = MainYellowColor,
                    fontWeight = FontWeight.Bold, fontSize = 17.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                CustomText(
                    text = tryoutInfo?.tryoutDetails?.tryOutTitle ?: "",
                    color = Color.White,
                    fontSize = 12.sp
                )
                CustomText(
                    text = "${tryoutInfo?.tryoutDetails?.startsAt?.toPreferrableFormatDate() ?: ""} " +
                            "until " +
                            (tryoutInfo?.tryoutDetails?.endsAt?.toPreferrableFormatDate() ?: ""),
                    color = Color.White,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Divider(modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.EditCalendar,
                        contentDescription = null,
                        tint = MainYellowColor, modifier = Modifier.size(17.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    CustomText(
                        text = stringResource(R.string.to_dimulai_title), color = Color.White,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.weight(1F))
                    CustomText(
                        text = if ((resultTimeBetween.days.toIntOrNull() ?: 0) <= 0 &&
                            (resultTimeBetween.hours.toIntOrNull() ?: 0) <= 0 &&
                            (resultTimeBetween.minutes.toIntOrNull() ?: 0) <= 0
                        )
                            stringResource(R.string.tryout_has_started) else
                            stringResource(
                                R.string.start_at_to_title,
                                resultTimeBetween.days,
                                resultTimeBetween.hours,
                                resultTimeBetween.minutes
                            ), color = Color.White,
                        fontSize = 13.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Divider(modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(modifier = Modifier.weight(1F)) {
                        Icon(
                            imageVector = Icons.Filled.AvTimer,
                            contentDescription = null,
                            modifier = Modifier.size(17.dp), tint = MainYellowColor
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        CustomText(
                            text = "Waktu Tryout",
                            color = Color.White,
                            fontSize = 13.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1F)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        CustomText(
                            text = "${tryoutInfo?.tryoutDetails?.duration ?: "0"}".take(3),
                            color = MainYellowColor,
                            fontSize = 13.sp, overflow = TextOverflow.Ellipsis, maxLines = 1
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        CustomText(text = "mnt", color = Color.White, fontSize = 13.sp)
                        Spacer(modifier = Modifier.width(2.dp))
                    }
                    Card(
                        modifier = Modifier
                            .width(1.dp)
                            .height(20.dp)

                    ) {}
                    Row(modifier = Modifier.weight(1F)) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Filled.EditCalendar,
                            contentDescription = null,
                            modifier = Modifier.size(17.dp),
                            tint = MainYellowColor
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        CustomText(text = "Jumlah Soal ", color = Color.White, fontSize = 13.sp)
                        Spacer(modifier = Modifier.weight(1F))
                        CustomText(text = "20", color = MainYellowColor, fontSize = 13.sp)
                        CustomText(text = " Soal", color = Color.White, fontSize = 13.sp)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                QuestionSection(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    tpsItem1 = tryOutSoalInfo?.mapelList?.penalaran ?: 0,
                    tpsItem2 = tryOutSoalInfo?.mapelList?.pengetahuanUmum ?: 0,
                    tpsItem3 = tryOutSoalInfo?.mapelList?.membacaMenulis ?: 0,
                    tpsItem4 = tryOutSoalInfo?.mapelList?.memahamiBacaanDanMenulis ?: 0,
                    tpsItem5 = tryOutSoalInfo?.mapelList?.literasiBahasaIndonesia ?: 0,
                    tpsItem6 = tryOutSoalInfo?.mapelList?.literasiBahasaInggris ?: 0,
                    tpsItem7 = tryOutSoalInfo?.mapelList?.penalaranMatematika ?: 0
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        controller.navigate(
                            NavigationHolder.ExamScreen.route + "/$slugname/" +
                                    "${tryoutInfo?.tryoutDetails?.duration}"
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(
                        MainYellowColor
                    )
                ) {
                    CustomText(
                        text = "Mulai",
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = MainBlueColor
                    )
                }
            }
        }
    }
}

@Composable
private fun QuestionSection(
    modifier: Modifier = Modifier,
    tpsItem1: Int = 0,
    tpsItem2: Int = 0,
    tpsItem3: Int = 0,
    tpsItem4: Int = 0,
    tpsItem5: Int = 0,
    tpsItem6: Int = 0,
    tpsItem7: Int = 0,
) {
    Card(
        modifier = modifier,
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 12.dp)
                .padding(
                    horizontal = 14.dp
                )
        ) {
            CustomText(
                text = stringResource(R.string.tps_title),
                fontWeight = FontWeight.Bold, color = MainYellowColor
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomText(
                    text = stringResource(R.string.penalaran_umum_title),
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.1.sp, fontSize = 13.sp, color = MainBlueColor
                )
                Spacer(modifier = Modifier.weight(1F))
                Icon(
                    imageVector = Icons.Filled.EditCalendar,
                    contentDescription = null,
                    modifier = Modifier.size(17.dp), tint = MainYellowColor
                )
                Spacer(modifier = Modifier.width(4.dp))
                CustomText(
                    text = "$tpsItem1 Soal",
                    letterSpacing = 0.1.sp, fontSize = 13.sp, color = MainBlueColor
                )
            }
            Divider()
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomText(
                    text = stringResource(R.string.pengetahuan_dan_pemahaman_umum_title),
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.1.sp, fontSize = 13.sp, color = MainBlueColor
                )
                Spacer(modifier = Modifier.weight(1F))
                Icon(
                    imageVector = Icons.Filled.EditCalendar,
                    contentDescription = null,
                    modifier = Modifier.size(17.dp), tint = MainYellowColor
                )
                Spacer(modifier = Modifier.width(4.dp))
                CustomText(
                    text = "$tpsItem2 Soal",
                    letterSpacing = 0.1.sp, fontSize = 13.sp, color = MainBlueColor
                )
            }
            Divider()
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomText(
                    text = stringResource(R.string.kemampuan_memahami_bacaan_dan_menulis_title),
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.1.sp, fontSize = 13.sp, color = MainBlueColor
                )
                Spacer(modifier = Modifier.weight(1F))
                Icon(
                    imageVector = Icons.Filled.EditCalendar,
                    contentDescription = null,
                    modifier = Modifier.size(17.dp), tint = MainYellowColor
                )
                Spacer(modifier = Modifier.width(4.dp))
                CustomText(
                    text = "$tpsItem3 Soal",
                    letterSpacing = 0.1.sp, fontSize = 13.sp, color = MainBlueColor
                )
            }
            Divider()
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomText(
                    text = stringResource(R.string.pengetahuan_kuantitatif_title),
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.1.sp, fontSize = 13.sp, color = MainBlueColor
                )
                Spacer(modifier = Modifier.weight(1F))
                Icon(
                    imageVector = Icons.Filled.EditCalendar,
                    contentDescription = null,
                    modifier = Modifier.size(17.dp), tint = MainYellowColor
                )
                Spacer(modifier = Modifier.width(4.dp))
                CustomText(
                    text = "$tpsItem4 Soal",
                    letterSpacing = 0.1.sp, fontSize = 13.sp, color = MainBlueColor
                )
            }
            Divider()
            Spacer(modifier = Modifier.height(12.dp))
            CustomText(
                text = stringResource(R.string.tes_literasi_title),
                fontWeight = FontWeight.Bold, color = MainYellowColor,
                letterSpacing = 0.1.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomText(
                    text = stringResource(R.string.literasi_dalam_bahasa_indonesia_title),
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.1.sp, fontSize = 13.sp, color = MainBlueColor
                )
                Spacer(modifier = Modifier.weight(1F))
                Icon(
                    imageVector = Icons.Filled.EditCalendar,
                    contentDescription = null,
                    modifier = Modifier.size(17.dp), tint = MainYellowColor
                )
                Spacer(modifier = Modifier.width(4.dp))
                CustomText(
                    text = "$tpsItem5 Soal",
                    letterSpacing = 0.1.sp, fontSize = 13.sp, color = MainBlueColor
                )
            }
            Divider()
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomText(
                    text = stringResource(R.string.literasi_dalam_bahasa_inggris_title),
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.1.sp, fontSize = 13.sp, color = MainBlueColor
                )
                Spacer(modifier = Modifier.weight(1F))
                Icon(
                    imageVector = Icons.Filled.EditCalendar,
                    contentDescription = null,
                    modifier = Modifier.size(17.dp), tint = MainYellowColor
                )
                Spacer(modifier = Modifier.width(4.dp))
                CustomText(
                    text = "$tpsItem6 Soal",
                    letterSpacing = 0.1.sp, fontSize = 13.sp, color = MainBlueColor
                )
            }
            Divider()
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomText(
                    text = stringResource(R.string.penalaran_matematika_title),
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.1.sp, fontSize = 13.sp, color = MainBlueColor
                )
                Spacer(modifier = Modifier.weight(1F))
                Icon(
                    imageVector = Icons.Filled.EditCalendar,
                    contentDescription = null,
                    modifier = Modifier.size(17.dp), tint = MainYellowColor
                )
                Spacer(modifier = Modifier.width(4.dp))
                CustomText(
                    text = "$tpsItem7 Soal",
                    letterSpacing = 0.1.sp, fontSize = 13.sp, color = MainBlueColor
                )
            }
            Divider()
            Spacer(modifier = Modifier.height(4.dp))
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