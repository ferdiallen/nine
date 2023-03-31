package com.example.nineintelligence.presentation.tryout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nineintelligence.R
import com.example.nineintelligence.core.CustomText
import com.example.nineintelligence.ui.theme.MainBlueColor
import com.example.nineintelligence.ui.theme.MainYellowColor

@Composable
fun TryoutInformation(
    modifier: Modifier = Modifier,
    controller: NavController
) {
    Column(modifier) {
        TopBarMain(onBackPress = {})
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
                    text = "Tryout 1 NineIntelligence",
                    color = Color.White,
                    fontSize = 12.sp
                )
                CustomText(
                    text = "Starts date until ends date", color = Color.White,
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
                        text = stringResource(
                            R.string.start_at_to_title,
                            1, 12, 10
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
                    Icon(
                        imageVector = Icons.Filled.AvTimer,
                        contentDescription = null,
                        modifier = Modifier.size(17.dp), tint = MainYellowColor
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    CustomText(text = "Waktu Tryout", color = Color.White, fontSize = 13.sp)
                    Spacer(modifier = Modifier.width(2.dp))
                    CustomText(text = "60", color = MainYellowColor, fontSize = 13.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    CustomText(text = "mnt", color = Color.White, fontSize = 13.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Card(
                        modifier = Modifier
                            .width(1.dp)
                            .height(20.dp)
                    ) {}
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
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                QuestionSection(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { },
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
    modifier: Modifier = Modifier
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
                    text = "0 Soal",
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
                    text = "0 Soal",
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
                    text = "0 Soal",
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
                    text = "0 Soal",
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
                    text = "0 Soal",
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
                    text = "0 Soal",
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
                    text = "0 Soal",
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