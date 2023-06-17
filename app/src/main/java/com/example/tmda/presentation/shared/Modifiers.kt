package com.example.tmda.presentation.shared

import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.tmda.presentation.shared.base.imageCardModifier

val HomeCardModifier = Modifier
    .size(200.dp, 270.dp)
    .clip(com.example.tmda.presentation.shared.base.mainShape)
val imageCardModifier = Modifier.imageCardModifier(200.dp, 270.dp)
