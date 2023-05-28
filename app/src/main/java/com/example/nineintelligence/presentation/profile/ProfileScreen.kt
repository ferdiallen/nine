package com.example.nineintelligence.presentation.profile

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.nineintelligence.R
import com.example.nineintelligence.core.CustomText
import com.example.nineintelligence.core.isTryoutOver
import com.example.nineintelligence.core.toPreferrableFormatDate
import com.example.nineintelligence.domain.models.HistoryBankSoalTryout
import com.example.nineintelligence.domain.models.HistoryModel
import com.example.nineintelligence.domain.models.TakenBankSoal
import com.example.nineintelligence.domain.models.TakenTryOutModel
import com.example.nineintelligence.domain.models.UpdateProfileModel
import com.example.nineintelligence.domain.models.UserProfileModel
import com.example.nineintelligence.domain.util.ActivityType
import com.example.nineintelligence.domain.util.ExamType
import com.example.nineintelligence.domain.util.SettingsModel
import com.example.nineintelligence.domain.util.TakenTryoutIdentifier.wheterContaintsTakenTryout
import com.example.nineintelligence.navigation.NavigationHolder
import com.example.nineintelligence.ui.theme.DeliverCustomFonts
import com.example.nineintelligence.ui.theme.MainBlueColor
import com.example.nineintelligence.ui.theme.MainYellowColor
import com.example.nineintelligence.ui.theme.Poppins
import com.example.nineintelligence.ui.theme.StrongGreen
import com.example.nineintelligence.ui.theme.StrongYellow
import com.example.nineintelligence.ui.theme.WeakGreen
import com.example.nineintelligence.ui.theme.WeakYellow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.marosseleng.compose.material3.datetimepickers.date.ui.dialog.DatePickerDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.time.format.DateTimeFormatter


private val tabNameList = listOf("Statistik", "Kegiatanku", "History")
private val genderList = listOf("Laki-laki", "Perempuan")
private val settingsList = listOf(
    SettingsModel("Edit Profile", Icons.Filled.Edit),
    SettingsModel("Log Out", Icons.Filled.Logout)
)

