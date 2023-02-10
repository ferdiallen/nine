package com.example.nineintelligence.presentation.boarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nineintelligence.R
import com.example.nineintelligence.ui.theme.MainBlueColor
import com.example.nineintelligence.ui.theme.MainYellowColor

val userType = mapOf(
    Pair("Siswa", R.drawable.customer),
    Pair("Mentor", R.drawable.customer)
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MainBoardingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainBlueColor), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Selamat Datang di Aplikasi \n Nine Intelligence",
            color = MainYellowColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "Pilih Role mu", color = MainYellowColor, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(containerColor = MainYellowColor)
        ) {
            LazyRow(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                items(userType.toList()) {
                    Surface(color = Color.Transparent,
                        shape = RoundedCornerShape(12.dp),
                        onClick = {

                        }) {
                        CustomerType(it.first, it.second)
                    }
                    if (it.first != userType.keys.last()) Spacer(modifier = Modifier.width(32.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
private fun CustomerType(type: String, image: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = null, modifier = Modifier.size(70.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = type, fontSize = 16.sp)
    }
}
