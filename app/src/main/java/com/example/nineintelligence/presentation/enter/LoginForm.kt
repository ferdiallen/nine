package com.example.nineintelligence.presentation.enter

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nineintelligence.R
import com.example.nineintelligence.ui.theme.MainBlueColor
import com.example.nineintelligence.ui.theme.MainYellowColor
import com.example.nineintelligence.ui.theme.PlaceholderColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginForm(
    type: String,
    viewModel: EnterViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val email by viewModel.currentEmail.collectAsStateWithLifecycle()
    val password by viewModel.currentPassword.collectAsStateWithLifecycle()
    val checkedRemember by viewModel.isCheckedRememberMe.collectAsStateWithLifecycle()
    val context = LocalContext.current
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
                Text(text = "Login $type", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(70.dp))
                Text(
                    text = "Username",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = viewModel::onEmailChange,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    placeholder = {
                        Text(
                            text = "Email or Phone Number",
                            fontWeight = FontWeight.SemiBold,
                            color = PlaceholderColor.copy(0.7F)
                        )
                    })

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Password",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start
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
                            color = PlaceholderColor.copy(0.7F)
                        )
                    })

                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = checkedRemember,
                        onCheckedChange = viewModel::onCheckedChange
                    )
                    Text(text = "Remember Me", modifier = Modifier.clickable {
                        viewModel.onCheckedChange(data = !checkedRemember)
                    })
                }
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.15F),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MainYellowColor)
                ) {
                    Text(text = "Sign In", fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.height(8.dp))
                when (type) {
                    stringResource(id = R.string.siswa) -> {
                        ClickableText(text = annotatedText, onClick = { offset ->
                            annotatedText.getStringAnnotations(
                                tag = context.getString(R.string.signup_tag),
                                start = offset,
                                end = offset
                            ).firstOrNull()?.let { clickedValue ->
                                println(clickedValue.item)
                            }
                        })
                    }

                    stringResource(id = R.string.mentor) -> {

                    }
                }
            }
        }
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginPreview() {
    LoginForm(type = "Siswa")
}
