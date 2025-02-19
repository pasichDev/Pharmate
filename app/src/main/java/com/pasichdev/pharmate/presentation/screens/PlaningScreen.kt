package com.pasichdev.pharmate.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.pasichdev.pharmate.R
import com.pasichdev.pharmate.presentation.Routes
import com.pasichdev.pharmate.presentation.components.EmptyPlaningScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaningScreen(navHost: NavHostController) {

    Scaffold(floatingActionButton = {
        ExtendedFloatingActionButton(
            onClick = { navHost.navigate(Routes.AddItemPlaning.toString()) },
            contentColor = Color.White,
            containerColor = MaterialTheme.colorScheme.primary,
            icon = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            },
            text = {
                Text(
                    text = stringResource(id = R.string.fab_add),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            },
            expanded = true


        )
    }, content = { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            EmptyPlaningScreen()
        }

    })


}
