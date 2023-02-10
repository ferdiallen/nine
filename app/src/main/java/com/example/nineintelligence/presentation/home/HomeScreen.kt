package com.example.nineintelligence.presentation.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.nineintelligence.R
import com.example.nineintelligence.ui.theme.DeliverCustomFonts
import com.example.nineintelligence.ui.theme.MainBlueColor
import com.example.nineintelligence.ui.theme.MainYellowColor
import com.example.nineintelligence.ui.theme.Poppins
import com.example.nineintelligence.util.WindowType
import com.example.nineintelligence.util.rememberWindoInfo
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(controller: NavController = rememberAnimatedNavController()) {
    val windowInfo = rememberWindoInfo()
    Box(modifier = Modifier.fillMaxSize()) {
        BottomBarCustom(modifier = Modifier.border(0.1.dp, Color.Black), info = windowInfo)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        HeaderRow(
            username = "Ferdialif",
            image = R.drawable.generated,
            modifier = Modifier.padding(horizontal = 8.dp),
            info = windowInfo
        )
        MiddleItems(modifier = Modifier.padding(top = 32.dp))
    }
}

@Composable
private fun HeaderRow(
    modifier: Modifier = Modifier,
    username: String,
    image: Any?,
    info: WindowType
) {
    DeliverCustomFonts(font = Poppins.fonts) { font ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1F),
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(containerColor = MainBlueColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .then(modifier),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Hello $username",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    fontFamily = font,
                    color = MainYellowColor
                )
            }
        }

    }

}

@Composable
private fun MiddleItems(modifier: Modifier = Modifier) {
    val config = LocalConfiguration.current
    DeliverCustomFonts(font = Poppins.fonts) { font ->
        Column(
            Modifier
                .fillMaxSize()
                .then(modifier)
        ) {
            Text(
                text = "Yuk kita lihat jadwal tryout terdekatmu",
                fontFamily = font,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MainBlueColor, modifier = Modifier.padding(start = 14.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Size : ${config.screenWidthDp}", fontSize = 40.sp)
        }

    }
}

private val dataBottomBar = mapOf(
    Pair("Bank Soal", R.drawable.hand_with_pen),
    Pair("Materi", R.drawable.open_book),
    Pair("TryOut", R.drawable.grade),
    Pair("Paket", R.drawable.online_payment),
    Pair("Profile", R.drawable.customer),
).toList()

@ExperimentalMaterial3Api
@Composable
private fun BottomBarCustom(modifier: Modifier = Modifier, info: WindowType) {
    DeliverCustomFonts(font = Poppins.fonts) { font ->
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Surface(modifier = Modifier.wrapContentSize(), shadowElevation = 32.dp) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.09F)
                        .then(modifier),
                    shape = RoundedCornerShape(0.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        dataBottomBar.forEachIndexed { index, indexAt ->
                            if (index == 2) {
                                Spacer(modifier = Modifier.width(100.dp))
                                return@forEachIndexed
                            }
                            Card(
                                modifier = Modifier
                                    .size(70.dp)
                                    .padding(start = 4.dp),
                                onClick = {},
                                shape = CircleShape,
                                colors = CardDefaults.cardColors(Color.Transparent)
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 4.dp),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    AsyncImage(
                                        model = indexAt.second,
                                        contentDescription = null,
                                        modifier = Modifier.size(25.dp)
                                    )
                                    Text(text = indexAt.first, fontSize = 11.sp, fontFamily = font)
                                }

                            }


                            Spacer(modifier = Modifier.width(2.dp))
                        }
                    }

                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = when  {
                        info.screenWidthDp < 360.dp -> 12.dp
                        info.screenWidthDp < 540.dp -> 8.dp
                        info.screenWidthDp == 540.dp -> 32.dp
                        else -> 12.dp
                    }),
                contentAlignment = Alignment.BottomCenter
            ) {
                Card(
                    shape = CircleShape,
                    modifier = Modifier.size(
                        when  {
                            info.screenWidthDp < 360.dp -> 60.dp
                            info.screenWidthDp < 540.dp -> 80.dp
                            info.screenWidthDp == 540.dp -> 100.dp
                            else -> 120.dp
                        }
                    ),
                    onClick = {

                    },
                    colors = CardDefaults.cardColors(Color.White),
                    border = BorderStroke(0.1.dp, color = Color.Black),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 4.dp),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        AsyncImage(
                            model = dataBottomBar[2].second,
                            contentDescription = null,
                            modifier = Modifier.size(50.dp)
                        )
                        /*  Text(text = dataBottomBar[2].first, fontSize = 11.sp, fontFamily = font)*/
                        Text(
                            text = when (info.screenWidthInfo) {
                                is WindowType.WindowInfo.Small -> "Small"
                                is WindowType.WindowInfo.Medium -> "Medium"
                                else -> "Large"
                            }
                        )
                    }
                }
            }
        }

    }
}

@Preview(
    showBackground = true, showSystemUi = true,
    device = "spec:width=1080px,height=2280px,dpi=480"
)
@Composable
fun SmallPreview() {
    HomeScreen()
}

@Preview(
    showBackground = true, showSystemUi = true,
    device = "spec:width=320dp,height=533.3dp,dpi=640"
)
@Composable
fun MediumPreview() {
    HomeScreen()
}

@Preview(
    showBackground = true, showSystemUi = true,
    device = "id:pixel_c"
)
@Composable
fun LargePreview() {
    HomeScreen()
}

@Preview(
    showBackground = true, showSystemUi = true,
    device = "spec:width=2400px,height=1080px,dpi=320,orientation=portrait"
)
@Composable
fun Note9Pro() {
    HomeScreen()
}