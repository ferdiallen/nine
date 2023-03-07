package com.example.nineintelligence.presentation.discuss

import android.content.Context
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.RelativeLayout
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.nineintelligence.R
import com.example.nineintelligence.ui.theme.MainBlueColor
import com.example.nineintelligence.ui.theme.MainYellowColor
import com.example.nineintelligence.ui.theme.Poppins
import com.example.nineintelligence.util.CustomText
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel

val videoList = listOf(
    "https://cdn.videvo.net/videvo_files/video/premium/video0032/large_watermarked/523_523-0006_preview.mp4",
    "https://cdn.videvo.net/videvo_files/video/premium/video0032/large_watermarked/523_523-0006_preview.mp4",
    "https://cdn.videvo.net/videvo_files/video/premium/video0032/large_watermarked/523_523-0006_preview.mp4",
    "https://cdn.videvo.net/videvo_files/video/premium/video0032/large_watermarked/523_523-0006_preview.mp4",
)

@OptIn(ExperimentalPagerApi::class)
@androidx.media3.common.util.UnstableApi
@Composable
fun DiscussionScreen(
    modifier: Modifier = Modifier,
    vm: DiscussionViewModel = koinViewModel(),
    subjectName: String = "",
    bankSoalOf: Int? = null,
    typeOf: String,
    controller : NavController
) {
    var shouldShowPlaylistSelector by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    MainScreen(
        pagerState, shouldShowPlaylistSelector = {
            shouldShowPlaylistSelector = !shouldShowPlaylistSelector
        }, bankSoalOf = bankSoalOf ?: 0, subjectName = subjectName,
        modifier = modifier
    )

    if (shouldShowPlaylistSelector) {
        MenuListDialog(onDismiss = {
            shouldShowPlaylistSelector = false
        }, videoList.size, onIndexSelected = {
            scope.launch {
                pagerState.scrollToPage(it)
            }
        }, currentPlay = pagerState.currentPage)
    }

}

@OptIn(ExperimentalPagerApi::class)
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
private fun MainScreen(
    state: PagerState,
    shouldShowPlaylistSelector: () -> Unit,
    modifier: Modifier = Modifier,
    bankSoalOf: Int?,
    subjectName: String
) {
    val context = LocalContext.current

    Column(modifier = modifier) {
        TopBarMain(font = Poppins.fonts, onBackPress = {

        }, onMenuClick = {
            shouldShowPlaylistSelector.invoke()
        })
        Spacer(modifier = Modifier.height(12.dp))
        CustomText(
            text = "Pembahasan Bank Soal $bankSoalOf : $subjectName",
            fontWeight = FontWeight.Bold,
            color = MainBlueColor
        )
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalPager(
            count = videoList.size, state = state,
            userScrollEnabled = false
        ) {
            val playerExo = lifecycleAwareExoPlayer(player = get())
            Column(
                modifier = Modifier.padding(
                    end = if (state.currentPage < 4) 8.dp
                    else 0.dp
                )
            ) {
                CustomText(
                    text = "Halaman ini menjelaskan tentang soal per " +
                            "soal dari bank soal 1 biologi yang " +
                            "dijelaskan oleh mentor yang berpengalaman.",

                    color = MainBlueColor
                )
                SideEffect {
                    playSelectedVideo(videoList[it], context, player = playerExo)
                }
                DisposableEffect(key1 = true, effect = {
                    onDispose {
                        playerExo.release()
                    }
                })
                Spacer(modifier = Modifier.height(8.dp))
                AndroidView(
                    factory = { contextOut ->
                        PlayerView(contextOut).apply {
                            player = playerExo
                            this.player?.apply {
                                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                                layoutParams =
                                    RelativeLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                                playWhenReady = true
                            }
                        }
                    }, modifier = Modifier.aspectRatio(16F / 9F)
                )

            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        CustomText(text = "Identitas Mentor", fontWeight = FontWeight.Bold, color = MainBlueColor)
        Spacer(modifier = Modifier.height(12.dp))
        MentorIdentitySection(
            image = R.drawable.male_user,
            "Syahrul Terhebat",
            Modifier.fillMaxWidth(),
            "Mentor di Nine Intelligence"
        )
    }
}

@Composable
private fun TopBarMain(font: FontFamily, onBackPress: () -> Unit, onMenuClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            onBackPress.invoke()
        }) {
            Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = null)
        }
        Text(text = "Back", fontFamily = font, fontSize = 16.sp, color = MainBlueColor)
        Spacer(modifier = Modifier.weight(1F))
        IconButton(onClick = {
            onMenuClick.invoke()
        }) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
