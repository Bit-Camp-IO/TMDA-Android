package com.example.sharedui.base.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.sharedui.uiStates.ErrorComponent
import com.example.sharedui.uiStates.LoadingScreen


@Composable
fun <T : Any> BaseLazyColumn(
    listState: LazyListState ,
    lazyItems: LazyPagingItems<T>,
    keyGetter: (T) -> Int,
    contentType: (Int) -> Any?,
    content: @Composable (Int) -> Unit

) {
    LazyColumn(
        Modifier
            .background(Color.Transparent)
            .fillMaxSize(),
        state = listState
    ) {
        item { Spacer(modifier = Modifier.height(80.dp)) }
        items(
            count = lazyItems.itemSnapshotList.size,
            contentType = contentType,
            key = { it },
            ) { content(it) }
        when (lazyItems.loadState.append) {
            is LoadState.Error -> {
                item { ErrorComponent { lazyItems.retry() } }
            }

            is LoadState.Loading -> {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(128.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        LoadingScreen()
                    }
                }
            }

            is LoadState.NotLoading -> {}
        }
    }
}

