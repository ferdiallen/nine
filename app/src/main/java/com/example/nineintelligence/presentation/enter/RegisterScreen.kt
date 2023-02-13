package com.example.nineintelligence.presentation.enter

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.nineintelligence.navigation.NavigationHolder
import com.example.nineintelligence.ui.theme.DeliverCustomFonts
import com.example.nineintelligence.ui.theme.MainYellowColor
import com.example.nineintelligence.ui.theme.PlaceholderColor
import com.example.nineintelligence.ui.theme.Poppins
import com.example.nineintelligence.ui.theme.RegistrationBlueColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    controller: NavController
) {

    DeliverCustomFonts(font = Poppins.fonts) { font ->
        val annotatedText = remember {
            buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        fontFamily = font,
                        fontSize = 12.sp
                    )
                ) {
                    append("Already have an account ?")
                }
                pushStringAnnotation(tag = "Log in", annotation = "Log in")
                withStyle(
                    SpanStyle(
                        color = RegistrationBlueColor, fontFamily = font,
                        fontSize = 12.sp
                    )
                ) {
                    append(" Log in")
                }
                pop()
            }
        }
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
                                text = "Firstname",
                                fontWeight = FontWeight.SemiBold,
                                color = PlaceholderColor.copy(0.5F)
                            )
                        }, singleLine = true
                    )

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
                        }, singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Email", fontSize = 16.sp,
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
                                text = "Email",
                                fontWeight = FontWeight.SemiBold,
                                color = PlaceholderColor.copy(0.5F)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Phone Number", fontSize = 16.sp,
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
                                text = "Phone Number",
                                fontWeight = FontWeight.SemiBold,
                                color = PlaceholderColor.copy(0.5F)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Password", fontSize = 16.sp,
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
                                text = "Password",
                                fontWeight = FontWeight.SemiBold,
                                color = PlaceholderColor.copy(0.5F)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Confirm Password", fontSize = 16.sp,
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
                                text = "Confirm Password",
                                fontWeight = FontWeight.SemiBold,
                                color = PlaceholderColor.copy(0.5F)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(checked = true, onCheckedChange = {

                        })
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Yes, I want to receive Nine Intelligence emails",
                            fontFamily = font,
                            fontSize = 12.sp,
                            style = TextStyle(letterSpacing = 0.5.sp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(checked = true, onCheckedChange = {

                        })
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = buildAnnotatedString {
                                append("I agree to all the ")
                                withStyle(style = SpanStyle(color = RegistrationBlueColor)) {
                                    append("Term,Privacy,Policy")
                                }
                                append(" and ")
                                withStyle(style = SpanStyle(color = RegistrationBlueColor)) {
                                    append("Fees")
                                }
                            },
                            fontFamily = font,
                            fontSize = 12.sp,
                            style = TextStyle(letterSpacing = 0.5.sp)
                        )
                    }
                    Button(
                        onClick = {

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.15F),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MainYellowColor)
                    ) {
                        Text(text = "Create Account", fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        ClickableText(text = annotatedText, onClick = {offset->
                            annotatedText.getStringAnnotations(
                                start = offset,
                                end = offset, tag = "Log in"
                            ).firstOrNull()?.let { out->
                                controller.popBackStack()
                            }
                        })
                    }
                }
            }
        }

    }
}