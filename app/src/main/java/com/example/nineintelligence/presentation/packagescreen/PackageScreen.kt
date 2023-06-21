package com.example.nineintelligence.presentation.packagescreen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.nineintelligence.core.CustomText
import com.example.nineintelligence.core.PaymentDialog
import com.example.nineintelligence.core.toProperRupiah
import com.example.nineintelligence.ui.theme.MainBlueColor
import com.example.nineintelligence.ui.theme.MainYellowColor
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import org.koin.androidx.compose.koinViewModel

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun PackageScreen(
    modifier: Modifier = Modifier,
    controller: NavController,
    viewModel: PackageViewModel = koinViewModel()
) {
    var shouldShowPaymentDialog by remember {
        mutableStateOf(false)
    }
    var selectedPrice by remember {
        mutableStateOf("")
    }
    var selectedPriceInteger by remember {
        mutableStateOf(0)
    }
    var shouldShowPaymentGateway by remember {
        mutableStateOf(false)
    }
    val paymentItems by viewModel.paymentItemList.collectAsStateWithLifecycle()
    val paymentResult by viewModel.paymentResult.collectAsStateWithLifecycle()
    val webViewState =
        rememberWebViewState(url = paymentResult?.redirectUrl ?: "https://www.google.com")
    LaunchedEffect(key1 = paymentResult, block = {
        shouldShowPaymentGateway = paymentResult!=null
    })
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
            items(paymentItems) {
                PackageItem(
                    name = it.itemName ?: "",
                    price = it.price?.toProperRupiah() ?: "",
                    desc = "",
                    onClickItem = { out ->
                        shouldShowPaymentDialog = !shouldShowPaymentDialog
                        selectedPrice = out
                        selectedPriceInteger = it.price ?: 0
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
    if (shouldShowPaymentDialog) {
        Dialog(onDismissRequest = { shouldShowPaymentDialog = false }) {
            PaymentDialog(
                modifier = Modifier.height(510.dp), price = selectedPrice, onclickPayment = {
                    viewModel.createPayment(paymentItems.find { it.price == selectedPriceInteger }
                        ?: return@PaymentDialog)
                }
            )
        }
    }
    if (shouldShowPaymentGateway){
        Dialog(onDismissRequest = { shouldShowPaymentGateway = false }) {
            WebView(state = webViewState, onCreated = {
                it.settings.javaScriptEnabled = true
            }, onDispose = {
                it.clearHistory()
            }, modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(vertical = 12.dp)
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
    name: String, price: String, desc: String, onClickItem: (String) -> Unit
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
                onClickItem.invoke(price)
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


