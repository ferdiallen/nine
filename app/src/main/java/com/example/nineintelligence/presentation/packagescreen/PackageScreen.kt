package com.example.nineintelligence.presentation.packagescreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.nineintelligence.core.CustomText
import com.example.nineintelligence.ui.theme.MainBlueColor
import com.example.nineintelligence.ui.theme.MainYellowColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState


private val paymentOptions = listOf(
    "QRIS", "OVO", "Cash", "Gopay", "Brimo"
)

data class PackageModel(
    val id: Int, val packageName: String, val packagePrice: String, val pakcageDescription: String
)

private val packageOptions = listOf(
    PackageModel(
        1,
        "Starter",
        "Free",
        "Semua gratis hanya " + "untuk 3 Bank Soal pertama, 1 Tryout Pertama, dan 3 Materi Pertama"
    ),
    PackageModel(
        2, "Basic", "Rp.500k", "Paket yang berdurasi 6" + " bulan dan mendapatkan semua akses."
    ),
    PackageModel(
        3, "Pro", "Rp.1000k", "Paket yang " + "berdurasi 12 bulan dan mendapatkan semua akses."
    ),
)

@Composable
fun PackageScreen(
    modifier: Modifier = Modifier,controller: NavController
) {
    var shouldShowPaymentDialog by remember {
        mutableStateOf(false)
    }
    Column(modifier) {
        TopBarMain(onBackPress = {
            controller.popBackStack()
        })
        CustomText(
            text = "Paket Berlangganan",
            fontWeight = FontWeight.Bold,
            color = MainBlueColor,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            items(packageOptions) {
                PackageItem(
                    name = it.packageName,
                    price = it.packagePrice,
                    desc = it.pakcageDescription,
                    onClickItem = {
                        shouldShowPaymentDialog = !shouldShowPaymentDialog
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
    if (shouldShowPaymentDialog) {
        Dialog(onDismissRequest = { shouldShowPaymentDialog = false }) {
            PaymentDialog(
                modifier = Modifier.fillMaxHeight(0.8F)
            )
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
private fun PackageItem(
    name: String, price: String, desc: String, onClickItem: () -> Unit
) {
    var shouldExpand by remember {
        mutableStateOf(false)
    }
    var rotationArrowChanges by remember {
        mutableStateOf(-90F)
    }
    val animateRotation by animateFloatAsState(targetValue = rotationArrowChanges, label = "")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(), onClick = {
            if (shouldExpand) {
                onClickItem.invoke()
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Card(
                colors = CardDefaults.cardColors(MainBlueColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp), shape = RoundedCornerShape(0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomText(text = name, color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.weight(1F))
                    CustomText(text = price, color = MainYellowColor, fontWeight = FontWeight.Bold)
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp),
                colors = CardDefaults.cardColors(Color.LightGray),
                shape = RoundedCornerShape(0.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    IconButton(onClick = {
                        rotationArrowChanges = if (shouldExpand) -90F else 90F
                        shouldExpand = !shouldExpand
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = null,
                            modifier = Modifier.rotate(animateRotation)
                        )
                    }
                }
            }
            AnimatedVisibility(visible = shouldExpand) {
                Spacer(modifier = Modifier.height(12.dp))
                CustomText(
                    text = desc,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp), fontSize = 15.sp
                )
                Spacer(modifier = Modifier.height(14.dp))
            }
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun PaymentDialog(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState()
    var selectedPayment by remember {
        mutableStateOf("")
    }
    var currentCardColor by remember {
        mutableStateOf(Color.White)
    }
    val currentPage by remember {
        derivedStateOf {
            pagerState.currentPage
        }
    }
    LaunchedEffect(key1 = currentPage, block = {
        when (pagerState.currentPage) {
            0 -> currentCardColor = Color.White
            1 -> currentCardColor = Color.White
            2 -> currentCardColor = Color.Green
            3 -> currentCardColor = Color.Red.copy(0.5F)
            4 -> currentCardColor = Color.White
        }
    })
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = currentCardColor
        )
    ) {
        HorizontalPager(count = 5, state = pagerState) {
            when (it) {
                0 -> {
                    SelectingPayment(selectedPayment = selectedPayment, onSelectedPayment = {
                        selectedPayment = it
                    })
                }

                1 -> {
                    LoadingPayment()
                }

                2 -> {
                    PaymentAccepted()
                }

                3 -> {
                    PaymentDeclined()
                }

                4 -> {
                    InvoiceGenerated(
                        selectedPayment
                    )
                }
            }
        }
    }
}

@Composable
private fun SelectingPayment(
    selectedPayment: String, onSelectedPayment: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(16.dp)
    ) {
        CustomText(
            text = "Detail Pembayaran", fontWeight = FontWeight.Bold, color = MainBlueColor
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            CustomText(text = "Harga", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1F))
            CustomText(text = "Rp.0", color = MainBlueColor)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            CustomText(text = "Harga Diskon", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1F))
            CustomText(text = "Rp.0", color = MainBlueColor)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            CustomText(text = "Pajak (PPN)", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1F))
            CustomText(text = "Rp.0", color = MainBlueColor)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            CustomText(text = "Total Bayar", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1F))
            CustomText(text = "Rp.0", color = MainBlueColor)
        }
        Spacer(modifier = Modifier.height(20.dp))
        CustomText(
            text = "Metode Pembayaran", fontWeight = FontWeight.Bold, color = MainBlueColor
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            items(paymentOptions) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = selectedPayment == it, onClick = {
                        onSelectedPayment.invoke(it)
                    })
                    CustomText(text = it)
                }
            }
        }
        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(MainBlueColor)
        ) {
            CustomText(text = "Bayar", color = MainYellowColor)
        }
    }
}

