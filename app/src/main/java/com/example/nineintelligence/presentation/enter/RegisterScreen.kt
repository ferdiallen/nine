package com.example.nineintelligence.presentation.enter

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.nineintelligence.R
import com.example.nineintelligence.core.CustomText
import com.example.nineintelligence.ui.theme.DeliverCustomFonts
import com.example.nineintelligence.ui.theme.MainYellowColor
import com.example.nineintelligence.ui.theme.PlaceholderColor
import com.example.nineintelligence.ui.theme.Poppins
import com.example.nineintelligence.ui.theme.RegistrationBlueColor
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = koinViewModel(),
    controller: NavController
) {
    val registerState by viewModel.registerState.collectAsStateWithLifecycle()
    var passwordVisibility by remember {
        mutableStateOf(true)
    }
    var confirmPasswordVisibility by remember {
        mutableStateOf(true)
    }
    val correctBothPassword = remember(viewModel.confirmPassword,viewModel.password) {
        viewModel.password == viewModel.confirmPassword
    }
    var hasFocusedConfirmPassword by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    LaunchedEffect(key1 = registerState, block = {
        if (registerState?.userName != null) {
            controller.popBackStack()
        }
    })
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
                        value = viewModel.userFirstName,
                        onValueChange = {
                            viewModel.setDataFor(
                                it,
                                context.getString(R.string.userfirstname),
                                context
                            )
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
                        value = viewModel.userLastName,
                        onValueChange = {
                            viewModel.setDataFor(
                                it,
                                context.getString(R.string.userlastname),
                                context
                            )
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
                        value = viewModel.userEmail,
                        onValueChange = {
                            viewModel.setDataFor(
                                it,
                                context.getString(R.string.useremail),
                                context
                            )
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
                        value = viewModel.userPhoneNumber,
                        onValueChange = {
                            viewModel.setDataFor(
                                it,
                                context.getString(R.string.userphonenumber),
                                context
                            )
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
                        value = viewModel.password,
                        onValueChange = {
                            viewModel.setDataFor(
                                it,
                                context.getString(R.string.password),
                                context
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        visualTransformation = if (passwordVisibility) PasswordVisualTransformation()
                        else VisualTransformation.None,
                        placeholder = {
                            Text(
                                text = "Password",
                                fontWeight = FontWeight.SemiBold,
                                color = PlaceholderColor.copy(0.5F)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                                Icon(
                                    imageVector = if (passwordVisibility) Icons.Filled.VisibilityOff
                                    else Icons.Filled.Visibility,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Confirm Password", fontSize = 16.sp,
                        fontFamily = font, fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = viewModel.confirmPassword,
                        onValueChange = {
                            viewModel.setDataFor(
                                it,
                                context.getString(R.string.confirmpassword),
                                context
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged {
                                if (hasFocusedConfirmPassword) {
                                    return@onFocusChanged
                                }
                                if (it.isFocused) {
                                    hasFocusedConfirmPassword = true
                                }
                            },
                        shape = RoundedCornerShape(14.dp),
                        placeholder = {
                            Text(
                                text = "Confirm Password",
                                fontWeight = FontWeight.SemiBold,
                                color = PlaceholderColor.copy(0.5F)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true, trailingIcon = {
                            IconButton(onClick = {
                                confirmPasswordVisibility = !confirmPasswordVisibility
                            }) {
                                Icon(
                                    imageVector = if (confirmPasswordVisibility)
                                        Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                    contentDescription = null
                                )
                            }
                        }, visualTransformation = if (confirmPasswordVisibility)
                            PasswordVisualTransformation() else VisualTransformation.None,
                        isError = !correctBothPassword, supportingText = {
                            if(!correctBothPassword){
                                CustomText(text = "Confirm Password incorrect")
                            }
                        }
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
                            viewModel.registerUser(
                                "${viewModel.userFirstName} ${viewModel.userLastName}",
                                viewModel.userEmail,
                                viewModel.password,
                                viewModel.userPhoneNumber
                            )
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        ClickableText(text = annotatedText, onClick = { offset ->
                            annotatedText.getStringAnnotations(
                                start = offset,
                                end = offset, tag = "Log in"
                            ).firstOrNull()?.let { out ->
                                controller.popBackStack()
                            }
                        })
                    }
                }
            }
        }

    }
}