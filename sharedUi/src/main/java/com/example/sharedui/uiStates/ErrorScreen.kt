package com.example.sharedui.uiStates

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sharedui.R
import com.example.sharedui.colors.PineGreenLight
import com.example.sharedui.colors.PineGreenMedium

@Composable
fun ErrorScreen(onTryAgain: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_wifi),
            contentDescription = null,
            Modifier.size(100.dp),
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "No connection found",
            style = MaterialTheme.typography.titleLarge,
            color = PineGreenLight
        )
        Text(
            text = "Please check your connection",
            fontSize = 18.sp,
            color = PineGreenMedium
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = onTryAgain,
            modifier = Modifier.size(160.dp, 42.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PineGreenMedium)
        ) {
            Text(text = "Try Again")
        }

    }

}