private fun MentorIdentitySection(
    image: Any?,
    name: String = "",
    modifier: Modifier = Modifier,
    status: String
) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            model = image,
            contentDescription = null,
            modifier = Modifier.size(50.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(verticalArrangement = Arrangement.Center) {
            CustomText(text = name, color = MainBlueColor, fontSize = 17.sp)
            CustomText(text = status, color = MainBlueColor, fontSize = 13.sp)
        }
    }
}

@Composable
private fun MenuListDialog(
    onDismiss: () -> Unit,
    playlistNumber: Int,
    onIndexSelected: (Int) -> Unit, currentPlay: Int? = null
) {
    var valueFloat by remember {
        mutableStateOf(0.01F)
    }
    val animatedFloat =
        animateFloatAsState(targetValue = valueFloat, label = "", animationSpec = tween(500))
    LaunchedEffect(key1 = Unit, block = {
        valueFloat = 0.7F
    })
    LaunchedEffect(key1 = animatedFloat.value, block = {
        if (animatedFloat.value == 0F) {
            onDismiss.invoke()
        }
    })
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(animatedFloat.value))
            .clickable(MutableInteractionSource(), null, true, onClick = {
                valueFloat = 0F
            })
    ) {
        AnimatedVisibility(
            visible = animatedFloat.value == 0.7F,
            enter = slideInVertically(tween(250)),
            exit = slideOutVertically(tween(250), targetOffsetY = {
                (it * -0.9F).toInt()
            })
        ) {
            MenuListSelector(
                numOfPlaylist = playlistNumber,
                onGoToSelectedIndex = onIndexSelected::invoke,
                currentPlaylist = currentPlay ?: 0
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MenuListSelector(
    numOfPlaylist: Int,
    onGoToSelectedIndex: (Int) -> Unit,
    currentPlaylist: Int
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
                items(numOfPlaylist) {
                    Card(
                        border = BorderStroke(1.dp, Color.Black),
                        modifier = Modifier
                            .height(50.dp)
                            .padding(end = 12.dp),
                        onClick = {
                            onGoToSelectedIndex.invoke(it)
                        },
                        colors = CardDefaults.cardColors(
                            if (currentPlaylist == it)
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
        }
    }
}

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
private fun playSelectedVideo(item: String?, context: Context, player: ExoPlayer) {
    val defaultSourceFactory = DefaultDataSource.Factory(context)
    val dataSourceFactory = DefaultDataSource.Factory(
        context, defaultSourceFactory
    )
    val source = ProgressiveMediaSource.Factory(dataSourceFactory)
        .createMediaSource(MediaItem.fromUri(item ?: ""))
    player.apply {
        setMediaSource(source)
        prepare()
    }
}

@Composable
private fun lifecycleAwareExoPlayer(player: ExoPlayer): ExoPlayer {
    val lifecycle = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycle, effect = {
        val observer = LifecycleEventObserver { _, e ->
            when (e) {
                Lifecycle.Event.ON_PAUSE -> {
                    player.pause()
                }

                Lifecycle.Event.ON_RESUME -> {
                    player.play()
                }

                else -> {}
            }
        }
        lifecycle.lifecycle.addObserver(observer)
        onDispose {
            lifecycle.lifecycle.removeObserver(observer)
        }
    })
    return player
}