package com.example.nineintelligence.presentation.enter

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.nineintelligence.R
import com.example.nineintelligence.core.CustomText
import com.example.nineintelligence.navigation.NavigationHolder
import com.example.nineintelligence.ui.theme.DeliverCustomFonts
import com.example.nineintelligence.ui.theme.MainBlueColor
import com.example.nineintelligence.ui.theme.MainYellowColor
import com.example.nineintelligence.ui.theme.PlaceholderColor
import com.example.nineintelligence.ui.theme.Poppins
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginForm(
    viewModel: EnterViewModel = koinViewModel(),
    controller: NavController
) {
    val email by viewModel.currentEmail.collectAsStateWithLifecycle()
    val password by viewModel.currentPassword.collectAsStateWithLifecycle()
    val checkedRemember by viewModel.isCheckedRememberMe.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var passwordVisibility by remember {
        mutableStateOf(true)
    }
    var hasFocusedEmail by remember {
        mutableStateOf(false)
    }
    val userData by viewModel.loginState
    LaunchedEffect(key1 = userData.tokenData, block = {
        if (userData.tokenData != "" && userData.tokenData != null) {
            controller.navigate(NavigationHolder.HomeScreen.route) {
                popUpTo(NavigationHolder.LoginScreen.route) {
                    inclusive = true
                }
            }
        }
    })
    val isErrorEmailAddress = remember(email) {
        !Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    val annotatedText = remember {
        buildAnnotatedString {
            append("Don't Have an account yet ? \n Make a new account")
            pushStringAnnotation(
                tag = context.getString(R.string.signup_tag),
                annotation = context.getString(R.string.signup_tag)
            )
            withStyle(SpanStyle(color = MainBlueColor)) {
                append(" here")
            }
            this.pop()
        }
    }
    DeliverCustomFonts(font = Poppins.fonts) { font ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MainBlueColor),
            verticalArrangement = Arrangement.Bottom
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.95F),
                shape = RoundedCornerShape(36.dp, 36.dp),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 24.dp)
                        .padding(horizontal = 28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Login",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = font
                    )
                    Spacer(modifier = Modifier.height(70.dp))
                    Text(
                        text = "Username",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        fontFamily = font
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = viewModel::onEmailChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged {
                                if (hasFocusedEmail) {
                                    return@onFocusChanged
                                }
                                hasFocusedEmail = it.isFocused
                            },
                        shape = RoundedCornerShape(14.dp),
                        placeholder = {
                            Text(
                                text = "Email or Phone Number",
                                fontWeight = FontWeight.SemiBold,
                                color = PlaceholderColor.copy(0.7F),
                                fontFamily = font,
                                fontSize = 14.sp
                            )
                        }, isError = if (hasFocusedEmail) isErrorEmailAddress else false,
                        singleLine = true,
                        supportingText = {
                             if(hasFocusedEmail && isErrorEmailAddress) {
                                 CustomText(text = "Invalid Email Address")
                             }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Password",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        fontFamily = font
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = viewModel::onPasswordChange,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        placeholder = {
                            Text(
                                text = "Password",
                                fontWeight = FontWeight.SemiBold,
                                color = PlaceholderColor.copy(0.7F),
                                fontFamily = font,
                                fontSize = 14.sp
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisibility = !passwordVisibility
                            }) {
                                Icon(
                                    imageVector = if (passwordVisibility) Icons.Filled.VisibilityOff
                                    else Icons.Filled.Visibility,
                                    contentDescription = null
                                )
                            }
                        },
                        visualTransformation = if (passwordVisibility) PasswordVisualTransformation()
                        else VisualTransformation.None,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = checkedRemember,
                            onCheckedChange = viewModel::onCheckedChange,
                            colors = CheckboxDefaults.colors(checkedColor = MainBlueColor)
                        )
                        Text(text = "Remember Me", modifier = Modifier.clickable {
                            viewModel.onCheckedChange(data = !checkedRemember)
                        }, fontFamily = font, fontSize = 14.sp, color = MainBlueColor)
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = {
                            viewModel.loginUser(email, password)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MainYellowColor)
                    ) {
                        Text(text = "Sign In", fontSize = 16.sp, fontFamily = font)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    ClickableText(text = annotatedText, onClick = { offset ->
                        annotatedText.getStringAnnotations(
                            tag = context.getString(R.string.signup_tag),
                            start = offset,
                            end = offset
                        ).firstOrNull()?.let { _ ->
                            controller.navigate(NavigationHolder.RegisterScreen.route)
                        }
                    }, style = TextStyle(fontFamily = font, fontSize = 12.sp))
                }
            }
        }

    }

}
