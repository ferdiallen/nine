package com.example.nineintelligence.presentation.profile

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.nineintelligence.R
import com.example.nineintelligence.ui.theme.DeliverCustomFonts
import com.example.nineintelligence.ui.theme.MainBlueColor
import com.example.nineintelligence.ui.theme.MainYellowColor
import com.example.nineintelligence.ui.theme.Poppins
import com.example.nineintelligence.ui.theme.StrongGreen
import com.example.nineintelligence.ui.theme.StrongYellow
import com.example.nineintelligence.ui.theme.WeakGreen
import com.example.nineintelligence.ui.theme.WeakYellow
import com.example.nineintelligence.util.ActivityType
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.marosseleng.compose.material3.datetimepickers.date.ui.dialog.DatePickerDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.time.format.DateTimeFormatter
import kotlin.random.Random


private val tabNameList = listOf("Statistik", "Kegiatanku", "History")
private val genderList = listOf("Laki-laki", "Perempuan")

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProfileScreen(modifier: Modifier = Modifier, onBackPress: () -> Unit) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    var shouldShowEditMenu by remember {
        mutableStateOf(false)
    }
    var shouldShowSettingsMenu by remember {
        mutableStateOf(false)
    }
    DeliverCustomFonts(font = Poppins.fonts) { font ->
        Column(
            modifier = modifier
                .animateContentSize(tween(100)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBarMain(font, onBackPress = {
                onBackPress.invoke()
            })
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 40.dp),
                    border = BorderStroke(1.dp, Color.Gray),
                    colors = CardDefaults.cardColors(
                        Color.Transparent
                    ),
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp)
                            .padding(top = 8.dp), horizontalArrangement = Arrangement.Center
                    ) {
                        IconButton(onClick = {
                            shouldShowSettingsMenu = !shouldShowSettingsMenu
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                            )

                        }
                        Spacer(modifier = Modifier.weight(1F))
                        IconButton(onClick = {
                            shouldShowEditMenu = !shouldShowEditMenu
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                            )

                        }
                    }
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = R.drawable.generated,
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Adyatma Prabowo",
                        fontSize = 18.sp,
                        fontFamily = font,
                        fontWeight = FontWeight.Bold,
                        color = MainYellowColor
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.2F),
                        shape = RoundedCornerShape(0.dp),
                        colors = CardDefaults.cardColors(MainYellowColor)
                    ) {
                        Column(Modifier.fillMaxSize()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                                    .padding(top = 8.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Jln. Tawangsari No. 44, Lawang, Kab. Malang",
                                    fontSize = 12.sp,
                                    fontFamily = font,
                                    modifier = Modifier.width(113.dp),
                                    textAlign = TextAlign.Center,
                                    lineHeight = 15.sp,
                                    color = MainBlueColor
                                )
                                Spacer(modifier = Modifier.weight(1F))
                                Text(
                                    text = "Bogor, 10 Oktober 2000",
                                    fontSize = 12.sp,
                                    fontFamily = font,
                                    modifier = Modifier.width(113.dp),
                                    textAlign = TextAlign.Center,
                                    lineHeight = 15.sp,
                                    color = MainBlueColor
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "test@gmail.com",
                                fontSize = 12.sp,
                                fontFamily = font,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                lineHeight = 15.sp,
                                color = MainBlueColor
                            )
                        }
                    }
                    TabRow(selectedTabIndex = pagerState.currentPage) {
                        tabNameList.forEachIndexed { index, string ->
                            Tab(selected = pagerState.currentPage == index, onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            }, text = {
                                Text(
                                    text = string,
                                    fontFamily = font,
                                    fontWeight = if (pagerState.currentPage == index) FontWeight.Bold
                                    else FontWeight.Normal
                                )
                            })
                        }
                    }
                    var isUserScrolled by remember {
                        mutableStateOf(true)
                    }
                    HorizontalPager(
                        count = tabNameList.size,
                        state = pagerState,
                        userScrollEnabled = !isUserScrolled
                    ) { page ->
                        when (page) {
                            0 -> StatisticScreen(font) {
                                isUserScrolled = it
                            }

                            1 -> ActivityTab(font = font, type = ActivityType.MYACTIVITY)
                            2 -> ActivityTab(font = font, type = ActivityType.DISCUSSION)
                        }
                    }
                }

            }
        }

        if (shouldShowEditMenu) {
            ProfileEdit(
                modifier = Modifier.padding(32.dp),
                onSaved = { },
                font = font,
                onTapExit = {
                    shouldShowEditMenu = false
                }, true
            )
        }
        if (shouldShowSettingsMenu) {
            SettingsMenu(
                font = font,
                onTapExit = { shouldShowSettingsMenu = false },
                enableOnDismiss = true, modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5F)
                    .padding(24.dp)
            )
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

