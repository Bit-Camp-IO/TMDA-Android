package com.example.profilefeature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sharedui.R


@Composable
fun WelcomeScreen(onRegisterClick: () -> Unit, onLoginClick: () -> Unit) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(250.dp),
            painter = painterResource(id = R.drawable.ic_login),
            contentDescription = "App Logo"
        )
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            modifier = Modifier.padding(horizontal = 46.dp),
            text = WELCOME_MESSAGE,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(60.dp))

        Image(
            modifier = Modifier
                .size(180.dp, 48.dp)
                .clickable {onLoginClick() },
            painter = painterResource(id = R.drawable.img_btn_login),
            contentDescription = "Login Button"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            modifier = Modifier
                .size(180.dp, 48.dp)
                .clickable {onRegisterClick() },
            painter = painterResource(id = R.drawable.img_btn_register),
            contentDescription = "Register Button"
        )
        Spacer(modifier = Modifier.height(80.dp))

    }
}

const val WELCOME_MESSAGE = "Your guide to Movies, TV shows and Celebrities"