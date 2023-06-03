package com.example.tmda.presentation.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tmda.R
import com.example.tmda.ui.theme.BlackTransparent37
import com.example.tmda.ui.theme.PineGreen
import com.example.tmda.ui.theme.WhiteTransparent60

fun mainShape(cornerRadiusDegree: Float = 60f, slopeLength: Float = 50f) =
    GenericShape { size, _ ->
        fun getSlopeDistance(y: Float): Float {
            val transitionUnit = slopeLength / size.height
            return transitionUnit * y
        }
        moveTo(slopeLength + cornerRadiusDegree, 0f)
        lineTo(size.width - cornerRadiusDegree, 0f)

        quadraticBezierTo(
            size.width, 0f,
            size.width - getSlopeDistance(cornerRadiusDegree), cornerRadiusDegree
        )

        lineTo(
            size.width - getSlopeDistance(size.height - cornerRadiusDegree),
            size.height - cornerRadiusDegree
        )

        quadraticBezierTo(
            size.width - slopeLength, size.height,
            size.width - getSlopeDistance(size.height) - cornerRadiusDegree, size.height
        )

        lineTo(cornerRadiusDegree, size.height)

        quadraticBezierTo(
            0f, size.height,
            getSlopeDistance(cornerRadiusDegree), size.height - cornerRadiusDegree
        )
        lineTo(getSlopeDistance(size.height - cornerRadiusDegree), cornerRadiusDegree)
        quadraticBezierTo(
            slopeLength, 0f,
            cornerRadiusDegree + getSlopeDistance(size.height), 0f
        )
        close()


    }

@Composable
fun AppToolBar(
    modifier: Modifier = Modifier,
    title: String="",
    suffixIcon: @Composable () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                modifier = Modifier
                    .background(color = BlackTransparent37, shape = RoundedCornerShape(8.dp))
                    .size(40.dp, 40.dp),
                onClick = { }, content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = "Back Button",
                        tint = WhiteTransparent60
                    )
                })
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = title, style = MaterialTheme.typography.titleLarge)
        }

        suffixIcon()


    }
}

@Composable
fun BackGround() {
    Box(
        modifier = Modifier.background(Color.Red)

    ) {
        Image(
            painter = painterResource(id = R.drawable.back_ground),
            alignment = Alignment.TopStart,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxSize()

        )
    }
}


@Composable
fun BookMarkIconButton(onClick: () -> Unit) {
    IconButton(onClick = onClick, modifier = Modifier) {
        Icon(
            painter = painterResource(id = R.drawable.ic_bookmark),
            contentDescription = "",
            tint = PineGreen,
            modifier = Modifier.size(height = 24.dp, width = 24.dp)
        )
    }
}