@Composable
private fun StatisticScreen(
    font: FontFamily,
    isScrolling: (Boolean) -> Unit
) {
    val scrollState = rememberLazyListState()
    LaunchedEffect(key1 = scrollState.isScrollInProgress, block = {
        if (scrollState.isScrollInProgress) {
            isScrolling.invoke(true)
        } else {
            isScrolling.invoke(false)
        }
    })
    LazyColumn(
        modifier = Modifier
            .wrapContentSize()
            .padding(top = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.8F)
                    .fillMaxHeight(0.15F),
                colors = CardDefaults.cardColors(WeakYellow)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.SignalCellularAlt,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = StrongYellow
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "1",
                            fontFamily = font,
                            fontSize = 12.sp,
                            color = StrongYellow,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Tryout yang belum diselesaikan",
                            fontFamily = font,
                            fontSize = 12.sp,
                            color = StrongYellow
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.8F)
                    .fillMaxHeight(0.15F),
                colors = CardDefaults.cardColors(WeakGreen)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.TaskAlt,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = StrongGreen
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "1",
                            fontFamily = font,
                            fontSize = 12.sp,
                            color = StrongGreen,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Tryout yang sudah diselesaikan",
                            fontFamily = font,
                            fontSize = 12.sp,
                            color = StrongGreen
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 38.dp)
            ) {
                Text(
                    text = "Statistik",
                    fontFamily = font,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier.fillMaxWidth(),
                    color = MainBlueColor
                )
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16F / 9F)
                ) {
                    var animateInt by remember {
                        mutableStateOf(0)
                    }
                    LazyRow(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Bottom,
                        contentPadding = PaddingValues(horizontal = 8.dp),
                        state = scrollState
                    ) {
                        items(20) {
                            LaunchedEffect(key1 = Unit, block = {
                                animateInt = Random.nextInt(10, 100)
                            })
                            val animatedValue = animateIntAsState(
                                targetValue = animateInt,
                                tween(450)
                            )
                            ItemsChart(
                                animatedValue.value
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Tryout",
                    fontFamily = font,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Light,
                    color = MainBlueColor
                )
            }

        }
    }
}

@Composable
private fun ItemsChart(result: Int, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .width(30.dp)
                .fillMaxHeight(result / 100F),
            colors = CardDefaults.cardColors(MainYellowColor),
            shape = RoundedCornerShape(
                topStart = 8.dp, topEnd = 8.dp, bottomStart = 4.dp, bottomEnd = 4.dp
            )
        ) {}
    }
}

@Composable
private fun ActivityTab(font: FontFamily, type: ActivityType) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 12.dp)
        ) {
            items(5) {
                ActivityList(
                    font = font,
                    tryOutName = "Try out 1",
                    startDate = "1 Januari 1111",
                    indexOf = it,
                    onClick = {

                    },
                    activityType = type
                )
            }
        }
    }
}

