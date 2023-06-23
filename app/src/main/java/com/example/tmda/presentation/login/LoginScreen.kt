package com.example.tmda.presentation.login

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tmda.R
import com.example.tmda.presentation.shared.uiStates.LoadingScreen
import com.example.tmda.presentation.shared.uiStates.UiState
import com.example.tmda.ui.theme.PineGreenDark
import com.example.tmda.ui.theme.WhiteTransparent15
import com.example.tmda.ui.theme.WhiteTransparent60
import kotlinx.coroutines.launch

@Composable
fun LoginScreen() {
    val viewModel = hiltViewModel<LoginViewModel>()
    val snackBarHostState = remember { SnackbarHostState() }
    val localCoroutineScope = rememberCoroutineScope()
    val context = LocalContext.current as ComponentActivity

    LaunchedEffect(viewModel.errorMsg.value) {
        if (viewModel.errorMsg.value.isNotEmpty())
            localCoroutineScope.launch {
                snackBarHostState.showSnackbar(
                    message = viewModel.errorMsg.value,
                    duration = SnackbarDuration.Short
                )
            }

    }
    var isUserNavigateToLogin by remember { mutableStateOf(false) }

    when (val isFirstLogin = viewModel.isFirstLogin.value) {


        is UiState.Failure -> {}
        is UiState.Loading -> LoadingScreen()
        is UiState.Success -> {
            when (isFirstLogin.data && !isUserNavigateToLogin) {
                true -> WelcomeScreen({ context.startBrowserIntent("https://www.themoviedb.org/signup") }) {
                    Log.d("xxx", viewModel.isFirstLogin.value.toString())
                    isUserNavigateToLogin = true
                }

                false -> LoginScreen(
                    viewModel = viewModel,
                    context = context,
                    snackBarHostState = snackBarHostState
                )
            }
        }


    }


}

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    context: ComponentActivity,
    snackBarHostState: SnackbarHostState
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(all = 90.dp),
            text = LOGIN_MESSAGE, style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            maxLines = 1,
            value = viewModel.userName,
            onValueChange = { viewModel.userName = it },
            label = { Text(text = "Username") },
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = WhiteTransparent15,
                unfocusedContainerColor = WhiteTransparent15,
                disabledLabelColor = WhiteTransparent15,
                focusedLabelColor = PineGreenDark,

                ),
        )
        Spacer(modifier = Modifier.height(16.dp))


        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            maxLines = 1,
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = { Text(text = "Password") },
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = WhiteTransparent15,
                unfocusedContainerColor = WhiteTransparent15
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        painterResource(id = if (isPasswordVisible) R.drawable.ic_visibility_off else R.drawable.ic_visibility),
                        contentDescription = if (isPasswordVisible) "Show Password" else "Hide Password",
                        tint = WhiteTransparent60
                    )
                }
            },
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp), horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = { context.startBrowserIntent("https://www.themoviedb.org/reset-password") }) {
                Text(
                    text = "Forget password?",
                    style = MaterialTheme.typography.bodySmall,
                    color = PineGreenDark
                )
            }
        }
        Spacer(modifier = Modifier.height(180.dp))
        Image(
            modifier = Modifier
                .size(180.dp, 48.dp)
                .clickable { viewModel.login() },
            painter = painterResource(id = R.drawable.img_btn_login),
            contentDescription = "Login Button"
        )
        Spacer(modifier = Modifier.height(136.dp))
        SnackbarHost(hostState = snackBarHostState, Modifier)
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp), horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don’t have an account?",
                style = MaterialTheme.typography.bodySmall
            )
            TextButton(onClick = { context.startBrowserIntent("https://www.themoviedb.org/signup") }) {
                Text(
                    text = "Register",
                    style = MaterialTheme.typography.bodySmall,
                    color = PineGreenDark
                )
            }
        }
    }
}

fun Context.startBrowserIntent(url: String, onError: () -> Unit = {}) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        onError()
    }
}

const val LOGIN_MESSAGE = "Welcome to our community"