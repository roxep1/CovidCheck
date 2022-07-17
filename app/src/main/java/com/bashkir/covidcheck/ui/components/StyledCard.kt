package com.bashkir.covidcheck.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bashkir.covidcheck.ui.theme.CardShape
import com.bashkir.covidcheck.ui.theme.normalElevation
import com.bashkir.covidcheck.ui.theme.normalPadding
import com.bashkir.covidcheck.ui.theme.smallPadding

@Composable
fun StyledCard(onClick: (() -> Unit)? = null, content: @Composable ColumnScope.() -> Unit) = Card(
    Modifier
        .fillMaxWidth()
        .padding(smallPadding)
        .clickable(enabled = onClick != null, onClick = onClick ?: {}),
    CardShape,
    elevation = normalElevation
) {
    Column(Modifier.padding(horizontal = normalPadding, vertical = smallPadding), content = content)
}