@Composable
private fun LoadingPayment() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomText(text = "Sedang menunggu proses pembayaran", textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(12.dp))
        CircularProgressIndicator(color = MainBlueColor)
    }
}

@Composable
private fun PaymentAccepted() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 12.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Button(
                onClick = { },
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.weight(1F),
                colors = ButtonDefaults.buttonColors(MainBlueColor)
            ) {
                CustomText(text = "Cetak Invoice", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Button(
                onClick = { },
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.weight(1F),
                colors = ButtonDefaults.buttonColors(MainBlueColor)
            ) {
                CustomText(
                    text = "Kembali", fontSize = 12.sp, fontWeight = FontWeight.Bold
                )
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomText(text = "Pembayaran Berhasil", color = Color.White)
        Icon(
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(45.dp)
        )
    }
}

@Composable
private fun PaymentDeclined() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 12.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth(0.9F),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(MainBlueColor)
        ) {
            CustomText(text = "kembali", fontWeight = FontWeight.Bold)
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomText(text = "Pembayaran Gagal", color = Color.White)
        Spacer(modifier = Modifier.height(24.dp))
        CustomText(
            text = "Silahkan cek kembali apakah semua data sudah benar",
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}

@Composable
private fun InvoiceGenerated(
    paymentType: String
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .padding(top = 12.dp)
            .padding(horizontal = 4.dp)
    ) {
        item {
            CustomText(
                text = "Detail Pembayaran", fontWeight = FontWeight.Bold, color = MainBlueColor
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                CustomText(text = "Harga", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1F))
                CustomText(text = "Rp.0", color = MainBlueColor)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                CustomText(text = "Harga Diskon", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1F))
                CustomText(text = "Rp.0", color = MainBlueColor)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                CustomText(text = "Pajak (PPN)", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1F))
                CustomText(text = "Rp.0", color = MainBlueColor)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                CustomText(text = "Total Bayar", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1F))
                CustomText(text = "Rp.0", color = MainBlueColor)
            }
            Spacer(modifier = Modifier.height(20.dp))
            CustomText(
                text = "Metode Pembayaran", fontWeight = FontWeight.Bold, color = MainBlueColor
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = true, onClick = { })
                CustomText(text = paymentType)
            }
            Spacer(modifier = Modifier.height(12.dp))
            CustomText(
                text = "Anda telah berlangganan paket ." +
                        " Selamat menikmati dan tetap semangat untuk belajar!",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(MainBlueColor)
            ) {
                CustomText(text = "Kembali", fontWeight = FontWeight.Bold, color = MainYellowColor)
            }
        }
    }
}