@Composable
private fun ActivityList(
    font: FontFamily,
    tryOutName: String,
    startDate: String,
    indexOf: Int,
    activityType: ActivityType,
    onClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.85F)
            .height(90.dp), colors = CardDefaults.cardColors(
            MainYellowColor
        ), elevation = CardDefaults.cardElevation(8.dp), shape = RoundedCornerShape(12.dp)
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
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = indexOf.toString(),
                        fontSize = 12.sp,
                        fontFamily = font,
                        fontWeight = FontWeight.Bold,
                        color = MainYellowColor
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = tryOutName, fontFamily = font, fontSize = 12.sp, fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1F))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(3F),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = startDate, fontFamily = font, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { },
                    modifier = Modifier
                        .wrapContentWidth()
                        .fillMaxHeight(0.5F),
                    colors = ButtonDefaults.buttonColors(MainBlueColor)
                ) {
                    Text(
                        text = when (activityType) {
                            ActivityType.MYACTIVITY -> stringResource(id = R.string.start)
                            ActivityType.DISCUSSION -> stringResource(id = R.string.discussion)
                        }, fontFamily = font, fontSize = 12.sp, color = MainYellowColor
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
private fun ProfileEdit(
    modifier: Modifier = Modifier,
    onSaved: () -> Unit, font: FontFamily,
    onTapExit: () -> Unit, enableOnDismiss: Boolean
) {
    var animatedFloat by remember {
        mutableStateOf(0.01F)
    }
    val animateBackground = animateFloatAsState(
        targetValue = animatedFloat, tween(250)
    )
    var shouldExpandGenderList by remember {
        mutableStateOf(false)
    }
    var selectedGender by remember {
        mutableStateOf("")
    }
    var userName by remember {
        mutableStateOf("")
    }
    var address by remember {
        mutableStateOf("")
    }
    var phoneNumber by remember {
        mutableStateOf("")
    }
    var shouldShowDatePicker by remember {
        mutableStateOf(false)
    }
    var birthDate by remember {
        mutableStateOf("")
    }
    LaunchedEffect(key1 = Unit, block = {
        delay(250)
        animatedFloat = 0.7F
    })
    LaunchedEffect(key1 = animateBackground.value, block = {
        if (animateBackground.value == 0F && enableOnDismiss) {
            onTapExit.invoke()
        }
    })
    if (shouldShowDatePicker) {
        DatePickerDialog(onDismissRequest = {
            shouldShowDatePicker = false
        }, onDateChange = {
            birthDate = it.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            shouldShowDatePicker = false
        })
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(animateBackground.value))
            .clickable(interactionSource = MutableInteractionSource(),
                indication = null, onClick = {
                    if (enableOnDismiss) {
                        animatedFloat = 0F
                    }
                }
            )
    ) {
        AnimatedVisibility(
            visible = animateBackground.value == 0.7F,
            enter = fadeIn(tween(250)),
            exit = fadeOut(tween(200))
        ) {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
                Card(
                    modifier = Modifier
                        .wrapContentSize()
                        .then(modifier)
                        .clickable(MutableInteractionSource(), null,
                            false, onClick = {

                            }),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .wrapContentHeight()
                            .statusBarsPadding()
                            .navigationBarsPadding()
                            .imePadding()
                            .padding(12.dp)
                    ) {
                        item {
                            Text(
                                text = "Ubah Profile",
                                fontFamily = font,
                                fontSize = 20.sp,
                                color = MainBlueColor,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = R.drawable.generated,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(CircleShape)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Card(
                                    modifier = Modifier
                                        .size(140.dp, 50.dp),
                                    border = BorderStroke(1.dp, Color.Black), onClick = {

                                    }
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Upload,
                                            contentDescription = null
                                        )
                                        Text(
                                            text = "Ubah Foto",
                                            fontFamily = font,
                                            fontWeight = FontWeight.Light
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = "Nama", fontFamily = font,
                                fontSize = 16.sp, fontWeight = FontWeight.Light,
                                color = MainBlueColor
                            )
                            OutlinedTextField(
                                value = userName,
                                onValueChange = {
                                    userName = it
                                },
                                shape = RoundedCornerShape(12.dp),
                                textStyle = TextStyle(fontFamily = font),
                                singleLine = true, placeholder = {
                                    Text(
                                        text = "Masukkan Nama",
                                        fontFamily = font,
                                        color = Color.LightGray
                                    )
                                }, modifier = Modifier.fillMaxWidth(0.9F)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Jenis Kelamin", fontFamily = font,
                                fontSize = 16.sp, fontWeight = FontWeight.Light,
                                color = MainBlueColor
                            )
                            Column {
                                var sizeTextField by remember {
                                    mutableStateOf(0.dp)
                                }
                                val density = LocalDensity.current
                                OutlinedTextField(modifier = Modifier
                                    .onSizeChanged {
                                        with(density) {
                                            sizeTextField = it.width.toDp()
                                        }
                                    }
                                    .fillMaxWidth(0.9F),
                                    value = selectedGender,
                                    onValueChange = {},
                                    shape = RoundedCornerShape(12.dp),
                                    textStyle = TextStyle(fontFamily = font),
                                    singleLine = true, placeholder = {
                                        Text(
                                            text = "Pilih Jenis Kelamin",
                                            fontFamily = font,
                                            color = Color.LightGray
                                        )
                                    }, trailingIcon = {
                                        IconButton(onClick = {
                                            shouldExpandGenderList = !shouldExpandGenderList
                                        }) {
                                            Icon(
                                                imageVector = Icons.Filled.ArrowBackIosNew,
                                                contentDescription = null,
                                                modifier = Modifier.rotate(
                                                    if (shouldExpandGenderList) 270F else 90F
                                                )
                                            )
                                        }
                                    }, readOnly = true
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                DropdownMenu(
                                    expanded = shouldExpandGenderList,
                                    onDismissRequest = {
                                        shouldExpandGenderList = false
                                    },
                                    modifier = Modifier.width(sizeTextField)
                                ) {
                                    genderList.forEachIndexed { index, out ->
                                        Text(
                                            text = out,
                                            fontFamily = font,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    selectedGender = out
                                                    shouldExpandGenderList = false
                                                }
                                                .padding(horizontal = 20.dp)
                                                .padding(bottom = 8.dp)
                                        )
                                        if (index < genderList.size - 1) {
                                            Divider()
                                        }
                                    }
                                }

                            }
                            Text(
                                text = "Alamat", fontFamily = font,
                                fontSize = 16.sp, fontWeight = FontWeight.Light,
                                color = MainBlueColor
                            )
                            OutlinedTextField(
                                value = address,
                                onValueChange = {
                                    address = it
                                },
                                shape = RoundedCornerShape(12.dp),
                                textStyle = TextStyle(fontFamily = font),
                                singleLine = true, placeholder = {
                                    Text(
                                        text = "Masukkan Alamat",
                                        fontFamily = font,
                                        color = Color.LightGray
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(0.9F)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Tanggal Lahir", fontFamily = font,
                                fontSize = 16.sp, fontWeight = FontWeight.Light,
                                color = MainBlueColor
                            )
                            OutlinedTextField(
                                value = birthDate,
                                onValueChange = {

                                },
                                shape = RoundedCornerShape(12.dp),
                                textStyle = TextStyle(fontFamily = font),
                                singleLine = true, placeholder = {
                                    Text(
                                        text = "Masukkan Tanggal Lahir",
                                        fontFamily = font,
                                        color = Color.LightGray
                                    )
                                }, trailingIcon = {
                                    IconButton(onClick = {
                                        shouldShowDatePicker = !shouldShowDatePicker
                                    }) {
                                        Icon(
                                            imageVector = Icons.Filled.CalendarMonth,
                                            contentDescription = null
                                        )
                                    }
                                }, readOnly = true,
                                modifier = Modifier.fillMaxWidth(0.9F)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "No. Telp", fontFamily = font,
                                fontSize = 16.sp, fontWeight = FontWeight.Light,
                                color = MainBlueColor
                            )
                            OutlinedTextField(
                                value = phoneNumber,
                                onValueChange = {
                                    phoneNumber = it
                                },
                                shape = RoundedCornerShape(12.dp),
                                textStyle = TextStyle(fontFamily = font),
                                singleLine = true, placeholder = {
                                    Text(
                                        text = "Masukkan Nomor Telepon",
                                        fontFamily = font,
                                        color = Color.LightGray
                                    )
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth(0.9F)
                            )
                            Spacer(modifier = Modifier.heightIn(12.dp))
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Bottom
                            ) {
                                Button(
                                    onClick = {
                                        onSaved.invoke()
                                        animatedFloat = 0F
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(40.dp, 70.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(MainBlueColor)
                                ) {
                                    Text(
                                        text = "Simpan",
                                        fontSize = 16.sp,
                                        fontFamily = font,
                                        fontWeight = FontWeight.Bold, color = MainYellowColor
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }

            }

        }

    }
}

@Composable
private fun SettingsMenu(
    modifier: Modifier = Modifier,
    font: FontFamily,
    onTapExit: () -> Unit,
    enableOnDismiss: Boolean
) {
    var animatedFloat by remember {
        mutableStateOf(0.01F)
    }
    val animateBackground = animateFloatAsState(
        targetValue = animatedFloat, tween(250)
    )
    LaunchedEffect(key1 = Unit, block = {
        delay(250)
        animatedFloat = 0.7F
    })
    LaunchedEffect(key1 = animateBackground.value, block = {
        if (animateBackground.value == 0F && enableOnDismiss) {
            onTapExit.invoke()
        }
    })
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(animateBackground.value))
            .clickable(interactionSource = MutableInteractionSource(),
                indication = null, onClick = {
                    if (enableOnDismiss) {
                        animatedFloat = 0F
                    }
                }
            )
    ) {
        AnimatedVisibility(
            visible = animateBackground.value == 0.7F,
            enter = fadeIn(tween(250)),
            exit = fadeOut(tween(400))
        ) {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
                Card(
                    modifier = Modifier
                        .wrapContentSize()
                        .then(modifier)
                        .clickable(MutableInteractionSource(), null,
                            false, onClick = {

                            }),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .wrapContentHeight()
                            .statusBarsPadding()
                            .navigationBarsPadding()
                            .imePadding()
                            .padding(12.dp)
                            .padding(horizontal = 12.dp)
                    ) {
                        item {
                            Text(
                                text = "Pengaturan",
                                color = MainBlueColor,
                                fontFamily = font,
                                fontWeight = FontWeight.Bold, fontSize = 20.sp
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = "Logout", color = MainBlueColor,
                                fontFamily = font, fontSize = 17.sp
                            )
                        }
                    }
                }

            }

        }

    }
}