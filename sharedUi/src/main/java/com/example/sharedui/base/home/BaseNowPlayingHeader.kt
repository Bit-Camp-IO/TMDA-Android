package com.example.sharedui.base.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sharedui.R


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T : Any> BaseNowPlayingHeader(items: List<T>, content: @Composable (Int) -> Unit) {

    val pagerState =
        rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f
        ) { items.size }
    Column {
        HorizontalPager(state = pagerState) {
            content(it)
        }
        Spacer(Modifier.height(8.dp))
        DotsIndicator(
            totalDots = pagerState.pageCount,
            currentIndex = pagerState.currentPage
        )

    }
}

@Composable
fun DotsIndicator(totalDots: Int, currentIndex: Int) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        for (i in 0 until totalDots) {
            if (i == currentIndex)
                Icon(
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                    painter = painterResource(id = R.drawable.ic_round_rectangle),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            else
                Icon(
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                    painter = painterResource(id = R.drawable.ic_dot),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
        }
    }
}