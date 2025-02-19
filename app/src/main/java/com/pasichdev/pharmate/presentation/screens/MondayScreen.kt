package com.pasichdev.pharmate.presentation.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pasichdev.pharmate.domain.model.DataCapsule
import java.time.LocalTime

@Composable
fun MondayScreen( navController: NavHostController,) {
    var dataScreens = mutableListOf<String>().apply {
        add("27.01.2025")
        add("28.01.2025")
        add("29.01.2025")
        add("30.01.2025")
        add("31.01.2025")
        add("01.02.2025")
        add("02.02.2025")
        add("03.02.2025")
        add("04.02.2025")
    }

    val liks = mutableListOf<DataCapsule>().apply {
        add(DataCapsule(name = "Парацетамол", data = "08:00")) // Парацетамол
        add(DataCapsule(name = "Активоване Вугілля", data = "08:20")) // Активоване вугілля
        add(DataCapsule(name = "Метформін", data = "12:00")) // Метформін (для діабету)
        add(DataCapsule(name = "Панкреатин", data = "13:00")) // Панкреатин
        add(DataCapsule(name = "Мефенамінова кислота", data = "19:00")) // Мефенамінова кислота
        add(DataCapsule(name = "Ібупрофен", data = "19:00")) // Ібупрофен
        add(DataCapsule(name = "Амоксицилін", data = "09:00")) // Амоксицилін (антибіотик)
        add(DataCapsule(name = "Азитроміцин", data = "11:00")) // Азитроміцин (антибіотик)
        add(DataCapsule(name = "Диклофенак", data = "15:00")) // Диклофенак (анальгезуючий)
        add(DataCapsule(name = "Супрастин", data = "16:00")) // Супрастин (антигістамінний)
        add(DataCapsule(name = "Глімепірид", data = "08:30")) // Глімепірид (проти діабету)
        add(DataCapsule(name = "Левоцетиризин", data = "17:00")) // Левоцетиризин (антигістамінний)
        add(DataCapsule(name = "Фуросемід", data = "10:00")) // Фуросемід (діуретик)
        add(
            DataCapsule(
                name = "Ранитидин", data = "14:00"
            )
        ) // Ранитидин (антигістамінний, при гастритах)
        add(DataCapsule(name = "Аторвастатин", data = "07:00")) // Аторвастатин (знижує холестерин)
        add(DataCapsule(name = "Лоперамід", data = "20:00")) // Лоперамід (від діареї)
        add(
            DataCapsule(
                name = "Бісопролол", data = "09:30"
            )
        ) // Бісопролол (для зниження артеріального тиску)
        add(
            DataCapsule(
                name = "Калію хлорид", data = "18:00"
            )
        ) // Калію хлорид (для відновлення рівня калію)
        add(DataCapsule(name = "Інгавірін", data = "13:30")) // Інгавірін (проти грипу)
        add(DataCapsule(name = "Гепарин", data = "11:30")) // Гепарин (антикоагулянт)
        add(DataCapsule(name = "Левофлоксацин", data = "16:30")) // Левофлоксацин (антибіотик)
        add(DataCapsule(name = "Фенібут", data = "21:00")) // Фенібут (анxiолітик)
        add(
            DataCapsule(
                name = "Магнію сульфат", data = "09:15"
            )
        ) // Магнію сульфат (лікування гіпертонії)
        add(DataCapsule(name = "Клоназепам", data = "22:00")) // Клоназепам (анxiолітик)
        add(DataCapsule(name = "Монтелукаст", data = "12:30")) // Монтелукаст (для лікування астми)
        add(DataCapsule(name = "Рибавірин", data = "14:30")) // Рибавірин (антивірусний)
        add(DataCapsule(name = "Сальбутамол", data = "07:30")) // Сальбутамол (для лікування астми)
        add(DataCapsule(name = "Цефтріаксон", data = "16:15")) // Цефтріаксон (антибіотик)
        add(DataCapsule(name = "Тималін", data = "10:30")) // Тималін (імуномодулятор)
        add(
            DataCapsule(
                name = "Пантопразол", data = "08:45"
            )
        ) // Пантопразол (засіб для лікування шлункових проблем)
    }





    Column(
        modifier = Modifier
        //   .fillMaxSize()
    ) {
        /*   LazyRow(
               modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
               horizontalArrangement = Arrangement.spacedBy(10.dp)
           ) {

               items(dataScreens.size) {
                   val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.getDefault())
                   val date = LocalDate.parse(dataScreens[it], formatter)

                   val dayOfWeek = date.dayOfWeek.getDisplayName(
                       TextStyle.SHORT, Locale.getDefault()
                   )
                   val dayOfMonth = date.dayOfMonth.toString().padStart(2, '0')

                   Card(shape = ShapeDefaults.Large) {
                       Column(
                           modifier = Modifier.padding(16.dp),
                           horizontalAlignment = Alignment.CenterHorizontally
                       ) {
                           Text(
                               dayOfWeek.toString().take(3),
                               style = MaterialTheme.typography.labelMedium
                           )
                           Text(dayOfMonth.toString(), style = MaterialTheme.typography.headlineMedium)
                       }


                   }

               }
           }

      */

        // Сортування по часу
        val sortedList = liks.sortedBy { LocalTime.parse(it.data) }

        // Групування по годинах
        val grouped = sortedList.groupBy { LocalTime.parse(it.data).hour }

        LazyColumn(
            modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            grouped.forEach { (hour, items) ->
                item {
                    // Розділювач для кожної години
                    Text(
                        text = "${hour}:00",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(vertical = 8.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                items(items.size) { index ->
                    val item = items[index]
                    Card(
                        onClick = {navController.navigate("detail")},
                        modifier = Modifier.fillMaxWidth(), shape = ShapeDefaults.Large
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround

                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .weight(1f),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(item.name, style = MaterialTheme.typography.titleLarge)
                                Spacer(modifier = Modifier.height(10.dp))
                                Row {
                                    Text("Після їжі", style = MaterialTheme.typography.labelMedium)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(item.data, style = MaterialTheme.typography.labelMedium)
                                }


                            }
                            Checkbox(
                                checked = false,
                                onCheckedChange = {},
                                modifier = Modifier.padding(end = 10.dp)
                            )
                        }
                    }
                }
            }
        }
    }


}