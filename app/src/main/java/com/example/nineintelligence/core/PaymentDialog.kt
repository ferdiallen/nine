package com.example.nineintelligence.core

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nineintelligence.domain.util.PaymentState
import com.example.nineintelligence.ui.theme.MainBlueColor
import com.example.nineintelligence.ui.theme.MainYellowColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


private val paymentOptions = listOf(
    "BCA"
)

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PaymentDialog(
    modifier: Modifier = Modifier,
    price: String = "",
    onclickPayment: () -> Unit,
    currentPaymentState: State<PaymentState>, onClosePage: () -> Unit, onFailedPage: () -> Unit
) {
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
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = currentPaymentState.value, block = {
        when (currentPaymentState.value) {
            PaymentState.IDLE -> {}
            PaymentState.ONCHECK -> {
                pagerState.animateScrollToPage(1)
            }

            PaymentState.SUCCESS -> {
                pagerState.animateScrollToPage(2)
            }

            PaymentState.FAILED -> {
                onFailedPage.invoke()
                pagerState.animateScrollToPage(3)
            }

            PaymentState.INVOICE_GENERATED -> {
                pagerState.animateScrollToPage(4)
            }
        }
    })
    LaunchedEffect(key1 = currentPage, block = {
        when (pagerState.currentPage) {
            0 -> currentCardColor = Color.White
            1 -> currentCardColor = Color.White
            2 -> currentCardColor = Color.Green
            3 -> currentCardColor = Color.Red
            4 -> currentCardColor = Color.White
        }
    })
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = currentCardColor
        )
    ) {
        HorizontalPager(count = 5, state = pagerState, userScrollEnabled = false) {
            when (it) {
                0 -> {
                    SelectingPayment(selectedPayment = selectedPayment, onSelectedPayment = { out ->
                        selectedPayment = out
                    }, onPayButtonClicked = onclickPayment::invoke, price)
                }

                1 -> {
                    LoadingPayment()
                }

                2 -> {
                    PaymentAccepted(scrollToGenerateInvoice = {
                        scope.launch { pagerState.scrollToPage(4) }
                    })
                }

                3 -> {
                    PaymentDeclined(closePage = onClosePage::invoke)
                }

                4 -> {
                    InvoiceGenerated(
                        selectedPayment, price,onClosePage = onClosePage::invoke
                    )
                }
            }
        }
    }
}

@Composable
private fun SelectingPayment(
    selectedPayment: String,
    onSelectedPayment: (String) -> Unit,
    onPayButtonClicked: () -> Unit,
    price: String
) {
    val getPrice = remember {
        price.drop(2).replace(".", "").toInt()
    }
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
            CustomText(text = price, color = MainBlueColor)
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
            CustomText(text = (getPrice - 0 - 0).toProperRupiah(), color = MainBlueColor)
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
            onClick = {
                onPayButtonClicked.invoke()
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(MainBlueColor), enabled = selectedPayment != ""
        ) {
            CustomText(
                text = "Bayar",
                color = if (selectedPayment != "") MainYellowColor else Color.Gray
            )
        }
    }
}

@Composable
private fun LoadingPayment() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .border(1.dp, Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomText(text = "Sedang menunggu proses pembayaran", textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(12.dp))
        CircularProgressIndicator(color = MainBlueColor)
    }
}

@Composable
private fun PaymentAccepted(scrollToGenerateInvoice: () -> Unit) {
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
                onClick = scrollToGenerateInvoice::invoke,
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
            modifier = Modifier.size(80.dp)
        )
    }
}

@Composable
private fun PaymentDeclined(closePage: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White.copy(0.3F))
            .padding(bottom = 12.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = { closePage.invoke() },
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
        Spacer(modifier = Modifier.height(12.dp))
        Icon(
            imageVector = Icons.Rounded.Error,
            contentDescription = "",
            modifier = Modifier.size(80.dp), tint = Color.White
        )
        Spacer(modifier = Modifier.height(12.dp))
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
    paymentType: String, price: String,onClosePage: () -> Unit
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
                CustomText(text = price, color = MainBlueColor)
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
                text = "Anda telah berlangganan paket ." + " Selamat menikmati dan tetap semangat untuk belajar!",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onClosePage::invoke,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(MainBlueColor)
            ) {
                CustomText(text = "Kembali", fontWeight = FontWeight.Bold, color = MainYellowColor)
            }
        }
    }
}
