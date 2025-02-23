package com.pasichdev.pharmate.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pasichdev.pharmate.R


@Composable
fun EmptyPlanningScreen() {
    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Image(modifier = Modifier.size(180.dp),
            painter = painterResource(R.drawable.ic_planning_empty), contentDescription = null)
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = stringResource(R.string.emptyTitle),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = stringResource(R.string.emptyDescr), style = MaterialTheme.typography.bodyLarge
        )
    }
}
