package com.example.sharedui.x

import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.sharedui.base.imageCardModifier

val HomeCardModifier = Modifier
    .size(200.dp, 270.dp)
    .clip(com.example.sharedui.base.mainShape)
val imageCardModifier = Modifier.imageCardModifier(200.dp, 270.dp)
