package com.example.tmda.presentation.shared

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.MotionLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.tmda.R
import com.example.tmda.ui.theme.BlackTransparent37
import com.example.tmda.ui.theme.GoldenYellow
import com.example.tmda.ui.theme.PineGreen
import com.example.tmda.ui.theme.WhiteTransparent60

@SuppressLint("ModifierParameter")
@Composable
fun MotionLayoutAppBar(
    modifier: Modifier = Modifier.background(Color.Transparent),
    imageUrl: String,
    voteCount: Int,
    voteAvg: Double,
    progress: Float
) {
    MotionLayout(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        start = startConstraintSet(),
        end = endConstraintSet(),
        progress = progress
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            modifier = Modifier
                .layoutId(MotionLayoutAppBarItem.Image)
                .background(Color.Transparent)
                .clip(
                    RoundedCornerShape(
                        bottomEnd = 120.dp,
                        bottomStart = 120.dp
                    )
                ),
            contentScale = ContentScale.FillBounds,
            contentDescription = null
        )

        AppToolBar(modifier = Modifier.layoutId(MotionLayoutAppBarItem.MainAppBar)) {
            IconButton(
                modifier = Modifier
                    .background(color = BlackTransparent37, shape = RoundedCornerShape(8.dp))
                    .size(40.dp, 40.dp),
                onClick = { }, content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_more),
                        contentDescription = "",
                        tint = WhiteTransparent60
                    )
                })
        }
        ServicesBox(
            modifier = Modifier.layoutId(MotionLayoutAppBarItem.ServicesBox),
            voteAvg,
            voteCount)


    }
}

@Composable
fun ServicesBox(modifier: Modifier, voteAvg: Double, voteCount: Int) {
    val sharedModifier = Modifier
        .background(Color.Transparent)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = sharedModifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = "rateIcon",
                tint = GoldenYellow,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "$voteAvg/10")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "$voteCount")
        }
        Column(
            modifier = sharedModifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextButton(onClick = { /*TODO*/ }) {

                Image(
                    modifier = Modifier.size(60.dp),
                    painter = painterResource(id = R.drawable.play_button),
                    contentDescription = "play trailer"
                )

            }

        }
        Column(
            modifier = sharedModifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Icon(
                painterResource(id = R.drawable.ic_bookmark),
                contentDescription = null,
                tint = PineGreen,
                modifier = Modifier.size(30.dp)
            )
            Text(text = "Add")

        }

    }

}

private fun startConstraintSet() = ConstraintSet {
    val mainAppBar = createRefFor(MotionLayoutAppBarItem.MainAppBar)
    val image = createRefFor(MotionLayoutAppBarItem.Image)
    val servicesBox = createRefFor(MotionLayoutAppBarItem.ServicesBox)

    constrain(mainAppBar) {
        top.linkTo(parent.top, 40.dp)
        start.linkTo(parent.start, 16.dp)
        end.linkTo(parent.end, 16.dp)
    }

    constrain(image) {
        this.width = Dimension.fillToConstraints
        this.height = Dimension.value(360.dp)
        top.linkTo(parent.top, 0.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)

    }

    constrain(servicesBox) {

        width = Dimension.matchParent
        height = Dimension.wrapContent
        top.linkTo(parent.top, 320.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)

    }
}

private fun endConstraintSet() = ConstraintSet {
    val mainAppBar = createRefFor(MotionLayoutAppBarItem.MainAppBar)
    val image = createRefFor(MotionLayoutAppBarItem.Image)
    val servicesBox = createRefFor(MotionLayoutAppBarItem.ServicesBox)

    constrain(mainAppBar) {
        width = Dimension.fillToConstraints
        top.linkTo(parent.top, 24.dp)
        start.linkTo(parent.start, 24.dp)
        end.linkTo(parent.end, 24.dp)

    }
    constrain(image) {
        alpha = 0f
        height = Dimension.value(0.dp)
        width = Dimension.fillToConstraints
        top.linkTo(parent.top, 0.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)

    }
    constrain(servicesBox) {
        alpha = 0f
        height = Dimension.value(0.dp)


        top.linkTo(parent.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)

    }
}

private enum class MotionLayoutAppBarItem {
    MainAppBar,
    Image,
    ServicesBox;
}
