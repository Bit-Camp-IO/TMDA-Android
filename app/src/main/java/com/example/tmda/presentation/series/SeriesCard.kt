package com.example.tmda.presentation.series

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.tmda.presentation.series.uiDto.TvShowBookMarkUiModel
import com.example.tmda.presentation.shared.base.list.BaseListItemWithBookmark
import com.example.tmda.presentation.shared.mainShape

val seriesCardShape = mainShape(cornerRadiusDegree = 100f, slopeLength = 30f)

@Composable
fun SeriesCard(
    tvShowBookMark: TvShowBookMarkUiModel,
    onCardClick: (Int) -> Unit,
    onBookmarkClick: suspend (Int,MutableState<Boolean>) -> Boolean
) {
    BaseListItemWithBookmark(
        id = tvShowBookMark.tvShowUiModel.id,
        title = tvShowBookMark.tvShowUiModel.title,
        backdropPath = tvShowBookMark.tvShowUiModel.backdropPath,
        date = tvShowBookMark.tvShowUiModel.releaseDate,
        genres = tvShowBookMark.tvShowUiModel.genres,
        language = tvShowBookMark.tvShowUiModel.originalLanguage,
        voteAverage = tvShowBookMark.tvShowUiModel.voteAverage,
        voteCount = tvShowBookMark.tvShowUiModel.voteCount,
        savedState =tvShowBookMark.bookMarkState,
        onCardClicked =onCardClick,
        onSaveItemClicked =onBookmarkClick
    )
}
//val show = tvShowBookMark.tvShowUiModel
//Surface(
//shape = seriesCardShape, color = BlackTransparent28,
//modifier = Modifier
//.fillMaxWidth()
//.height(160.dp)
//.padding(vertical = 8.dp).clickable {
//    onCardClick(show.id)
//}
//
//) {
//    Row(
//        verticalAlignment = Alignment.Top,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(all = 8.dp)
//    ) {
//        AsyncImage(
//            model = getTmdbImageLink(show.posterPath ?: show.backdropPath),
//            contentDescription = show.title + " image",
//            contentScale = ContentScale.FillBounds,
//            modifier = Modifier
//                .size(144.dp)
//                .clip(moviesCardShape)
//        )
//        Spacer(modifier = Modifier.width(8.dp))
//        Column(
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier
//                .weight(0.4f)
//                .fillMaxHeight(),
//        ) {
//            Text(
//                text = show.title,
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Medium,
//                color = Color.White
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = show.releaseDate + " . " + show.genres + show.originalLanguage.replaceFirstChar { it.uppercase() },
//                fontSize = 12.sp,
//                fontWeight = FontWeight.Light,
//                color = Color.White
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            Row(
//                horizontalArrangement = Arrangement.SpaceEvenly,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//
//                Row {
//                    Icon(
//                        modifier = Modifier.size(14.dp),
//                        painter = painterResource(id = R.drawable.ic_star),
//                        contentDescription = null,
//                        tint = Color.Unspecified
//                    )
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Text(
//                        text = show.voteAverage.toString(),
//                        fontSize = 10.sp,
//                        fontWeight = FontWeight.Light,
//                        color = Color.White
//                    )
//                }
//                Text(
//                    text = show.voteCount.toString(),
//                    fontSize = 10.sp,
//                    fontWeight = FontWeight.Light,
//                    color = Color.White
//                )
//
//            }
//        }
//        val coroutineScope = rememberCoroutineScope()
//        var isSavedState by remember { tvShowBookMark.bookMarkState }
//        SavedItemIcon(modifier = Modifier.clickable {
//            coroutineScope.launch {
//                if (onBookmarkClick(
//                        tvShowBookMark.tvShowUiModel.id,
//                        tvShowBookMark.bookMarkState.value
//                    )
//                ) {
//                    tvShowBookMark.bookMarkState.value = !tvShowBookMark.bookMarkState.value
//                    isSavedState = tvShowBookMark.bookMarkState.value
//                }
//            }
//        }, isSavedState = isSavedState.toSuccessState())
//
//    }
//
//}