private val subActivityList = listOf("Tryout", "Banksoal")

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onBackPress: () -> Unit,
    viewModel: ProfileViewModel = koinViewModel(),
    onLogoutAction: () -> Unit, controller: NavController
) {
    val pagerState = rememberPagerState()
    var shouldShowEditMenu by remember {
        mutableStateOf(false)
    }
    var shouldShowSettingsMenu by remember {
        mutableStateOf(false)
    }
    val userDataInfo by viewModel.userDataInfo.collectAsStateWithLifecycle()
    val showLoadingProgress by viewModel.shouldShowLoadingScreen.collectAsStateWithLifecycle()
    val takenTryOutData by viewModel.listTakenTryOutModel.collectAsStateWithLifecycle()
    val userHistory by viewModel.userHistory.collectAsStateWithLifecycle()
    val takenBankSoalList by viewModel.takenBankSoal.collectAsStateWithLifecycle()
    DeliverCustomFonts(font = Poppins.fonts) { font ->
        ChildProfileScreen(
            modifier,
            font = font,
            onBackPress = { onBackPress.invoke() },
            shouldShowSettingsMenu = {
                shouldShowSettingsMenu = !shouldShowSettingsMenu
            },
            userDataInfo = userDataInfo,
            pagerState = pagerState, takenTryOutData, controller = controller,
            userHistory, takenBankSoal = takenBankSoalList
        )
    }
    if (shouldShowSettingsMenu) {
        SettingsMenu(
            font = Poppins.fonts,
            onTapExit = { shouldShowSettingsMenu = false },
            enableOnDismiss = true, modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5F)
                .padding(24.dp), onSelected = {
                when (it) {
                    "Log Out" -> {
                        viewModel.clearData()
                        onLogoutAction.invoke()
                    }

                    "Edit Profile" -> {
                        shouldShowEditMenu = !shouldShowEditMenu
                    }
                }
            }
        )
    }
    if (shouldShowEditMenu) {
        ProfileEdit(
            modifier = Modifier.padding(32.dp),
            onSaved = {
                viewModel.updateData(
                    userDataInfo?.userId.toString(),
                    it.userName.toString(),
                    userDataInfo?.userEmail.toString(),
                    it.phone.toString(),
                    it.address.toString(),
                    "p",
                    it.gender.toString()
                )
                shouldShowEditMenu = false
            },
            font = Poppins.fonts,
            onTapExit = {
                shouldShowEditMenu = false
            }, true, currentData = userDataInfo
        )
    }
    if (showLoadingProgress) {
        Dialog(onDismissRequest = { }) {
            CircularProgressIndicator()
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun ChildProfileScreen(
    modifier: Modifier = Modifier,
    font: FontFamily,
    onBackPress: () -> Unit,
    shouldShowSettingsMenu: () -> Unit,
    userDataInfo: UserProfileModel?,
    pagerState: PagerState,
    takenTryOut: List<TakenTryOutModel>, controller: NavController,
    history: HistoryBankSoalTryout?, takenBankSoal: List<TakenBankSoal> = emptyList()
) {
    val scope = rememberCoroutineScope()
    val hasNotTakenTryout = remember(history) {
        history?.tryoutContent?.wheterContaintsTakenTryout(takenTryOut) ?: 0
    }
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
                    Spacer(modifier = Modifier.weight(1F))
                    IconButton(onClick = {
                        shouldShowSettingsMenu.invoke()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
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
                        .clip(CircleShape)
                        .clickable {

                        },
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = userDataInfo?.userName ?: "",
                    fontSize = 18.sp,
                    fontFamily = font,
                    fontWeight = FontWeight.Bold,
                    color = MainYellowColor
                )
                Text(
                    text = userDataInfo?.userEmail ?: "",
                    fontSize = 12.sp,
                    fontFamily = font,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    lineHeight = 15.sp,
                    color = MainBlueColor
                )
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.15F),
                    shape = RoundedCornerShape(0.dp),
                    colors = CardDefaults.cardColors(MainYellowColor)
                ) {
                    Column(
                        Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(horizontalArrangement = Arrangement.Center) {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = "",
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = userDataInfo?.address ?: "",
                                fontSize = 12.sp,
                                fontFamily = font,
                                modifier = Modifier.width(113.dp),
                                textAlign = TextAlign.Center,
                                lineHeight = 15.sp,
                                color = MainBlueColor, maxLines = 1
                            )
                        }
                        Row(horizontalArrangement = Arrangement.Center) {
                            Icon(
                                imageVector = Icons.Filled.CalendarMonth,
                                contentDescription = "",
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Coming Soon",
                                fontSize = 12.sp,
                                fontFamily = font,
                                modifier = Modifier.width(113.dp),
                                textAlign = TextAlign.Center,
                                lineHeight = 15.sp,
                                color = MainBlueColor, maxLines = 1
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
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
                                else FontWeight.Normal, maxLines = 1,
                                overflow = TextOverflow.Ellipsis
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
                        0 -> StatisticScreen(
                            font,
                            history = history?.tryoutContent ?: emptyList(),
                            isScrolling = {
                                isUserScrolled = it
                            },
                            unfinishedTryout = hasNotTakenTryout
                        )

                        1 -> ActivityTab(
                            font = font,
                            type = ActivityType.MYACTIVITY,
                            takenTryOut,
                            secondData = history?.tryoutContent ?: emptyList(),
                            navigateToExamScreen = {
                                controller.navigate(NavigationHolder.ExamScreen.route + "/$it/${0}/${ExamType.BANK_SOAL}")
                            },
                            navigateToTryOutInformation = {
                                controller.navigate(
                                    NavigationHolder.TryoutInformation.route
                                            + "/$it"
                                )
                            },
                            bankSoalModel = takenBankSoal
                        )

                        2 -> ActivityTab(
                            font = font,
                            type = ActivityType.DISCUSSION,
                            history?.tryoutContent ?: emptyList(), navigateToExamScreen = {

                            }, navigateToTryOutInformation = {
                                controller.navigate(NavigationHolder.QuestionDiscussion.route
                                        + "/$it")
                            }
                        )
                    }
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

@Composable
private fun StatisticScreen(
    font: FontFamily,
    isScrolling: (Boolean) -> Unit, history: List<HistoryModel>, unfinishedTryout: Int = 0
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
                            text = "$unfinishedTryout",
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
                            text = "${history.size}",
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
                    LazyRow(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Bottom,
                        contentPadding = PaddingValues(horizontal = 8.dp),
                        state = scrollState
                    ) {
                        items(history) {
                            var animateScore by remember {
                                mutableStateOf(0)
                            }
                            LaunchedEffect(key1 = Unit, block = {
                                animateScore = it.hasilTryout?.score ?: 0
                            })
                            val animatedValue = animateIntAsState(
                                targetValue = animateScore,
                                tween(250), label = ""
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

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun <T> ActivityTab(
    font: FontFamily,
    type: ActivityType,
    data: List<T>,
    secondData: List<HistoryModel> = emptyList(),
    bankSoalModel: List<TakenBankSoal> = emptyList(),
    navigateToExamScreen: (String) -> Unit,
    navigateToTryOutInformation: (String) -> Unit
) {
    val context = LocalContext.current
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxSize()) {
        if (type == ActivityType.MYACTIVITY) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.wrapContentHeight()
            ) {
                subActivityList.forEachIndexed { index, string ->
                    Tab(
                        selected = string == subActivityList[pagerState.currentPage],
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }) {
                        CustomText(
                            text = string,
                            fontWeight = if (index == pagerState.currentPage) FontWeight.Bold
                            else FontWeight.Normal,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }
            HorizontalPager(
                count = subActivityList.size,
                state = pagerState
            ) {
                when (it) {
                    0 -> {
                        LazyColumn(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(top = 12.dp, bottom = 16.dp)
                        ) {
                            item {
                                CustomText(
                                    text = "Ongoing",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = MainBlueColor,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 30.dp),
                                    textAlign = TextAlign.Start
                                )
                            }
                            items(data.filterIsInstance<TakenTryOutModel>().filter { out ->
                                out.details == "Ongoing"
                            }) { data ->
                                ActivityList(
                                    font = font,
                                    tryOutName = data.tryoutDetails?.tryOutTitle ?: "",
                                    startDate = data.tryoutDetails?.startsAt?.toPreferrableFormatDate()
                                        ?: "",
                                    onClick = {
                                        if (secondData.find { out ->
                                                out.tryoutDetails?.tryOutSlug == data.tryoutDetails?.tryOutSlug
                                            } != null) {
                                            Toast.makeText(
                                                context,
                                                "You have submitted this tryout",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            return@ActivityList
                                        }
                                        navigateToTryOutInformation.invoke(
                                            data.tryoutDetails?.tryOutSlug ?: return@ActivityList
                                        )
                                    },
                                    activityType = type, buttonEnabled = true, onCardClick = {

                                    }
                                )
                            }
                            item {
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                            item {
                                CustomText(
                                    text = "Upcoming",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = MainBlueColor,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 30.dp),
                                    textAlign = TextAlign.Start
                                )
                            }
                            items(data.filterIsInstance<TakenTryOutModel>().filter { out ->
                                out.details == "Upcoming" && out.tryoutDetails?.endsAt?.isTryoutOver() == false
                            }) {
                                ActivityList(
                                    font = font,
                                    tryOutName = it.tryoutDetails?.tryOutTitle.toString(),
                                    startDate = it.tryoutDetails?.startsAt?.toPreferrableFormatDate()
                                        .toString(),
                                    onClick = {

                                    },
                                    activityType = type, buttonEnabled = false, onCardClick = {

                                    }
                                )
                            }
                        }
                    }

                    1 -> {
                        LazyColumn(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(top = 12.dp, bottom = 16.dp)
                        ) {
                            items(bankSoalModel) { data ->
                                ActivityList(
                                    font = font,
                                    tryOutName = data.tryoutDetails?.tryOutTitle ?: "",
                                    startDate = data.tryoutDetails?.createdAt?.toPreferrableFormatDate()
                                        ?: "",
                                    onClick = {
                                        navigateToExamScreen.invoke(
                                            data.tryoutDetails?.tryOutSlug ?: return@ActivityList
                                        )
                                    },
                                    activityType = type, buttonEnabled = true, onCardClick = {

                                    }
                                )
                            }
                        }
                    }
                }
            }
        } else {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(top = 12.dp, bottom = 16.dp)
            ) {
                items(data.filterIsInstance<HistoryModel>()) {
                    ActivityList(
                        font = font,
                        tryOutName = it.tryoutDetails?.tryOutTitle ?: "",
                        startDate = it.tryoutDetails?.startsAt?.toPreferrableFormatDate() ?: "",
                        onClick = {
                            navigateToTryOutInformation.invoke(
                                it.tryoutDetails?.tryOutSlug ?: return@ActivityList
                            )
                        },
                        activityType = type, buttonEnabled = true, onCardClick = {

                        }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ActivityList(
    font: FontFamily,
    tryOutName: String,
    startDate: String = "",
    activityType: ActivityType,
    onClick: () -> Unit,
    buttonEnabled: Boolean = false,
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.85F)
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            MainYellowColor
        ),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = {
            onCardClick.invoke()
        }
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 12.dp)
                .padding(vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = tryOutName,
                fontFamily = font,
                fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1F)
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1F),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = startDate,
                    fontFamily = font,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        onClick.invoke()
                    },
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(32.dp),
                    colors = ButtonDefaults.buttonColors(
                        if (buttonEnabled) MainBlueColor else Color.LightGray
                    )
                ) {
                    Text(
                        text = when (activityType) {
                            ActivityType.MYACTIVITY -> stringResource(id = R.string.start)
                            ActivityType.DISCUSSION -> stringResource(id = R.string.discussion)
                        },
                        fontFamily = font,
                        fontSize = 11.sp,
                        color = if (buttonEnabled) MainYellowColor else Color.Gray
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
    onSaved: (UpdateProfileModel) -> Unit, font: FontFamily,
    onTapExit: () -> Unit, enableOnDismiss: Boolean, currentData: UserProfileModel?
) {
    val focusState = LocalFocusManager.current
    var temporaryUserProfile: Uri? by remember {
        mutableStateOf(null)
    }
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(), onResult = {
            temporaryUserProfile = it
        }
    )
    var animatedExit by remember {
        mutableStateOf(0.1F)
    }
    val animatedState =
        animateFloatAsState(
            targetValue = animatedExit, label = ""
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
        currentData?.let {
            userName = it.userName
        }
    })
    LaunchedEffect(key1 = Unit, block = {
        animatedExit = 1F
    })
    LaunchedEffect(key1 = animatedState.value, block = {
        if (animatedState.value < 0.1F) {
            onTapExit.invoke()
        }
    })
    if (shouldShowDatePicker) {
        DatePickerDialog(onDismissRequest = {
            shouldShowDatePicker = false
            focusState.clearFocus()
        }, onDateChange = {
            birthDate = it.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            shouldShowDatePicker = false
            focusState.clearFocus()
        })
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(interactionSource = MutableInteractionSource(),
                indication = null, onClick = {
                    if (enableOnDismiss) {
                        animatedExit = 0F
                    }
                }
            )
    ) {
        AnimatedVisibility(
            visible = animatedState.value == 1F,
            enter = fadeIn(tween(200)),
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
                                    model = temporaryUserProfile,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(CircleShape), contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Card(
                                    modifier = Modifier
                                        .size(140.dp, 50.dp),
                                    border = BorderStroke(1.dp, Color.Black), onClick = {
                                        photoPicker.launch(
                                            PickVisualMediaRequest(
                                                ActivityResultContracts.PickVisualMedia.ImageOnly
                                            )
                                        )
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
                                                    if (shouldExpandGenderList) 90F else 270F
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
                                modifier = Modifier
                                    .fillMaxWidth(0.9F)
                                    .onFocusChanged {
                                        if (it.hasFocus) {
                                            shouldShowDatePicker = !shouldShowDatePicker
                                        }
                                    }
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
                                        onSaved.invoke(
                                            UpdateProfileModel(
                                                "",
                                                userName,
                                                "",
                                                phoneNumber,
                                                address,
                                                "l",
                                                selectedGender
                                            )
                                        )
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
    enableOnDismiss: Boolean,
    onSelected: (String) -> Unit
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
                            .fillMaxWidth()
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
                        }
                        items(settingsList) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = it.iconMenu, contentDescription = null,
                                    tint = MainBlueColor
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = it.name,
                                    color = MainBlueColor,
                                    fontFamily = font,
                                    fontSize = 17.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            onSelected.invoke(
                                                it.name
                                            )
                                        }
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }

            }

        }

    }
}