package com.example.cukcuk.presentation.ui.unit.unit_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.cukcuk.R
import com.example.cukcuk.presentation.components.Toolbar
import com.example.cukcuk.presentation.ui.unit.unit_form.UnitForm


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitListScreen(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry,
    viewModel: UnitListViewModel = hiltViewModel()

) {
    val units = viewModel.units.value
    val isOpenForm = viewModel.isFormOpen.value
    val unitUpdateID = viewModel.unitUpdateID.value

    var unitSelectedIDArg = backStackEntry.arguments?.getString("currentUnitId")
    val unitSelectedID = remember { mutableStateOf(unitSelectedIDArg) }
    print("unitId: $unitSelectedID")

    Scaffold(
        topBar = {
            Toolbar(
                title = "Đơn vị tính",
                menuTitle = null,
                hasMenuAction = true,
                onBackClick = {navController.popBackStack()},
                onMenuClick = {viewModel.openForm(null)}
            )
        }
    ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White)
            ) {
                items(units) { unit ->
                    UnitItem(
                        unit,
                        unitSelectedID.value,
                        onSelected = {
                            println("Chọn unit: ${unit.UnitName}")
                            unitSelectedID.value = unit.UnitID.toString()
                        },
                        onClickEditIcon = {
                            viewModel.openForm(unit.UnitID)
                        }
                    )
                }
        }
    }

    if (isOpenForm) {
        UnitForm(
            unitId = unitUpdateID,
            onClose = {state ->
                viewModel.closeForm(state)
            }
        )
    }
}

@Composable
fun UnitItem(
    unit: com.example.cukcuk.domain.model.Unit,
    currentUnitId: String?,
    onSelected: () -> Unit,
    onClickEditIcon: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = strokeWidth
                )
            }
            .clickable{onSelected()},
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.width(40.dp),
            contentAlignment = Alignment.Center
        ) {
            if (unit.UnitID.toString() == currentUnitId) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(
                            color = colorResource(id = R.color.color_unit_selected),
                            shape = RoundedCornerShape(14.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_yes),
                        contentDescription = "Ảnh ví dụ",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(18.dp)
                    )
                }

            }
        }

        Text(
            text = unit.UnitName,
            modifier = Modifier.weight(1f)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_edit_pencil),
            contentDescription = "Ảnh ví dụ",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(end = 10.dp)
                .size(28.dp)
                .clickable{onClickEditIcon()}
        )
    }
}

