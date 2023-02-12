package com.example.nineintelligence.presentation.enter

import android.widget.Space
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nineintelligence.ui.theme.DeliverCustomFonts
import com.example.nineintelligence.ui.theme.PlaceholderColor
import com.example.nineintelligence.ui.theme.Poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    DeliverCustomFonts(font = Poppins.fonts) { font ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .then(modifier),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Registration",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    fontFamily = font
                )
                Spacer(modifier = Modifier.height(36.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Firstname", fontSize = 16.sp,
                        fontFamily = font, fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = {

                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        placeholder = {
                            Text(
                                text = "Email or Phone Number",
                                fontWeight = FontWeight.SemiBold,
                                color = PlaceholderColor.copy(0.5F)
                            )
                        })
                    
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Last Name", fontSize = 16.sp,
                        fontFamily = font, fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = {

                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        placeholder = {
                            Text(
                                text = "Last Name",
                                fontWeight = FontWeight.SemiBold,
                                color = PlaceholderColor.copy(0.5F)
                            )
                        })

                    Text(
                        text = "Firstname", fontSize = 16.sp,
                        fontFamily = font, fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = {

                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        placeholder = {
                            Text(
                                text = "Email or Phone Number",
                                fontWeight = FontWeight.SemiBold,
                                color = PlaceholderColor.copy(0.5F)
                            )
                        })

                    Text(
                        text = "Firstname", fontSize = 16.sp,
                        fontFamily = font, fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = {

                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        placeholder = {
                            Text(
                                text = "Email or Phone Number",
                                fontWeight = FontWeight.SemiBold,
                                color = PlaceholderColor.copy(0.5F)
                            )
                        })

                    Text(
                        text = "Firstname", fontSize = 16.sp,
                        fontFamily = font, fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = {

                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        placeholder = {
                            Text(
                                text = "Email or Phone Number",
                                fontWeight = FontWeight.SemiBold,
                                color = PlaceholderColor.copy(0.5F)
                            )
                        })
                }
            }
        }

    }
}