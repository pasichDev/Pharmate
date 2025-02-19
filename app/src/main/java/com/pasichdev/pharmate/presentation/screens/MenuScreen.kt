package com.pasichdev.pharmate.presentation.screens

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pasichdev.pharmate.R
import com.pasichdev.pharmate.domain.model.getThemeTypeLabel
import com.pasichdev.pharmate.presentation.components.RoundPosition
import com.pasichdev.pharmate.presentation.components.SettingBox
import com.pasichdev.pharmate.presentation.dialog.ThemeColorDialog
import com.pasichdev.pharmate.presentation.dialog.ThemeColorDialogAction
import com.pasichdev.pharmate.presentation.viewmodel.MenuViewModel
import com.pasichdev.pharmate.presentation.viewmodel.SettingsEvent


@Composable
fun MenuScreen(menuViewModel: MenuViewModel = hiltViewModel()) {

    val themeSettings = menuViewModel.themeSettingsFlow.collectAsState()
    val (isDynamicTheme, themeType) = themeSettings.value
    val dialogVisible = remember { mutableStateOf(false) }


    if (dialogVisible.value) ThemeColorDialog({ action, type ->
        when (action) {
            ThemeColorDialogAction.CLOSE -> dialogVisible.value = false
            ThemeColorDialogAction.UPDATE -> menuViewModel.onEvent(
                SettingsEvent.UpdateThemeType(
                    type
                )
            )
        }
    }, themeType)

    Column {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            item {
                Card(
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                stringResource(R.string.app_name),
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                            )
                            Spacer(modifier = Modifier.height(5.dp))

                            Text(
                                stringResource(R.string.app_descr),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(
                            painter = painterResource(R.drawable.ic_pharmate_logo),
                            modifier = Modifier.size(44.dp),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }

            }


            item {
                SettingBox(
                    active = true,
                    roundPosition = RoundPosition.Last,
                    title = stringResource(id = R.string.donate),
                    subTitle = stringResource(id = R.string.donate_desc),
                    icon = painterResource(id = R.drawable.ic_cofee),
                )
            }

            item {
                SettingBox(title = stringResource(id = R.string.theme),
                    subTitle = getThemeTypeLabel(themeType),
                    roundPosition = RoundPosition.First,
                    action = {
                        dialogVisible.value = true
                    },
                    endWidget = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                            contentDescription = "Change App Theme"
                        )
                    })
            }



            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) item {
                SettingBox(title = stringResource(id = R.string.dynamic_color),
                    subTitle = stringResource(id = R.string.dynamic_color_subtitle),
                    roundPosition = RoundPosition.Last,
                    endWidget = {
                        Switch(checked = (isDynamicTheme), onCheckedChange = {
                            menuViewModel.onEvent(
                                SettingsEvent.UpdateIsDynamicTheme(
                                    !isDynamicTheme
                                )

                            )


                        })
                    })
            }

            item {
                SettingBox(
                    title = stringResource(id = R.string.about),
                    subTitle = stringResource(id = R.string.about_desc),
                    icon = painterResource(id = R.drawable.ic_about)
                )

            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.6f))
        )
    }

}
